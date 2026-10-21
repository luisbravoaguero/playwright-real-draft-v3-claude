# CLAUDE.md

Black-box UI test automation for **MAPFRE Perú's OIM portal** (`https://oim.pre.mapfre.com.pe/#/login`, env `qa`).
The project contains no application code, only tests. The code is written in Java, but names, comments, Gherkin steps and error messages are in **Spanish**. Keep that convention.

## Stack
- Java 17, Maven 3.9 (no wrapper). Playwright for Java 1.57, Cucumber 7.33 (+ picocontainer), TestNG 7.11 via Surefire 3.5.4.
- ExtentReports 5.1.2 (JSON per run + Spark HTML), Cucumber HTML/JSON/JUnit, SLF4J simple.
- Apache POI (edits xls/xlsx upload templates at runtime), Gson, commons-io/lang3.
- CI: `JenkinsCI` (declarative pipeline on Kubernetes pods, Bitbucket repo `map-pe-devops/oim_automatizacion`).

## Commands
```bash
mvn -B test-compile                                        # compile
mvn -B test -Dcucumber.filter.tags="@emisionsctr"          # run a tag (always pass tags)
mvn -B test -Dcucumber.filter.tags="@x" -Ddp.threads=2     # parallel scenarios (TestNG DataProvider threads)
mvn -B test -Dcucumber.execution.dry-run=true -Dcucumber.plugin=json:target/dry.json   # list/validate steps, no browser
mvn -B test ... -Ddata.seed=12345                          # reproduce the random test data of a run
mvn -B test ... -Dheadless=false -Dbrowser=firefox         # driver.properties keys can be overridden with -D
mvn -B test ... -Dbrowser.perScenario=false                # try browser reuse per thread (default true = new browser per scenario)
mvn -B test ... -Dui.legacyWait.scale=0.5                  # shorten the legacy fixed waits (default 1.0)
mvn -B test ... -Dtrace.mode=on                             # Playwright trace for every scenario (default retain-on-failure)
mvn -B test ... -Dbrowser.ua.hideHeadless=true -Dbrowser.channel=chromium   # headless mitigations (opt-in)
```
Runner: `src/test/java/com/mapfre/test/runner/RunnerCucumberTest.java`. The default tag expression is `"not @wip"`, and `-Dcucumber.filter.tags` overrides it.
Reports go to `target/cucumber-reports/` and `target/extent-reports/extent.json`. Add `-Dextent.spark.enabled=true` to also get `index.html`, and use `-Dextent.out.dir` to change the output folder.

## Configuration and credentials
- Files: `src/main/resources/driver.properties` (browser, headless, maximize, pageLoadTimeout, `browser.perScenario`) and `src/main/resources/global.properties` (env, `app.url.<env>`, spinner sync, checkpoints, `ui.legacyWait.scale`). Both are the **shared baseline** for everyone, including CI.
- Lookup priority in `ConfigManager`: **`-Dkey` > env var `KEY_UPPER_WITH_UNDERSCORES` > `config/local/global.properties` > classpath `global.properties`**.
- **Credentials are never committed.** `user.email` / `user.password` are supplied by:
  - Local: `config/local/global.properties` in the project root, which is git-ignored and only needs the keys it overrides. It uses Java properties syntax, so a backslash must be written as `\\`.
  - Jenkins: the env vars `USER_EMAIL` / `USER_PASSWORD`, injected with `withCredentials(usernamePassword(...))` around the `mvn test` in the pod stage. **This is not added to `JenkinsCI` yet** (the user will do it).
- Read config through `ConfigManager.getGlobal(key)` (or `getDriver(key)` for `driver.properties`), which respects -D and ENV. Don't use `getProperty`, which reads only the file.
- Diagnostics and timeout keys:
  - `driver.properties`: `trace.mode` (`off|on|retain-on-failure`) and `trace.dir` (default `target/traces`). The headless mitigations are blank/false by default: `browser.channel`, `browser.ua.hideHeadless`, `browser.userAgent`, `browser.locale`, `browser.timezoneId`.
  - `global.properties`: `ui.nav.timeoutMs` (blank = `pageLoadTimeout`), `ui.nav.readyTimeoutMs` (60 s), `ui.screenshot.timeoutMs` (10 s).
