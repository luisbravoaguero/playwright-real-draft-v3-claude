@regresion
Feature: Actualizacion de planilla de una poliza sctr para pension o salud
  @constanciaActualizacionSctrDarBaja
  Scenario Outline: Actualizacion dar de baja planilla poliza sctr pension salud regular mes adelantado
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Constancias SCTR y VL
    And en la pagina Constancias SCTR y VL selecciona la opcion SCTR General
    And en la pagina Clientes seleccionamos el numero de poliza "<numero_poliza>" para declarar
    And en la pagina Perfil Clientes seleccionamos una aplicacion para actualizar la planilla
    And en la pagina Constancias cambiamos el estado del asegurado a inactivo por medio de la baja
    Then en la pagina Constancias se muestra el estado inactivo del asegurado actualizado
    Examples:
      |numero_poliza|
      |7012600003076|