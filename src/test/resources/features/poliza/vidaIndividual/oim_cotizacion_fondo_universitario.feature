@CotizacionFondoUniversitario
Feature: Cotizacion Vida individual fondo universitario Soles
  @CotizacionFondoUniversitario
  Scenario Outline: Cotizacion Vida individual producto fondo universitario Soles
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Vida Individual
    And en la pagina Vida Individual selecciona la opcion Cotizar Póliza de vida individual
    And en la pagina cotizacion poliza vida individual se ingresa los campos de la seccion datos del contratante "<tipo_documento>", "<nro_documento>", "<nombres>", "<ape_paterno>", "<ape_materno>", datos contacto "<correo>"
    And en la pagina cotizacion poliza vida individual se ingresa los campos de la seccion Datos del Asegurado
    And en la pagina cotizacion poliza vida individual se ingresa las caracteristicas del seguro producto "<producto>"
    And en la pagina cotizacion poliza vida individual se procesa la cotizacion
    Then en la pagina cotizacion poliza vida individual se muestra la cotizacion guardada

    Examples:
      |tipo_documento |nro_documento    |nombres  |ape_paterno  |ape_materno  |correo                 |producto             |
      |DNI            |48348927         |KEVIN    |JIMENEZ      |CHACON       |EXTKGJC@MAPFRE.COM.PE  |Fondo Universitario  |

