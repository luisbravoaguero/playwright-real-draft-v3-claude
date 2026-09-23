@regresion
Feature: Constancia de reemplazo de una poliza sctr para pension o salud
  @constanciaReemplazoSctrGeneral
  Scenario Outline: Constancia de reemplazo sctr pension salud regular facturada
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Constancias SCTR y VL
    And en la pagina Constancias SCTR y VL selecciona la opcion SCTR General
    And en la pagina Clientes seleccionamos el numero de poliza "<numero_poliza>" para declarar
    And en la pagina Perfil Cliente selecciona una aplicacion con estado declarada para reemplazar
    And en la pagina Constancias SCTR General selecciona un registro de la tabla de asegurados para reemplazar
    And en la pagina Reemplazar Planilla SCTR General carga la planilla del asegurado a reemplazar
    And en la pagina Reemplazar Planilla SCTR General realiza el reemplazo de planilla
    Then en la pagina Constancias SCTR General se muestra el asegurado reemplazante con tipo movimiento REEMPLAZO y estado ACTIVO
    And el asegurado reemplazado con estado INACTIVO
    Examples:
      | numero_poliza |
      | 7012600004455 |
      #| 7012600004455 | usado para pruebas y funciono con la aplicacion 4 Desde: 01/04/2026 - Hasta: 30/04/2026