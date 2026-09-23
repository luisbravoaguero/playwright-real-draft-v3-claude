@regresion @sctr @moduloconstacias @declaracionsctr
Feature: Declaracion de una poliza sctr para pension o salud
  @declaracionpensionsalud @declaracionsctr @declaracionpensionsaludmesadelantado @migraciondeclaracionpensionsalud
  Scenario Outline: Declaracion de una poliza sctr para pension y salud mes adelantado y tipo de carga individual
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Constancias SCTR y VL
    And en la pagina Constancias SCTR y VL selecciona la opcion SCTR General
    And en la pagina Clientes seleccionamos el numero de poliza "<numero_poliza>" para declarar
    And en la pagina Perfil Cliente seleccionamos una aplicacion para declarar la poliza
    And en la pagina Declaracion completamos el formulario Datos de los asegurados
    And en la pagina Declaracion completamos el formulario Informacion de Declaracion
    And en la pagina Declaracion seleccionamos el boton Declarar
    Then en la pagina Resumen Declaracion SCTR se muestra el resumen de la aplicacion declarada
    Examples:
      | numero_poliza |
      | 7012600005517 |

  @clienteconpolizayrucactivopensionsaludmesadelantado @migracionclienteconpolizayrucactivopensionsaludmesadelantado
  Scenario Outline: Validar cliente con poliza sctr y RUC activo para pension y salud mes adelantado
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Constancias SCTR y VL
    And en la pagina Constancias SCTR y VL selecciona la opcion SCTR General
    And en la pagina Clientes ingresamos el tipo de documento "<tipo_documento>" y numero de documento "<numero_documento>"
    Then en la pagina Perfil Cliente se valida que la poliza esta vigente y el RUC esta activo
    Examples:
      | tipo_documento|numero_documento|
      | RUC           |20538995364     |


  @declaracionrenovacionsctrpensionsaludmesadelantado
  Scenario Outline: Declaracion de una poliza sctr con renovacion para pension y salud mes adelantado y tipo de carga individual
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Constancias SCTR y VL
    And en la pagina Constancias SCTR y VL selecciona la opcion SCTR General
    And en la pagina Clientes seleccionamos el numero de poliza "<numero_poliza>" para declarar
    And en la pagina Perfil Cliente seleccionamos una aplicacion para declarar la poliza con renovacion
    And en la pagina Declaracion completamos el formulario Datos de los asegurados
    And en la pagina Declaracion completamos el formulario Informacion de Declaracion
    And en la pagina Declaracion seleccionamos el boton Declarar
    Then en la pagina Resumen Declaracion SCTR se muestra el resumen de la aplicacion declarada
    Examples:
      | numero_poliza |
      | 7012600003160 |

  @declaracionSctrGeneralMesVencidoIndividual
  Scenario Outline: Declaracion de una poliza sctr general mes vencido mes vencido tipo de carga individual
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Constancias SCTR y VL
    And en la pagina Constancias SCTR y VL selecciona la opcion SCTR General
    And en la pagina Clientes seleccionamos el numero de poliza "<numero_poliza>" sctr general
    And en la pagina Perfil Cliente seleccionamos la aplicacion "<numero_aplicacion>" para declarar sctr general mes vencido
    And en la pagina Perfil Cliente seccion Lista de Polizas declaramos la aplicacion seleccionada
    And en la pagina Declaracion de planilla realizamos la carga individual para la generacion
    Then en la pagina Declaracion de planilla se muestra el reporte constancia


    Examples:
      | numero_poliza |numero_aplicacion|
      | 7012600001562 | 5               |