- The old WEBMASTER password was committed in the past, so it must be rotated. Never write credentials into source, docs or reports.

## Architecture
```
src/main/java/com/mapfre
  config/              ConfigManager, EnvironmentConfig (app.url.<env>)
  playwright/driver/   DriverFactory (default: new Playwright+Browser+Context+Page per scenario; opt-in browser reuse per thread), DriverManager (ThreadLocals, cleanup, shutdownAll), BrowserOptions
  playwright/pageobjects/  BasePage (shared helpers) + pages per business area (poliza/*, constanciasSctrVidaLey/*, inspeccionAutos/*)
  playwright/components/   MaterialAutocomplete, MaterialDatePicker, SearchResults component
  utils/               UiSync (spinner waves + quiet period), Checkpoint, DownloadVsErrorRace, DateUtils, StringUtils
  utils/waits/         FirstAppearanceRace, FirstDisappearanceRace, GestorModal, OptionalModalWait, ValueWait
  utils/testdata/      RandomData, SeedManager
  asserts/             ElementAsserts (wrapper over PlaywrightAssertions)
  reporting/           ArtifactSink/ArtifactSinks (main code emits screenshots without depending on Cucumber)
src/test/java/com/mapfre
  test/runner/         RunnerCucumberTest (AbstractTestNGCucumberTests, @DataProvider(parallel=true))
  test/hooks/          Hooks, ExtentLifecycleHooks, ScenarioContext, ScenarioKeys, PageProvider
  test/stepdefinitions/  mirrors the page-object tree
  reporting/           ExtentReportManager, ExtentCucumberStepsPlugin, ConsoleStepLogger, ExtentEvidence, ExtentJsonMergeMain
src/test/resources
  features/            .feature files (all Scenario Outlines); docsFeatures/ has Markdown docs per feature
  testdata/uploads/templates/, images/   upload files
```

### Patterns to follow
- **Step classes** take `PageProvider` (+ `ScenarioContext` when needed) through the constructor (PicoContainer) and create page objects with `new XxxPage(pageProvider.get())`.
- **Share data between steps** with `ScenarioContext.put/getString` using constants from `ScenarioKeys`. Never use static fields.
- **Page objects** extend `BasePage`. Define locators as `private final Locator` fields in the constructor, and prefer `getByRole`/`getByLabel`/`getByText` over XPath.
- **Navigation / diagnostics:** navigate with `AppReadiness.navegar(page, url, senalListo)`, which is what `BasePage.navigate()` and `LoginPage.navigate()` use. It waits for `DOMContentLoaded`, never `load`.
  - The readiness signal is an element that proves the SPA rendered. On a timeout, the error is labeled `[NAVEGACION]`, `[APP_NO_RENDERIZO]`, `[SPINNER_ATASCADO]` or `[CONTENIDO_INESPERADO]` and carries `PageDiagnostics.capturar(page)`.
  - `ActivityRecorder` (attached per context in `DriverFactory`, so popups are covered too) adds the pending/failed requests, HTTP >= 400 responses and console/JS errors. URLs are logged without the query string.
  - `UiSync` spinner timeouts now throw `FrameworkException` with the same diagnosis instead of a bare `TimeoutError`.
- **Synchronization:** perform the action, then `UiSync.waitForAppIdle(page)`, or use the `*AndSync` helpers in `BasePage`. For "success or error?" transitions, use `FirstAppearanceRace` / `FirstDisappearanceRace` (the `processAndAssert...(timeoutMs, quietMs)` methods). Don't add new `waitForTimeout`/`Thread.sleep`.
- **Evidence:** `ExtentEvidence.shot("...")` in steps, `Checkpoint.capture("...")` from main code. Every step is also screenshotted automatically, **once**: `ExtentCucumberStepsPlugin` takes the capture and `Hooks.@AfterStep` reuses it for the Cucumber report (`ExtentReportManager.takeLastStepScreenshot()`). Don't add another capture in hooks: the failed step's screenshot is the failure evidence.
  - Only PASSED and FAILED steps are captured; SKIPPED steps are not.
  - Every `ScreenshotUtils` capture has its own timeout (`ui.screenshot.timeoutMs`).
  - On failure, `Hooks.@After` saves a Playwright trace (`TraceRecorder`, before `cleanup()`) and logs its path to Extent.
