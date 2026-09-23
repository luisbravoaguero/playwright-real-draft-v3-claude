@regresion
Feature: Visualizar constancia de tipo exclusion sctr general
  @visualizarConstanciaTipoExclusionSctrGeneral
  Scenario Outline: Visualizar constancia de tipo exclusion sctr general
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Constancias SCTR y VL
    And en la pagina Constancias SCTR y VL selecciona la opcion SCTR General
    And en la pagina Clientes SCTR General seleccionamos la opcion Constancia dentro del menu Consultas
    And en la pagina Busqueda de Constancias SCTR General, selecciona la pestana Poliza
    And en la pagina Busqueda de Constancias SCTR General, ingresa el numero de poliza "<numero_poliza>" y el numero de recibo "<numero_recibo>"
    Then en la pagina Busqueda de Constancias SCTR General, se muestra la constancia de tipo exclusion del numero de poliza "<numero_poliza>" y recibo "<numero_recibo>"

    Examples:
      |numero_poliza|numero_recibo|
      |7012400128589|171389250    |
      #|7012400128589|170608554    | segunda data para usar