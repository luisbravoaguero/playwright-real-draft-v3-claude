@CotizacionDevolucionAhorro
Feature: Cotizacion Vida individual devolucion ahorro Soles
  @CotizacionDevolucionAhorro
  Scenario Outline: Cotizacion Vida individual producto Vida Devolucion Ahorro Soles
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Vida Individual
    And en la pagina Vida Individual selecciona la opcion Cotizar Póliza de vida individual
    And en la pagina cotizacion poliza vida individual se ingresa los campos de la seccion datos del contratante "<tipo_documento>", "<nro_documento>", "<nombres>", "<ape_paterno>", "<ape_materno>", datos contacto "<correo>"
    And en la pagina cotizacion poliza vida individual se ingresa los campos de la seccion Datos del Asegurado
    And en la pagina cotizacion poliza vida individual se ingresa las caracteristicas del seguro producto "<producto>", Anios duracion seguro "<duracion>", porcentaje devolucion "<devolucion>"
    And en la pagina cotizacion poliza vida individual se ingresa las coberturas la suma asegurada"<suma_asegurada>"
    And en la pagina cotizacion poliza vida individual se procesa la cotizacion Vida Devolucion Ahorro
    Then en la pagina cotizacion poliza vida individual se muestra la cotizacion guardada

    Examples:
      |tipo_documento |nro_documento    |nombres  |ape_paterno  |ape_materno  |correo                 |producto                   |duracion |devolucion|suma_asegurada|
      |DNI            |48348927         |KEVIN    |JIMENEZ      |CHACON       |EXTKGJC@MAPFRE.COM.PE  | Vida Devolución Ahorro OC | 5       | 50%      |1000000   |

