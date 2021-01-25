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

Como una medida inicial de registro de los llamados a los servicios de la aplicación se creó la entidad `CallLog` con los siguientes atributos:
   * idCallLog: Corresponde al identificador del registro
   * date: Corresponde a la fecha de llamado del servicio
   * method: Nombre del servicio llamado
   * remoteAddress: Dirección IP desde la que se llamó el servicio

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
* `logging.file.path`: Corresponde a la ruta donde se guardaran los logs de la aplicación cuando se ejecute
* `logging.file.name`: Corresopnde al nombre del archivo que se quiere dejar para los logs. Debe comenzar con el parámetro
`${logging.file.path}` parar que tome la ruta definida en la propiedad anterior. Por ejemplo: 
`${logging.file.path}/iplocator.log`

### 5. Ejecución de la aplicación
*La siguiente descripción para ejecutar la aplicación se explican utilizando Eclipse como IDE de desarrollo.* 

1. Una vez haya descargado el proyecto, haga clic derecho en sobre el Proyecto y seleccione la opción *Maven >> Update Project*. En
el cuadro de diálogo que se despliega seleccione la opción *Force Update of Snapshots/Releases* y luego de clic en *Ok*
1. Haga clic derecho sobre el Proyecto y seleccione la opción *Run As >> Maven Build...* y en la opción *Goals* diligencie 
`clean install`. Haga clic en *Apply* y luego en *Run* 
1. Cuando termine el proceso anterior, haga clic derecho sobre el Proyecto y seleccione la opción *Run As >> Spring Boot App*, 
o haga clic derecho sobre el archivo `src\main\java\com\appgate\iplocator\IpLocatorApplicacion.java` y seleccione
la opción *Run As >> Spring Boot App*. En caso que le aparezca un cuadro de dialogo de *Select Java Application* seleccione la opción
`IpLocatorApplicacion.java` y luego haga Clic en *OK*
1. La aplicación se inicia, por lo que puede acceder a ella través de http://localhost:8080/

Al iniciar la aplicación, la base de datos queda cargada con dos entidades `Range` y una entidad `Location` de muestra para realizar consultas. Las mismas se borran al momento de ejecutar la carga del archivo de geolocalización

### 6. Descripción de los servicios
Todos los servicios al ser consumidos devolveran un objeto JSON con los siguientes campos:
   * `meta`: contiene la información de identificación de la respuesta. Contiene los siguientes elementos
      * `date`: Fecha de respuesta
      * `rsUid`: Identificador único de la respuesta en formato UUID
   * `data`: En caso de error se devuelve nulo. si no, devuelve un objeto representado la respuesta
   * `errors`: En caso de exito se duelve nulo. Si no, devuelve un listado de errores, cada uno con la siguiente estructura
      * `code`: Codigo del error
      * `title`: Título del error
      * `detail`: Descripción del error
      * `source`: Origen del error, puede tomar los valores `CLIENT` si el error es del cliente o
      `SYSTEM` si el error es del sistema

###### i. loadDatabase
Este servicio realiza la carga del archivo definido en la propiedad `iplocator.filepath` del archivo de configuración. El 
procedimiento valida que no se inicie nuevamente la carga del archivo si ya se está ejecutando una actualización. En caso de éxito
de la carga, se enviara el siguiente objeto en el campo `data` de la respuesta:
   * `date`: Fecha de finalización de carga
   * `success`: Booleano que indica el éxito de la operación. Se recibe `true`
   * `desc`: Mensaje descriptivo del éxito de la operación
   
En caso que se llame el servicio y se encuentre corriendo un proceso de carga llamado con anterioridad, se devolvera un objeto de 
error dentro del listado `errors` con las siguiente información:
   * `code`: 2000
   * `title`: Process already runnning
   * `detail`: There's an instance of the process already running. Please wait for it to finish
   * `source`: CLIENT

###### ii. getStatus
Este servicio devuelve la información del estado de carga del archivo. Se enviara el siguiente objeto en el campo `data` de la respuesta:
   * `lastUpdate`: Se recibe un `String` en formato `YYYY/MM/DD HH:mm:ss.SSS` de la última 
   actualización de la base de datos, o `never` si no se ha terminado nunca el proceso
   * `running`: Booleano que indica si el proceso se está ejecutando. Envía `true` si se está ejecutando o `false` si no
   hay una ejecución en proceso
   * `runningSince`: Se recibe un `String` en formato `YYYY/MM/DD HH:mm:ss.SSS` de la fecha en la que se inició el 
   proceso que se está corriendo del archivo. En caso que no se esté ejecutando se recibe `--`
   * `totalRecords`: Se recibe un `Long` indicando el número de registros que se han registrado en la base de datos

###### ii. ranges
Este servicio retorna en el campo `data` un listado de todas las entidades `Range` que se encuentren en la base de datos 
   
En caso que no se encuentre entidades `Range` en la base de datos, se devolvera un objeto de error dentro del 
listado `errors` con las siguiente información:
   * `code`: 5001
   * `title`: Range: empty Database
   * `detail`: There are no Range elements on Database
   * `source`: CLIENT
   
###### iii. ranges/{id}
Este servicio retorna en el campo `data` la entidad `Range` que se encuentren en la base de datos que tenga el `id` enviado
en el parámetro
   
En caso que no se encuentre la entidad con dicho parámetro en la base de datos, se devolvera un objeto de error dentro del 
listado `errors` con las siguiente información:
   * `code`: 5000
   * `title`: Range not Found
   * `detail`: Range could not be found on Database with givend id
   * `source`: CLIENT
   
###### iv. locations
Este servicio retorna en el campo `data` un listado de todas las entidades `Location` que se encuentren en la base de datos 
   
En caso que no se encuentre entidades `Location` en la base de datos, se devolvera un objeto de error dentro del 
listado `errors` con las siguiente información:
   * `code`: 4001
   * `title`: Location: empty Database
   * `detail`: There are no Location elements on Database
   * `source`: CLIENT
   
###### v. locations/{id}
Este servicio retorna en el campo `data` la entidad `Location` que se encuentren en la base de datos que tenga el `id` enviado
en el parámetro
   
En caso que no se encuentre la entidad con dicho parámetro en la base de datos, se devolvera un objeto de error dentro del 
listado `errors` con las siguiente información:
   * `code`: 4000
   * `title`: Location not Found
   * `detail`: Location could not be found on Database with givend id
   * `source`: CLIENT