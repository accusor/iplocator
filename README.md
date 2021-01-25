#iplocator
##Prueba técnica Appgate
Método HTTP | Ruta | Descripcion
----------- | ---- | -----------
GET | `iplocator/api/v1/loadDatabase` | Realiza la carga del archivo a la base de datos del sistema
GET | `iplocator/api/v1/getStatus` | Devuelve la información del estado del proceso de carga del archivo
GET | `iplocator/api/v1/ranges` | Devuelve la información de todos los registros Range cargados en el sistema
GET | `iplocator/api/v1/ranges/{id}` | Devuelve la información del registro Range con el id enviado en el parámetro
GET | `iplocator/api/v1/locations` | Devuelve la información de todos los registros Location cargados en el sistema
GET | `iplocator/api/v1/locations/{id}` | Devuelve la información del registro Location con el id enviado en el parámetro
GET | `iplocator/api/v1//locationByIp/{ip}` | Devuelve la información del localización de la ip enviada en el parámetro