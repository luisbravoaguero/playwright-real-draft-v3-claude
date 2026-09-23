@regresion
Feature: Constancia de reemplazo de una poliza vidaley
  @constanciaReemplazoVidaLey
  Scenario Outline: Constancia de reemplazo vidaley
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Constancias SCTR y VL
    And en la pagina Constancias SCTR y VL selecciona la opcion Vida Ley
    And en la pagina Clientes seleccionamos el numero de poliza "<numero_poliza>" vidaley
    And en la pagina Perfil Cliente Vida Ley selecciona una aplicacion con estado declarada para reemplazar
    And en la pagina Constancias Vida Ley selecciona un registro de la tabla de asegurados para reemplazar
    And en la pagina Reemplazar Planilla Vida Ley carga la planilla del asegurado a reemplazar
    And en la pagina Reemplazar Planilla Vida Ley realiza el reemplazo de planilla
    Then en la pagina Constancias Vida Ley se muestra el asegurado reemplazante con tipo movimiento REEMPLAZO y estado ACTIVO
    And el asegurado reemplazado Vida Ley con estado INACTIVO

    Examples:
      | numero_poliza |
      | 6102600001329 |