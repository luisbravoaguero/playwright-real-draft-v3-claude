@regresion
Feature: Constancia de reemplazo de una poliza sctr mineria para pension o salud
  @constanciaReemplazoSctrMineria
  Scenario Outline: Constancia de reemplazo sctr mineria pension salud
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Constancias SCTR y VL
    And en la pagina Constancias SCTR y VL selecciona la opcion SCTR Mineria
    And en la pagina Clientes seleccionamos el numero de poliza "<numero_poliza>" vidaley
    And en la pagina Perfil Cliente SCTR Mineria selecciona una aplicacion con estado declarada para reemplazar
    And en la pagina Constancias SCTR Mineria selecciona un registro de la tabla de asegurados para reemplazar
    And en la pagina Reemplazar Planilla SCTR Mineria carga la planilla del asegurado a reemplazar
    And en la pagina Reemplazar Planilla SCTR Mineria realiza el reemplazo de planilla
    Then en la pagina Constancias SCTR Mineria se muestra el asegurado reemplazante con tipo movimiento REEMPLAZO y estado ACTIVO
    And el asegurado reemplazado SCTR Mineria con estado INACTIVO

    Examples:
      | numero_poliza |
      | 7012600005308 |