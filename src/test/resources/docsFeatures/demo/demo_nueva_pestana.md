# Feature: Manejo de una nueva pestaña en el sitio demo LetsKodeIt

### 📌 Descripción
Ejemplo de referencia (sitio público, sin VPN) de un flujo que abre una **nueva pestaña** y trabaja sobre ella
con `PopupHandler` (`page.waitForPopup`). Sirve como modelo para los escenarios OIM que abren pestañas.

### 🧭 Flujo cubierto
1. Apertura de la página de práctica (`demo.url.letskodeit`)
2. Selección de un auto en el combo *Select Class Example* (pestaña principal)
3. Clic en **Open Tab**: se captura la nueva pestaña (pasa a ser la pestaña activa)
4. En la nueva pestaña: validación de *All Courses*, selección de categoría y búsqueda de un curso
5. Cierre de la nueva pestaña y vuelta a la pestaña principal
6. En la pestaña principal: el auto sigue seleccionado y se ingresa un nombre

### ✅ Validaciones clave
- La nueva pestaña se abre en `/courses` con el título *All Courses*
- La categoría navega a `/courses/category/...` y el título muestra `Category : <categoria>`
- Todos los cursos listados tras la búsqueda (AJAX) contienen el texto buscado, y hay al menos uno
- La pestaña principal conserva su estado después de cerrar la nueva pestaña

### ❌ No cubre
- Flujos OIM (es un sitio demo)
- Login, compra o inscripción en cursos

## 📄 Feature asociado
- `features/demo/demo_nueva_pestana.feature` (tags `@demo @nuevapestana`)
- Page objects: `pageobjects/demo/letskodeit/PracticaPage`, `CursosPage`
- Steps: `stepdefinitions/demo/NuevaPestanaDemoSteps` + `PestanasSteps` (cerrar y volver)

## 📊 Datos de prueba
| auto  | categoria       | curso    | nombre |
|-------|-----------------|----------|--------|
| Honda | Test Automation | Cypress  | Luis   |
| Benz  | Test Automation | Selenium | Ana    |
