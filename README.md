# Kinal App

Proyecto de spring boot creado para la gention 
de productos, usuarios y ventas llevando control 
funcional.

## Tecnologías utilizadas
* **Java 21**
* **SpringBoot 4.0.2**
* **Maven** (Gestor de dependencias)
* **MySQL** (Sistema Gestor de Base de Datos)

## Requisitos Previos
* JDK 17 o superior instalado
* Maven
* Instancia de MySQL abierta para revisar la base de datos
* Editor de archivos (Intellij o Visual Studio Code)

## Instalacion y Ejecución
Es importante saber que debes hacer para instalar y ejecutar el programa:
* Clonar el repositorio
* Ejecutar la aplicacion desde un editor de textos (Preferiblemente Intellij)

## Documentación
* Módulo de Productos: Implementación completa de la capa de entidad, repositorio, servicio y controlador.
* Módulo de Usuarios: Desarrollo de la entidad de usuario junto con sus capas de repositorio, servicio y gestión de peticiones.
* Gestión de Ventas: Implementación de la entidad Venta para el encabezado de las transacciones, junto con su repositorio y capa de servicio.
* Gestión de Detalle de Ventas: Desarrollo de la entidad DetalleVenta para el desglose de productos vendidos, incluyendo su propia lógica de repositorio y servicio.
* Seguridad y Autenticación: Integración de dependencias de Spring Security.
* Interfaz de Usuario: Creación del menú principal con controladores dedicados, maquetación HTML y diseño visual mediante CSS.

## Solución de Errores
* Refactorización en Entidad Producto: Corrección de errores sintácticos en la clase Producto para asegurar la persistencia y el correcto mapeo con la base de datos.
* Corrección de Tipado en UsuarioRepository: Se detectó un error de sintaxis donde se utilizaba el tipo String en lugar de Long. Esta discrepancia causaba errores de compilación y lógica en la capa de UsuarioService, por lo que se procedió a estandarizar el tipo de dato del ID.
* Ajuste de Wrapper Classes en Usuario: Se corrigió la declaración de variables en la entidad Usuario, cambiando el tipo primitivo long por su Wrapper Class Long. Esto permite un manejo adecuado de valores nulos y mejora la compatibilidad con las especificaciones de JPA/Hibernate.ong a Long