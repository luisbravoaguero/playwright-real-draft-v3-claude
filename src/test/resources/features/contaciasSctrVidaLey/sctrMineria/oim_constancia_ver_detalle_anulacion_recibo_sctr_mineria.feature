@regresion
Feature: Visualizar detalle de anulacion de los recibos y la constancia sctr mineria
  @visualizarDetalleAnulacionRecibosConstanciaSctrMineria
  Scenario Outline: Visualizar detalle de anulacion de los recibos y la constancia sctr mineria
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Constancias SCTR y VL
    And en la pagina Constancias SCTR y VL selecciona la opcion SCTR Mineria
    And en la pagina Clientes SCTR Mineria seleccionamos la opcion Constancia dentro del menu Consultas
    And en la pagina Busqueda de Constancias SCTR Mineria, selecciona la pestana Poliza
    And en la pagina Busqueda de Constancias SCTR Mineria, ingresa el numero de poliza "<numero_poliza>" y el numero de recibo "<numero_recibo>"
    Then en la pagina Busqueda de Constancias SCTR Mineria, se muestra el detalle del recibo anulado "<numero_recibo>" en una ventana emergente

    Examples:
      |numero_poliza|numero_recibo|
      |7012600002906|110756719    |