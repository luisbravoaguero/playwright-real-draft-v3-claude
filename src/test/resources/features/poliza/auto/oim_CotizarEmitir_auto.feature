Feature: Cotizar y emitir una póliza auto nuevo con DNI
  @CotizaryEmitirAutoNuevo @regresion @grupo1
  Scenario Outline: Cotizar y emitir una póliza auto nuevo con DNI
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Polizas
    And en la pagina Polizas selecciona la opcion Auto
    And en la pagina Auto selecciona la opcion Cotizar poliza de Auto
    And en la pagina Cotización póliza de auto ingresa el numero de placa
    And en la pagina Cotización póliza de auto en la seccion Bien Asegurado se ingresa el tipo de vehiculo "<tipo_vehiculo>", marca y modelo "<marca_modelo>", anio de fabricacion "<anio_fabricacion>", estado del vehiculo "<estado_vehiculo>"
    And en la pagina Cotización póliza de auto en la seccion Contratante y circulación del riesgo se ingresa el tipo de documento "<tipo_doumento>", numero de documento "<numero_documento>", nombre "<nombre>", apellido paterno "<ap_paterno>", apellido materno "<ap_materno>", fecha nacimiento "<fecha_nacimiento>", correo electronico "<correo_electronico>", sexo "<sexo>", departmento "<departamento>", provincia "<provincia>", distrito "<distrito>"
    And en la pagina Cotización póliza de auto en la seccion Elige el producto a cotizar se ingresa el producto "<producto>" y tipo de uso "<tipo_uso>"
    And el sistema muestra el resumen y el numero de cotizacion
    And en la pagina Cotización póliza de auto se realiza la emisión de la póliza
    And en la pagina Emisión Póliza Auto Nuevo completamos el formulario Datos de la Poliza e ingresamos el numero de placa
    And en la pagina Emisión Póliza Auto Nuevo completamos el formulario Datos del Contratante en la seccion Datos Principales ingresamos el nombre "<nombre_contratante>" apellido paterno "<apellido_paterno>" apellido materno "<apellido_materno>" fecha de nacimiento "<fecha_nacimiento>" sexo "<sexo>" y profesion "<profesion>"
    And en la pagina Emisión Póliza Auto Nuevo completamos el formulario Datos del Contratante en la seccion Datos de Contacto ingresamos el numero de telefono de casa "<numero_telefono_casa>" telefono movil "<telefono_movil>" correo electronico "<correo_electronico>" departamento "<departamento>" procinvia "<procinvia>" distrito "<distrito>" tipo de via "<tipo_de_via>" y nombre de la via "<nombre_de_via>"
    #And en la pagina Emisión Póliza Auto Nuevo completamos el formulario Datos del Contratante en la seccion Datos del Vehiculo
    And en la pagina Emisión Póliza Auto Nuevo completamos el formulario Datos del Contratante ingresamos el tipo de financiamiento "<tipo_financiamiento>"
    And en la pagina Emisión Póliza Auto Nuevo completamos el formulario Datos del Contratante selecciona el boton Emitir Poliza
    Then en la pagina Póliza Emitida de Autos se muestra el resumen de la poliza

    Examples:
      | marca_modelo| anio_fabricacion | tipo_vehiculo  | estado_vehiculo |tipo_doumento |numero_documento| nombre | ap_paterno | ap_materno | fecha_nacimiento | correo_electronico   | sexo     | departamento | provincia | distrito | producto            | tipo_uso   | nombre_contratante |apellido_paterno|apellido_materno|fecha_nacimiento|sexo        |profesion|numero_telefono_casa|telefono_movil|correo_electronico   |departamento|procinvia|distrito|tipo_de_via | nombre_de_via |tipo_financiamiento |
      | TOYOTA AGYA | 2025             |  Automovil     | Nuevo           |DNI           |99765835        | JUAN   | CALDAS     | BRUNO      | 19/08/1993       | EXTLUBA@MAPFRE.COM.PE| MASCULINO| Lima         |Lima       | Comas    | Dorada / premium i  | Particular | JUAN               |CALDAS          |BRUNO           |19/08/1993      |Masculino   |ABOGADO  |12354432            |987654321     |EXTLUBA@MAPFRE.COM.PE|Lima        |Lima     |Comas    |Agrupacion | VIA PRINCIPIAL|Al contado          |