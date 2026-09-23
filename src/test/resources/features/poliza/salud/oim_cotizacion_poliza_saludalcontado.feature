@PolizaSaludContado @regresion  @cotizacionpolizasalud
Feature:Cotizacion de poliza salud al contado
  Scenario Outline: Cotizacion de poliza salud al contado
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Salud
    And en la pagina salud se debe seleccionar la opcion cotizar poliza de salud
    And en la pagina cotizacion poliza salud se ingresa los campos producto "<producto>" , agente "<agente>"
    And en la pagina cotizacion poliza salud se agrega un asegurado
    And en la pagina cotizacion poliza salud se agrega los datos tipo de documento "<tipo_documento>", numero documento "<nro_documento>", nombres "<nombre_asegurado>", apellido paterno"<paterno_asegurado>", apellido materno "<materno_asegurado>", fecha de nacimiento "<fec_nacimiento>",tipo "<tipo>", sexo "<sexo>"
     Then en la pagina cotizacion poliza salud se genera la cotizacion

     Examples:
       |producto             |agente|tipo_documento    |nro_documento   |nombre_asegurado    |paterno_asegurado |materno_asegurado|fec_nacimiento |tipo         |sexo         |
       |TREBOL SALUD NUEVOS  |9808  |DNI               |58282921        |WELLS               |CERVANTES         |SIMMONS          |19/08/1993     |Titular      |Masculino    |