@regresion @constanciasSctrMineria
Feature: Declaracion de una poliza sctr mineria para pension o salud
  @declaracionSctrMineriaPensionSaludMesAdelantado
  Scenario Outline: Declaracion de una poliza sctr mineria para pension y salud mes adelantado
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Constancias SCTR y VL
    And en la pagina Constancias SCTR y VL selecciona la opcion SCTR Mineria
    And en la pagina Clientes seleccionamos el numero de poliza "<numero_poliza>" para declarar
    And en la pagina Perfil Cliente seleccionamos una aplicacion para declarar la poliza
    And en la pagina Declaracion completamos los datos de los asegurados de forma masiva sctr mineria
    And en la pagina Declaracion completamos el formulario Informacion de Declaracion
    And en la pagina Declaracion seleccionamos el boton Declarar
    Then en la pagina Resumen Declaracion SCTR se muestra el resumen de la aplicacion declarada
    Examples:
      | numero_poliza |
      | 7012600005587 |

  @declaracionSctrMineriaMesVencido
  Scenario Outline: Declaracion sctr mineria mes vencido
  #Scenario Outline: Declaracion de una poliza sctr mineria para pension y salud mes vencido
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Constancias SCTR y VL
    And en la pagina Constancias SCTR y VL selecciona la opcion SCTR Mineria
    And en la pagina Clientes seleccionamos el numero de poliza "<numero_poliza>" sctr mineria
    And en la pagina Perfil Cliente seleccionamos una aplicacion para declarar sctr mineria mes vencido
    Examples:
      | numero_poliza |
      | 7012600005191 |
