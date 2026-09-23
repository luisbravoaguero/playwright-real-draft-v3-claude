@regresion
Feature: Reimpresion de constancia de una poliza sctr para pension o salud
  @constanciaReimpresionIndividualVidaLey
  Scenario Outline: Reimpresion individual de constancia poliza vidaley mes adelantado
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Constancias SCTR y VL
    And en la pagina Constancias SCTR y VL selecciona la opcion Vida Ley
    And en la pagina Clientes seleccionamos el numero de poliza "<numero_poliza>" para declarar
    And en la pagina Perfil Clientes seleccionamos una aplicacion para actualizar la planilla
    And en la pagina Constancias generamos la reimpresión de constancia
    Then en la pagina Constancias se muestra la reimpresion y descarga de la constancia vidaley
    Examples:
      |numero_poliza|
      |6102600001329|
