# 👤 Microservicio de Usuario

Microservicio encargado de la gestión de usuarios para la plataforma e-commerce basada en arquitectura de microservicios.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen)
![Maven](https://img.shields.io/badge/Maven-Build-blue)
![Oracle DB](https://img.shields.io/badge/Oracle-Database-red)
![Swagger](https://img.shields.io/badge/OpenAPI-Swagger-green)
![HATEOAS](https://img.shields.io/badge/HATEOAS-Enabled-blue)

---

# 📋 Descripción

Este microservicio permite administrar usuarios dentro del sistema e-commerce, proporcionando operaciones CRUD completas, validaciones, documentación OpenAPI y soporte HATEOAS para la navegación entre recursos.

Entre sus responsabilidades se encuentran:

* Registro de nuevos usuarios
* Consulta de usuarios
* Actualización de información
* Eliminación de usuarios
* Validación de correos electrónicos únicos
* Integración con otros microservicios
* Documentación automática mediante Swagger/OpenAPI
* Navegación REST mediante HATEOAS

---

# 🚀 Funcionalidades principales

✅ Registro de usuarios

✅ Consulta de usuarios por ID

✅ Consulta de usuarios por email

✅ Actualización de información

✅ Eliminación de usuarios

✅ Validación de email único

✅ Integración con autenticación

✅ Documentación Swagger/OpenAPI

✅ API HATEOAS (versión v2)

✅ DTOs para transferencia de datos

✅ Manejo centralizado de excepciones

✅ Pruebas unitarias con JUnit y Mockito

---

# 🌐 Endpoints disponibles

## API Tradicional

| Método | Endpoint                  | Descripción              |
| ------ | ------------------------- | ------------------------ |
| POST   | `/usuarios/registro`      | Registrar usuario        |
| GET    | `/usuarios`               | Listar usuarios          |
| GET    | `/usuarios/{id}`          | Obtener usuario por ID   |
| GET    | `/usuarios/email/{email}` | Buscar usuario por email |
| PUT    | `/usuarios/{id}`          | Actualizar usuario       |
| DELETE | `/usuarios/{id}`          | Eliminar usuario         |

---

## API HATEOAS

| Método | Endpoint                     |
| ------ | ---------------------------- |
| POST   | `/v2/usuarios/registro`      |
| GET    | `/v2/usuarios`               |
| GET    | `/v2/usuarios/{id}`          |
| GET    | `/v2/usuarios/email/{email}` |
| PUT    | `/v2/usuarios/{id}`          |
| DELETE | `/v2/usuarios/{id}`          |

---

# 🔗 HATEOAS

La versión 2 de la API incorpora HATEOAS (Hypermedia as the Engine of Application State), permitiendo que cada recurso incluya enlaces relacionados para facilitar la navegación de la API.

## Ejemplo

```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Perez",
  "email": "juan@gmail.com",
  "rol": "USUARIO",
  "_links": {
    "self": {
      "href": "/v2/usuarios/1"
    },
    "usuarios": {
      "href": "/v2/usuarios"
    },
    "buscar-por-email": {
      "href": "/v2/usuarios/email/juan@gmail.com"
    }
  }
}
```

---

# 📖 Documentación Swagger / OpenAPI

La documentación interactiva se genera automáticamente mediante SpringDoc OpenAPI.

## Swagger UI

```text
http://localhost:8080/swagger-ui/index.html
```

## OpenAPI JSON

```text
http://localhost:8080/v3/api-docs
```

La documentación incluye:

* Endpoints disponibles
* DTOs de entrada y salida
* Parámetros requeridos
* Códigos de respuesta HTTP
* Descripciones detalladas
* Seguridad mediante JWT (si aplica)

---

# 📦 DTOs

Para desacoplar la capa REST del modelo de persistencia se utilizan DTOs.

## UsuarioRequest

Utilizado para:

* Registrar usuarios
* Actualizar usuarios

## UsuarioResponse

Utilizado para:

* Consultas
* Respuestas REST
* Recursos HATEOAS

---

# 🔐 Seguridad

El microservicio se encuentra preparado para integrarse con Spring Security y autenticación basada en JWT.

Funciones:

* Autenticación mediante token JWT
* Protección de endpoints
* Control de acceso basado en roles
* Integración con Auth Service

---

# 🔗 Integraciones

| Microservicio   | Propósito                          |
| --------------- | ---------------------------------- |
| Auth Service    | Login y validación de credenciales |
| Orden Service   | Asociación de órdenes a usuarios   |
| Carrito Service | Asociación de carritos de compra   |

---

# 🛠️ Tecnologías utilizadas

* Java 17
* Spring Boot 3
* Spring Data JPA
* Spring Validation
* Spring Security
* Spring HATEOAS
* SpringDoc OpenAPI (Swagger)
* Maven
* Oracle Database
* Lombok
* JUnit 5
* Mockito

---

# 📂 Estructura del proyecto

```bash
src/
 ├── assembler
 ├── controller
 ├── dto
 ├── model
 ├── repository
 ├── service
 ├── security
 ├── exception
 ├── config
 └── UsuarioServiceApplication.java
```

## Descripción de carpetas

| Carpeta    | Función                      |
| ---------- | ---------------------------- |
| controller | Endpoints REST               |
| assembler  | Recursos HATEOAS             |
| service    | Lógica de negocio            |
| repository | Acceso a datos               |
| dto        | Objetos de transferencia     |
| model      | Entidades JPA                |
| security   | Configuración JWT/Security   |
| exception  | Manejo global de excepciones |
| config     | Configuraciones generales    |

---

# 🧪 Pruebas

El proyecto incorpora pruebas unitarias utilizando:

* JUnit 5
* Mockito
* MockMvc

Cobertura aplicada sobre:

* Controllers
* Services
* Security
* Exception Handlers
* DTOs
* HATEOAS Assemblers

---

# ▶️ Ejecución del proyecto

## Maven Wrapper

```bash
./mvnw spring-boot:run
```

## Windows

```powershell
mvnw.cmd spring-boot:run
```

---

# ⚙️ Configuración

Configurar las credenciales de conexión Oracle en:

```properties
src/main/resources/application.properties
```

Ejemplo:

```properties
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```

También se deben configurar las propiedades JWT cuando corresponda:

```properties
jwt.secret=
jwt.expiration=
```

---

# 📌 Requisitos

* Java 17 o superior
* Maven 3.9 o superior
* Oracle Database
* Oracle Wallet configurado
* Acceso a los microservicios relacionados

---

# 👨‍💻 Autor

Proyecto desarrollado como parte de una plataforma e-commerce basada en arquitectura de microservicios utilizando Spring Boot, Oracle Database, Swagger/OpenAPI y HATEOAS.
