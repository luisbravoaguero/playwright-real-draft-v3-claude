@regresion
Feature: Anulacion de recibos de una poliza vidaley
  @anulacionRecibosPolizaVidaley
  Scenario Outline: Anulacion de recibo de una poliza vidaley
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Constancias SCTR y VL
    And en la pagina Constancias SCTR y VL selecciona la opcion Vida Ley
    And en la pagina Clientes Vida Ley seleccionamos la opcion Constancia dentro del menu Consultas
    And en la pagina Busqueda de Constancias Vida Ley, selecciona la pestana Poliza
    And en la pagina Busqueda de Constancias Vida Ley, ingresa el numero de poliza "<numero_poliza>" y el numero de recibo "<numero_recibo>"
    And en la pagina Busqueda de Constancias Vida Ley, realiza la anulacion del recibo "<numero_recibo>" de la poliza "<numero_poliza>"
    Then en la pagina Busqueda de Constancias Vida Ley, se muestra el detalle del recibo anulado "<numero_recibo>" en una ventana emergente
    Examples:
      | numero_poliza | numero_recibo |
      | 6102500003513 | 110788436     |