- **Popups / new tabs:** open them with `BasePage.clickAndCapturePopup(locator)` or `PopupHandler.abrir(page, trigger)` (`page.waitForPopup`). The popup becomes the **active page**: `DriverManager.page()`, `PageProvider`, `UiSync` and the per-step screenshots all use it. Return with `PopupHandler.cerrarYVolver()` / `volverAPrincipal()`, or reactivate a popup with `activar(p)`. If a tab closes by itself, the main page becomes active again. Generic steps are in `PestanasSteps`. Steps that act on a popup must build their page objects **inside the step method** (`new XxxPopupPage(pageProvider.get())`), not in the constructor, because PicoContainer creates a step class when it is first used, so a constructor would capture whichever tab was active at that moment. Don't poll `context.pages()`, don't use `waitForTimeout`, and never close the main page. A runnable reference on a public site (no VPN) is `features/demo/demo_nueva_pestana.feature` (`-Dcucumber.filter.tags="@nuevapestana"`), with `PracticaPage`/`CursosPage` and `NuevaPestanaDemoSteps`. Register per-tab handlers such as `addLocatorHandler` once, when the tab opens, not in a page-object constructor.
  - **Popup timeouts:** `PopupHandler.abrir` gives the popup its own timeout (`ui.popup.pageTimeoutMs`, default 120 s) for actions, navigation and assertions, and returns it only after `UiSync.waitForAppIdle(popup)`. Other tabs keep `pageLoadTimeout` (actions) and `ui.assert.timeoutMs` (assertions, 30 s). Playwright's assertion timeout is a JVM-wide static, so the per-tab value lives in `PageTimeouts` and `ElementAsserts` applies it to every assertion. In popup page objects, assert through `ElementAsserts` (`assertUrl` for URLs) or pass `PageTimeouts.assertTimeoutFor(locator)` to a direct `assertThat`. Never raise a global timeout for a slow tab.
- **Driver lifecycle:** don't create or close Playwright/Browser in steps or pages. `Hooks` calls `DriverFactory.init()` / `DriverManager.cleanup()` per scenario and `DriverManager.shutdownAll()` in `@AfterAll`.
- **Errors:** when rethrowing inside a `catch`, always pass the caught exception as the cause (`new AssertExceptions(msg, e)`). Use SLF4J (`log` from `BasePage`, or a `private static final Logger`), never `System.out.println`.
- **Legacy waits:** `BasePage.waitRandomBetween(ms)` is now a deterministic wait of `ms * ui.legacyWait.scale`. Don't add new callers.
- **Random data:** `RandomData.*`. The seed is reset per scenario in `Hooks` and logged to Extent as `Data seed: N`. Within one scenario, plate, VIN and engine number **intentionally share their first 6 characters** (`correlatedRnd()`). Keep that.
- **Real document numbers (pool):** steps that need a real, unused document number call `DocumentosPrueba.asignar(ConsumidorDocumento.X)` (`utils/testdata/documentos/`) instead of `RandomData`. Values come from a pool file you extract with the SQL query; it's never committed.
  - Pool file: one value per line, `#` comments allowed, UTF-8 BOM tolerated. The first line should be `#runIdBase=<next BUILD_NUMBER of the job>`: Jenkins uses `runId = BUILD_NUMBER - runIdBase`, so counting restarts at 0 with each new file and the wrap WARN is accurate. If the header is missing, base 0 is used and a WARN is logged. A build below the base fails. Duplicate values are dropped on load (WARN), but still use `SELECT DISTINCT`. Extract ~20k values with a query that **excludes already-used/insured documents**, ideally in random order (`FETCH FIRST 20000 ROWS ONLY`). Set its path with `testdata.documentos.pool.path`: locally in `config/local/global.properties` (e.g. `config/local/documentos_pool.txt`); in Jenkins with the env var `TESTDATA_DOCUMENTOS_POOL_PATH` from a "Secret file" credential.
  - Index = `(runId * stride + slot) mod zona`. Jenkins: `runId = BUILD_NUMBER - runIdBase`, zone `[0, N - reservaLocal)`. Local: a counter in `config/local/documentos_pool.contador`, zone = the last `reservaLocal` values, read backwards. `-Ddata.runId=N` forces the runId. With the same runId, different slots never collide, even across pods.
  - New scenario: add an enum constant with the next free slot (< `testdata.documentos.stride`, default 20). Never reassign slots. Change the stride only when you regenerate the pool. Each consumer gets one value per run.
  - Capacity ≈ (N - reservaLocal) / stride Jenkins builds. A WARN "ya dio la vuelta" means the pool must be regenerated.
  - Unit test (no browser): `mvn -B test -Dtest=PoolArchivoProveedorTest -Dsurefire.failIfNoSpecifiedTests=false`.
