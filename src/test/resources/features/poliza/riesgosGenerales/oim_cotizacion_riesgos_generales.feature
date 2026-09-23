@riesgosgenerales @regresion  @cotizacionriesgosgenerales
Feature:Cotizacion de poliza riesgos generales
  @cotizacionriesgosgeneraleshidrocarburosconcoberturasoloriesgociudadano
  Scenario Outline: Cotizacion de poliza riesgos generales del producto Hidrocarburos y cobertura solo riesgo ciudadano con numero de RUC
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Riesgos Generales
    And en la pagina Riesgos Generales selecciona la opcion Cotizar poliza riesgos generales
    And en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Producto ingresamos el nombre del producto "<nombre_producto>" y la cobertura "<nombre_cobertura>"
    And en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Datos de la Poliza ingresamos el numero de documento "<numero_documento>", el corredor "<corredor>", giro del negocio "<giro_negocio>", marca la opcion asegurar tercero igual a "<asegurar_tercero>", tipo de moneda "<tipo_moneda>", tipo de canal "<tipo_canal>", numero de locales "<numero_locales>" y descuentos director "<descuentos_director>"
    And en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Datos del local ingresamos la ubicacion del riesgo "<ubicacion_riesgo>", departamento "<departamento>", provincia "<provincia>" y distrito "<distrito>"
    And en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Suma asegurada ingresamos la cantidad unitaria "<cantidad_unitaria>"
    Then en la pagina Cotizador de Riesgos Generales en el paso Resultado de la cotizacion se muestra el resumen de la cotizacion del producto Hidrocarburos con cobertura Solo RC

    Examples:
      |nombre_producto               |nombre_cobertura    |numero_documento|corredor|giro_negocio            |asegurar_tercero|tipo_moneda |tipo_canal|numero_locales|descuentos_director|ubicacion_riesgo|departamento|provincia|distrito|cantidad_unitaria|
      |RC y Transportes Hidrocarburos|Solo RC Hidrocarburo|20410478180     |corredor|B: Consumidores Directos|NO              |NUEVOS SOLES|Enlace    |1             |OPCIONAL           |RIESGO COMAS    |LIMA        |LIMA     |COMAS   |50               |
      #|RC y Transportes Hidrocarburos|Solo RC Hidrocarburo|12345678911|corredor|B: Consumidores Directos|NO              |NUEVOS SOLES|1             |OPCIONAL           |RIESGO COMAS    |LIMA        |LIMA     |COMAS   |1                |

  @cotizacionriesgosgeneralescarlite
  Scenario Outline: Cotizacion de poliza riesgos generales del producto Car lite con numero de RUC
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Riesgos Generales
    And en la pagina Riesgos Generales selecciona la opcion Cotizar poliza riesgos generales
    And en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Producto ingresamos el nombre del producto "<nombre_producto>"
    And en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Datos de la Poliza ingresamos el numero de documento "<numero_documento>", el corredor "<corredor>"
    And en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Datos de la obra ingresamos el tipo de proyecto "<tipo_proyecto>", nombre de la obra "<nombre_obra>", departamento "<departamento>", provincia "<provincia>", distrito "<distrito>", direccion del riesgo "<direccion_riesgo>", tipo de moneda "<tipo_moneda>", tipo de canal "<tipo_canal>", monto de la mano de obra "<monto_mano_obra>"
    Then en la pagina Cotizador de Riesgos Generales en el paso Resultado de la cotizacion se muestra el resumen de la cotizacion del producto CAR Lite

    Examples:
      |nombre_producto|numero_documento|corredor|tipo_proyecto   |nombre_obra   |tipo_moneda |tipo_canal|departamento|provincia|distrito|direccion_riesgo        |monto_mano_obra|
      |CAR Lite       |20410478180     |corredor|Lozas deportivas|OBRA DE PRUEBA|NUEVOS SOLES|Enlace    |LIMA        |LIMA     |COMAS   |AVENIDA LAS PALMERAS 123|1000           |


  @riesgosgenerales @regresion @cotizacionDeshTrabajosEspecificosRamooResponsabilidadCivil
  Scenario Outline: Cotizacion de poliza riesgos generales del producto Trabajos especificos desh ramo responsabilidad civil con numero de RUC
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Riesgos Generales
    And en la pagina Riesgos Generales selecciona la opcion Cotizar poliza riesgos generales
    And en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Producto ingresamos el producto "<producto>"
    And en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Datos del contratante ingresamos el ruc "<tipo_documento>", numero de documento "<nro_documento>", corredor "<corredor>"
    And en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Datos del contrato ingresamos trabajo u obra a realizar "<trabajo_obra>", departamento "<departamento>", Provincia "<provincia>", distrito "<distrito>", direccion del riesgo "<dir_riesgo>", ramo "<ramo>", moneda "<moneda>", numero de trabajadores "<nro_trabajadores>", valor del contrato "<valor_contrato>", suma asegurada "<suma_asegurada>", canal "<canal>"
    Then en la pagina Cotizador de Riesgos Generales en el paso Resultado de la cotizacion se muestra el resumen de la cotizacion del producto

    Examples:
      | producto                        | tipo_documento | nro_documento | corredor | trabajo_obra                 | departamento | provincia | distrito          | dir_riesgo | ramo                    | moneda         | nro_trabajadores | valor_contrato | suma_asegurada | canal  |
      | RC y Desh Trabajos Especificos  | RUC            | 20410478180   | LUI SAC  | EMPRESA RESPONSABILIDA CIVIL | LIMA         | LIMA      | CERCADO DE LIMA   | VICTORIA123| Responsabilidad Civil   | NUEVOS SOLES   | 1                | 100000         | 800            | Enlace |


  @riesgosgenerales @regresion @cotizacionDeshTrabajosEspecificosRamoDeshonestidad
  Scenario Outline: Cotizacion de poliza riesgos generales del producto Trabajos especificos desh ramo Deshonestidad  con numero de RUC
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Riesgos Generales
    And en la pagina Riesgos Generales selecciona la opcion Cotizar poliza riesgos generales
    And en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Producto ingresamos el producto "<producto>"
    And en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Datos del contratante ingresamos el ruc "<tipo_documento>", numero de documento "<nro_documento>", corredor "<corredor>"
    And en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Datos del contrato ingresamos trabajo u obra a realizar "<trabajo_obra>", departamento "<departamento>", provincia "<provincia>", distrito "<distrito>", direccion del riesgo "<dir_riesgo>", ramo "<ramo>", ramo deshonestidad "<ramo_deshonestidad>", moneda "<moneda>", numero de trabajadores "<nro_trabajadores>", valor del contrato "<valor_contrato>", suma asegurada "<suma_asegurada>", canal "<canal>"
    Then en la pagina Cotizador de Riesgos Generales en el paso Resultado de la cotizacion se muestra el resumen de la cotizacion del producto

    Examples:
      | producto                        | tipo_documento | nro_documento | corredor | trabajo_obra                 | departamento | provincia | distrito          | dir_riesgo | ramo                    | ramo_deshonestidad             | moneda                    | nro_trabajadores | valor_contrato | suma_asegurada | canal  |
      | RC y Desh Trabajos Especificos  | RUC            | 20410478180   | LUI SAC  | EMPRESA RESPONSABILIDA CIVIL | LIMA         | LIMA      | CERCADO DE LIMA   | VICTORIA123| Deshonestidad           | Deshonestidad por Cargos       | NUEVOS SOLES              | 1                | 100000         | 800            | Enlace |


  @riesgosgenerales @regresion @cotizacionRCEventos
  Scenario Outline: Cotizacion de poliza riesgos generales del producto RC Eventos
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Riesgos Generales
    And en la pagina Riesgos Generales selecciona la opcion Cotizar poliza riesgos generales
    And en la pagina Cotizador de Riesgos Generales en seccion Producto ingresamos el producto "<producto>"
    And en la pagina Cotizador de Riesgos Generales en la seccion Datos del contratante ingresamos el tipo de documento "<tipo_documento>", numero de documento "<nro_documento>", asegurado "<asegurado>"
    And en la pagina Cotizador de Riesgos Generales en la seccion Datos del evento ingresamos Ramo "<ramo>", Moneda "<moneda>", Canal "<canal>"
    And en la pagina Cotizador de Riesgos Generales en la seccion Duracion del evento ingresamos la fecha desde "<fec_desde>" hasta "<fec_hasta>"
    And en la pagina Cotizador de Riesgos Generales ingresamos tipo de evento "<tipo_evento>", evento "<evento>", aforo "<aforo>", seguridad "<seguridad>", suma asegurada "<suma_asegurada>"
    Then en la pagina Cotizador de Riesgos Generales en el paso Resultado de la cotizacion se muestra el resumen de la cotizacion del producto

    Examples:
      | producto     | tipo_documento | nro_documento | asegurado | ramo                   | moneda       | canal    |fec_desde  |fec_hasta  | tipo_evento | evento | aforo  | seguridad | suma_asegurada  |
      | RC Eventos   | RUC            | 20410478180   | MAPFRE    |  Responsabilidad Civil | NUEVOS SOLES | Enlace   |24/08/2026 |22/11/2026 | Boda        | BODA   | 1000   | 10        | 1000            |

  @riesgosgenerales @regresion @cotizacionRCDEMOLICIONES
  Scenario Outline: Cotizacion de poliza riesgos generales del producto RC Demoliciones
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Riesgos Generales
    And en la pagina Riesgos Generales selecciona la opcion Cotizar poliza riesgos generales
    And en la pagina Cotizador de Riesgos Generales en seccion Producto ingresamos el producto "<producto>"
    And en la pagina Cotizador de Riesgos Generales en el paso Datos de la poliza, seccion Datos del contratante ingresamos el ruc "<tipo_documento>", numero de documento "<nro_documento>", corredor "<corredor>"
    And en la pagina Cotizador de Riesgos Generales en la seccion Datos del evento ingresamos Ramo "<ramo>", Moneda "<moneda>", Canal "<canal>"
    And en la pagina Cotizador de Riesgos Generales en la seccion Duracion del evento ingresamos la fecha desde "<fec_desde>" hasta "<fec_hasta>", asegurado "<asegurado>", actividad a realizar "<actividad>"
    And en la pagina Cotizador de Riesgos Generales se ingresa direccion de riesgo "<direccion_riesgo>", suma asegurada "<suma_asegurada>"
    Then en la pagina Cotizador de Riesgos Generales en el paso Resultado de la cotizacion se muestra el resumen de la cotizacion del producto

    Examples:
      | producto        | tipo_documento | nro_documento | corredor  | ramo                   | moneda       | canal    |fec_desde  |fec_hasta  | asegurado       | actividad                 | direccion_riesgo  | suma_asegurada |
      | RC Demoliciones | RUC            | 20410478180   | MAPFRE    |  Responsabilidad Civil | NUEVOS SOLES | Enlace   |24/08/2026 |24/08/2026 | cesar gonzales  | Demolicion de un edificio | Av Principal      | 0              |