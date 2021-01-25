# iplocator
*Nota: Este archivo se visualiza mejor desde github*
### Prueba técnica Appgate
El objetivo de este proyecto es implementar una bateria de servicios REST que permitan realizar la carga de 
un archivo plano con la información de geolocalización por IP a una Base de Datos relacional y diferentes métodos
de consulta sobre la base de datos creada
### Tabla de Contenidos
1. ¿Qué se desarrolló?
1. Tecnologías utilizadas
1. Principales decisiones de diseño
1. Configuración de la aplicación
1. Ejecución de la aplicación
1. Descripción de los servicios
   1. loadDatabase
   1. getStatus
   1. ranges
   1. ranges/{id}
   1. locations
   1. locations/{id}
   1. locationByIp/{ip}
1. Siguientes pasos
	
### 1. ¿Qué se desarrolló?
Se desarrolló las siguiente batería de servicios REST para cargar la información de un archivo plano con con la información de geolocalización por IP a una Base de Datos relacional

Servicio No. | Método HTTP | Ruta | Descripcion
------------ | ----------- | ---- | -----------
1 | GET | `iplocator/api/v1/loadDatabase` | Realiza la carga del archivo a la base de datos del sistema
2 | GET | `iplocator/api/v1/getStatus` | Devuelve la información del estado del proceso de carga del archivo
3 | GET | `iplocator/api/v1/ranges` | Devuelve la información de todos los registros Range cargados en el sistema
4 | GET | `iplocator/api/v1/ranges/{id}` | Devuelve la información del registro Range con el id enviado en el parámetro
5 | GET | `iplocator/api/v1/locations` | Devuelve la información de todos los registros Location cargados en el sistema
6 | GET | `iplocator/api/v1/locations/{id}` | Devuelve la información del registro Location con el id enviado en el parámetro
7 | GET | `iplocator/api/v1/locationByIp/{ip}` | Devuelve la información del localización de la ip enviada en el parámetro

### 2. Tecnologías utilizadas
* Spring Boot 2.4.2
* JDK - 1.8
* Spring Framework 5.3.3
* Hibernate 5.4.27
* Maven 3.6.3+
* Spring Data JPA 2.4.3
* Jersey 
* IDE - Eclipse 2.32
* H2 1.4.200

### 3. Principales decisiones de diseño
El desarollo se realizó sobre el framework de Spring Boot ya es una herramienta versatil que permite realizar desarrollos con rapidez,
con un consumo de memoria mínimo, generando una aplicación Spring con una estructura Maven de fácil configuración.
Como Base de Datos Relacional se eligió H2 ya que permite crear la estructura de las tablas basandose en la entidades de JPA,
y manteniendo la información de la persistencia en la memoria de la aplicación y no en disco. Esto con el fin de no guardar
ningún tipo de información confidencial que se encuentra en los archivos procesados. Gracias a que se utiliza JPA como API
para la persistencia, se podría realizar el cambio de H2 a otra base de datos relacional como MySQL o PostgreSQL a través de los
archivos de configuración de la aplicación.

Al validar la información del archivo recibido, se identifica que la información de los campos *Country_code, Country, Region, City, Latitude, Longitude, ISP* se pueden repetir para distintos rangos de IP, po lo que se decidió crear dos entidades para la Base de Datos:

   * `Range`: para persistir la información de los rangos de IP, creando una relación *ManyToOne* con la entidad `Location` a través del campo `location_id_location`
   * `Location`: para persistir la información de las localizaciones, creando una relación *OneToMany* con la entidad `Range` a través del campo `id_location`

Para lograr esto, en el procedimiento de carga del archivo de base de datos se valida si la localización de un rango de IPs ya está
creado en el sistema, si está creado busca el id con el que quedó registrado y lo asigna al rango, si no está creado, lo registra en
la base de datos

### 4. Configuración de la aplicación
La configuración de la aplicación se realiza a través del archivo `src/main/resources/application.properties`. Las 
propiedades que el usuario debe cambiar son las siguientes:

* `iplocator.filepath`: Corresponde a la ubicación del archivo csv a cargar con la información de la geolocalización de las IPs
* `spring.datasource.username`: Corresponde al nombre de usuario para conectarse a la base de datos de H2
si se dejó la configuración por defecto debe ser `sa`
* `spring.datasource.password`: Corresponde a la contraseña del usuario para conectarse a la base de datos. Por defecto será
vació
* `spring.h2.console.enabled`: Si se desea poder entrar a la consola de H2 a realizar consultas se debe dejar como `true`.
En caso que se quiera desactivar se pone como `false`
*  `spring.h2.console.path`: Corresponde a la ruta que se desea dejar para acceder a la consola de H2. Por defecto se tiene
`/h2-console` que configura la aplicación para acceder a la consola a través de la URL http://localhost:8080/h2-console/