- **Upload templates:** copy and edit them with `testdata.TemplateFactory` + `ExcelXlsEditor` into `target/test-output/uploads`.

## CI pipeline (JenkinsCI)
Parameters: RAMA, TAGS, CLASE_RUNNER, HILOS_DE_EJECUCION. Stages:
1. A dry run discovers `feature:line` items.
2. The items are split into groups of 2 per Kubernetes pod, and the pods run in parallel with `-Dcucumber.features=... -Ddp.threads=N -Dextent.out.dir=target/extent-archives/pod-N`.
3. JUnit names are cleaned (` - #1` suffixes removed, for Octane) and published.
4. `ExtentJsonMergeMain` (`exec:java`, test classpath) merges every pod's `extent.json` into `target/extent-merged/index.html`, which is published with `publishHTML`.

## History and decisions (2026-09-23)
- A full project analysis was done. The three High findings were fixed:
  1. Credentials were removed from `global.properties`. `ConfigManager` now loads the optional `config/local/global.properties`, and `LoginPage` uses `getGlobal` and fails with a clear message when credentials are missing.
  2. `RandomData` returned the same values every time. Now `SeedManager.random()` gives one sequence per scenario, and `Hooks` clears and logs the seed in `@Before` and clears it again in `@After`.
  3. The runner's hardcoded `@riesgosgenerales` tag was replaced with `"not @wip"`.
- The user asked **not to modify `JenkinsCI`** for now.
- A conversation export is in `docs/` (`chat-export.html/.pdf` and the `/export` .txt).

## History and decisions (2026-09-25): Medium findings
- **One screenshot per step:** the plugin captures it and `@AfterStep` reuses it. A failed screenshot now logs a warning instead of marking the test FAIL.
- **Browser reuse (implemented, opt-in):**
  - With `browser.perScenario=false`, each thread launches one browser and reuses it, with a new `BrowserContext` per scenario, and browsers close in `Hooks.@AfterAll`.
  - At the user's request, the **default is `true`** (new Playwright and browser per scenario, the original workflow).
  - Reuse has no effect in Jenkins today, because each pod runs 2 scenarios on 2 threads (1 scenario per thread). It only helps when a thread runs several scenarios (local bulk runs, `HILOS_DE_EJECUCION=1`). The user will test it later.
- **Safe cleanup:** `cleanup()` / `shutdownAll()` close each resource separately, log failures, never throw, and always remove the ThreadLocals.
- **`ElementAsserts` keeps the cause:** every `AssertExceptions` thrown from a catch carries the Playwright error.
- **`waitRandomBetween`** is deterministic and scaled by `ui.legacyWait.scale`.
- **`clickMenuOption`:**
  - Passes the menu name as an `evaluate` argument, so there's no JS injection.
  - Waits for the item instead of 1 s, and waits for app idle instead of 2.5 s.
  - Throws if the item is not found. It has no callers today.
