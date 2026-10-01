package com.mapfre.test.hooks;

import com.mapfre.playwright.driver.DriverFactory;
import com.mapfre.playwright.driver.DriverManager;
import com.mapfre.reporting.ArtifactSinks;
import com.mapfre.reporting.ExtentReportManager;
import com.mapfre.reporting.ReportLogger;
import com.mapfre.test.reporting.CucumberExtentArtifactSink;
import com.mapfre.utils.ScreenshotUtils;
import com.mapfre.utils.testdata.SeedManager;
import io.cucumber.java.*;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

public class Hooks {
    private static final Logger log = LoggerFactory.getLogger(Hooks.class);

    private final ScenarioContext scenarioContext;

    public Hooks(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        // Sink for Checkpoint.capture(...)
        ArtifactSinks.set(new CucumberExtentArtifactSink(scenario));

        // Driver
        DriverFactory.init();

        // Ensure Extent test exists for this thread (plugin may have created it already)
        try {
            ExtentReportManager.test();
        } catch (IllegalStateException e) {
            ExtentReportManager.createTest(scenario.getName());
        }

        ReportLogger.info("Scenario started: " + scenario.getName());
        scenarioContext.put("scenarioName", scenario.getName());

        // Semilla nueva de datos de prueba por escenario (evita repetir placa/VIN/etc. en el mismo hilo)
        SeedManager.clear();
        long seed = SeedManager.seed();
        ReportLogger.info("Data seed: " + seed + " (reproducir con -Ddata.seed=" + seed + ")");
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        // Reutiliza la captura que ExtentCucumberStepsPlugin tomo al terminar el step (no se toma una segunda).
        // Cucumber publica TestStepFinished del step antes de ejecutar este hook.
        String base64 = ExtentReportManager.takeLastStepScreenshot();
        if (base64 == null) return;
        try {
            scenario.attach(Base64.getDecoder().decode(base64), "image/png", "Step Screenshot");
        } catch (Exception e) {
            log.warn("No se pudo adjuntar la captura del paso al reporte Cucumber: {}", e.toString());
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                // Keep Cucumber failure screenshot if you want
                try {
                    String base64 = ScreenshotUtils.screenshotBase64();
                    scenario.attach(Base64.getDecoder().decode(base64), "image/png", "failure-screenshot");
                } catch (Exception e) {
                    log.warn("No se pudo capturar la pantalla del fallo: {}", e.toString());
                }
                ReportLogger.fail("Scenario failed: " + scenario.getName());
            } else {
                ReportLogger.pass("Scenario passed: " + scenario.getName());
            }
        } finally {
            DriverManager.cleanup();
            ArtifactSinks.clear();
            ExtentReportManager.clearTest();
            SeedManager.clear();
        }
    }

    @AfterAll
    public static void closeBrowsers() {
        // Los navegadores se reutilizan por hilo entre escenarios; se cierran al final de la ejecucion.
        DriverManager.shutdownAll();
    }
}