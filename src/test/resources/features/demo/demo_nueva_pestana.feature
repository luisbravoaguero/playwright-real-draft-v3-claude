@demo @nuevapestana
Feature: Manejo de una nueva pestaña en el sitio demo LetsKodeIt
  @demo @nuevapestana
  Scenario Outline: Abrir la pagina de cursos en una nueva pestaña, filtrar por categoria y buscar un curso
    Given el usuario abre la pagina de practica de LetsKodeIt
    And en la pagina de practica selecciona el auto "<auto>"
    When en la pagina de practica hace clic en Open Tab y se abre la pagina de cursos en una nueva pestaña
    Then en la nueva pestaña se muestra la pagina All Courses
    When en la nueva pestaña selecciona la categoria "<categoria>"
    And en la nueva pestaña busca el curso "<curso>"
    Then en la nueva pestaña los resultados contienen "<curso>"
    When cerramos la nueva pestaña y volvemos a la pestaña principal
    Then en la pagina de practica el auto seleccionado sigue siendo "<auto>"
    And en la pagina de practica ingresa el nombre "<nombre>" y se muestra en el campo
    Examples:
      | auto  | categoria       | curso    | nombre |
      | Honda | Test Automation | Cypress  | Luis   |
      | Benz  | Test Automation | Selenium | Ana    |