- **Logging and exceptions:**
  - Live `System.out.println` calls were replaced with SLF4J.
  - 11 rethrows now pass the cause.
  - 4 silent catches in SOAT/Accidentes pages now `log.debug` the reason.
- **Verification:** there is no OIM access from the dev machine (VPN only). The changes were verified with compilation, a dry run (81 scenarios), and a local Cucumber smoke run with real Chromium (thread reuse, per-scenario flag, screenshot counts, "Caused by" in Extent, no leftover browser processes). They still need to be run on a VPN machine.

## History and decisions (2026-10-21): headless login timeouts
- Problem: in headless mode the OIM login timed out after ~60 s, and the error didn't say whether navigation stalled, the page stayed blank, the spinner got stuck or other content rendered.
- Causes found:
  - `navigate()` waited for `load`, which depends on third-party resources.
  - `UiSync` treats a spinner that doesn't exist yet (blank page) as idle.
  - Nothing recorded the network or the console.
  - `screenshot()` waits for web fonts with no timeout of its own, and SKIPPED steps were captured too.
  - There was an extra failure capture in `@After`.
  - `DriverFactory` leaked the `Playwright` process when the browser failed to launch, and leaked the context when `newPage()` failed.
- Implemented:
  - `AppReadiness` (phased navigation)
  - `utils/diagnostics/ActivityRecorder` + `PageDiagnostics`
  - `TraceRecorder` (default `retain-on-failure`)
  - screenshots bounded to 10 s, captured only for PASSED/FAILED
  - both leaks fixed
  - opt-in headless mitigations, at the user's request: UA without "HeadlessChrome", `browser.channel`, locale/timezone
- Verified locally without VPN:
  - compile and dry run
  - `@nuevapestana` with `-Dtrace.mode=on`
  - simulated login failures (`-Dapp.url.qa=about:blank` / `https://example.com` / an unreachable IP) correctly classified as APP_NO_RENDERIZO / CONTENIDO_INESPERADO / NAVEGACION, with a trace saved and no leftover processes
- Still pending:
  - Real VPN headless run: compare the per-phase timings (`[NAVEGACION]`/`[RENDER]`/`[SPINNER]` log lines), then try `-Dbrowser.ua.hideHeadless=true` and `-Dbrowser.channel=chromium`.
  - Jenkins doesn't archive `target/traces/` yet. That needs a `JenkinsCI` change, which the user will make.

## Known issues / backlog
- **Undefined step:** `features/poliza/auto/oim_emision_auto.feature` line 14 ("en la pagina Emisión Póliza Auto Nuevo completamos el formulario Datos del Contratante en la seccion Datos del Vehiculo ingresamos la frecuencia de uso ..."). No step definition matches it, so a default run reports it as UNDEFINED.
- Medium (remaining; needs VPN runs module by module):
  - Replace the ~60 `waitRandomBetween(...)` calls (mostly Constancias pages) and ~38 page-level `waitForTimeout` sleeps with state waits (locator visible/hidden, `UiSync.waitForAppIdle`).
  - Checkbox and overlay helpers in `BasePage` (`clickCheckBoxList`, `dismissAnyOverlay`) still use short fixed waits.
  - Broad `catch (Exception)` blocks are still broad. Only the cause and logging were fixed; exception types were not narrowed.
- Low:
  - Very long steps (up to 16 params), single-row Scenario Outlines, no `Background` for login.
  - `BasePage` has ~890 lines with near-duplicate helpers.
  - Tag inconsistencies (`@regresion`/`@Regresion`) and typos in folders/steps (`contaciasSctrVidaLey`, `riegosgenerales`, `emsion`, `fomrulario`).
  - Placeholder Maven coordinates (`org.example`), no Maven Wrapper, saucedemo URLs for dev/prod, unused `implicitWait`.
  - `TemplateFactory` uses hardcoded relative paths (`TestResourceFiles` exists).
  - Double Extent flush.
  - No unit tests or static analysis for the framework.
