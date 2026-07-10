## Practica Spring Boot

### Estructura del proyecto

<img src="assets/01-estructura.png" width="600">

### Servidor iniciado

<img src="assets/02-servidor-iniciado.png" width="600">

### Respuesta de la API

<img src="assets/03-api-status.png" width="600">

### Respuesta de la API Students

<img src="assets/04-api-students.png" width="600">

### Estructura modular del proyecto

<img src="assets/07-estructura-modular.png" width="300">

### Compilación y ejecución exitosa

<img src="assets/06-ejecucion.png" width="600">

## Importancia de la organización modular
La organización modular permite dividir la aplicación en componentes independientes según su funcionalidad. En este proyecto se separaron los módulos users y products, cada uno con sus respectivos controladores y servicios. Esta estructura es super util ya que facilita el mantenimiento del codigo y mejora la escalabilidad del proyecto, además de permitirnnos trabajar de manera simultanea sin generar conflictos.  

###  Productos creados en PostgreSQL

<img src="assets/08-productos.png" width="600">

El flujo de datos entre la API REST y PostgreSQL se realiza mediante un ORM que traduce las peticiones HTTP en consultas SQL y viceversa, apoyándose en BaseEntity como un estándar de arquitectura. Al entrar, la petición procesa los datos y BaseEntity inyecta automáticamente campos clave como el id y las marcas de tiempo de auditoría (createdAt, updatedAt) antes de que el ORM ejecute la inserción o actualización en la base de datos. Al salir, PostgreSQL devuelve los registros, el ORM los convierte en objetos estructurados que heredan de BaseEntity, y finalmente la API los serializa en formato JSON para entregarlos al cliente.

### Respuesta 400 Bad Request al enviar los datos incorrectos.
<img src="assets/09_usuario_erroneo.png" width="600">

### Eliminación de un producto
<img src="assets/10_eliminado_exitoso.png" width="600">

<img src="assets/10_eliminado_correcto.png" width="600">

### Intento de eliminar un producto eliminado.

<img src="assets/11_intento_eliminacion.png" width="600">

### Actualización Parcial de Contraseña
<img src="assets/12_hashPass.png" width="600">

### Consulta de endpoint de productos con paginación
<img src="assets/13-consulta-page-result.png" width="600">
<img src="assets/13-consulta-page.png" width="600">
<img src="assets/13-consulta1-page.png" width="600">

### Consulta de endpoint de productos con slice
<img src="assets/14-consulta-slice.png" width="600">

### ¿Cuál es la diferencia entre Page y Slice?
Page devuelve el total de elementos y páginas porque ejecuta una consulta COUNT, en cambio, slice solo indica si existe una página siguiente y es más ligero

### ¿Por qué la paginación debe aplicarse en el repositorio y no después de traer todos los datos en memoria?
Porque la base de datos aplica LIMIT y OFFSET antes de enviar los datos. Paginar después de traer todos los registros seguiría consumiendo memoria, tiempo y red innecesariamente.

### Registro exitoso
<img src="assets/15-registro.png" width="600">

### Login exitoso
<img src="assets/16-login.png" width="600">

### Endpoint protegido sin token
<img src="assets/17-endpointSinToken.png" width="600">

### Endpoint protegido con token
<img src="assets/18-endpointConToken.png" width="600">