@regresion @vidaley @moduloconstacias @constanciamanual @constanciamanualvidaley @grupo1
Feature: Constancia manual de una poliza vida ley
  @constanciamanualvidaley @constanciamanualvidaleymesadelantado @migracionconstanciamanualvidaley
  Scenario Outline: Constancia Manual de una poliza vida ley mes adelantado
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Constancias SCTR y VL
    And en la pagina Constancias SCTR y VL selecciona la opcion Vida Ley
    And en la pagina Clientes seleccionamos el numero de poliza "<numero_poliza>" para declarar
    And en la pagina Perfil Cliente selecciona la opcion Constancia Manual para abrir el modal Generar Nueva Constancia Manual
    And en el modal Generar Nueva Constancia Manual completamos la fecha de cobertura y cargamos los asegurados vidaley
    Then en el modal Generar Nueva Constancia Manual se muestra el numero de la constancia manual generada para la poliza vidaley
    Examples:
      | numero_poliza |
      | 6102600003846 |

  @constanciamanualvidaleymesadelantadov2 @constanciamanualvidaleymesadelantadov2 @migracionconstanciamanualvidaleyv2
  Scenario Outline: Constancia Manual de una poliza vida ley mes adelantado
    Given el usuario accede a la pagina OIM con credenciales validas
    When desde la pagina Home selecciona el modulo Constancias SCTR y VL
    And en la pagina Constancias SCTR y VL selecciona la opcion Vida Ley
    And en la pagina Clientes seleccionamos el numero de poliza "<numero_poliza>" para declarar
    And en la pagina Perfil Cliente Vida Ley selecciona la opcion Constancia Manual para abrir el modal Generar Nueva Constancia Manual
    And en el modal Generar Nueva Constancia Manual Vida Ley completamos la fecha de cobertura y cargamos los asegurados
    Then en el modal Generar Nueva Constancia Manual Vida Ley se muestra el numero de la constancia manual generada

    Examples:
      | numero_poliza |
      | 6102600003846 |

