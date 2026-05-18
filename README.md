# 👤 Microservicio de Usuario

Microservicio encargado de la gestión de usuarios para la plataforma e-commerce.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen)
![Maven](https://img.shields.io/badge/Maven-Build-blue)
![Oracle DB](https://img.shields.io/badge/Oracle-Database-red)

---

## 📋 Descripción

Este microservicio permite administrar usuarios dentro del sistema, incluyendo:

- Registro de nuevos usuarios
- Consulta de información
- Actualización de datos
- Eliminación de usuarios
- Validación de correos únicos
- Integración con autenticación y otros microservicios

---

## 🚀 Funcionalidades principales

✅ Registro de usuarios  
✅ Consulta por ID  
✅ Actualización de información  
✅ Eliminación lógica/física  
✅ Validación de email único  
✅ Integración con autenticación  

---

## 🌐 Endpoints principales

| Método | Endpoint | Descripción |
|---|---|---|
| `POST` | `/usuarios` | Registrar usuario |
| `GET` | `/usuarios/{id}` | Obtener usuario por ID |
| `PUT` | `/usuarios/{id}` | Actualizar usuario |
| `DELETE` | `/usuarios/{id}` | Eliminar usuario |

---

## 🔗 Integraciones

| Microservicio | Propósito |
|---|---|
| Auth Service | Login y validación de credenciales |
| Ordenes Service | Asociación de órdenes a usuarios |
| Carrito Service | Asociación de carrito de compras |

---

## 🛠️ Tecnologías utilizadas

- ☕ Java 17
- 🌱 Spring Boot
- 📦 Maven
- 🗄️ Oracle Database

---

## 📂 Estructura del proyecto

```bash
src/
 ├── controller
 ├── service
 ├── repository
 ├── model
 ├── dto
 └── config
```

---

## ▶️ Ejecución del proyecto

### Ejecutar con Maven Wrapper

```bash
./mvnw spring-boot:run
```

### O en Windows

```powershell
mvnw.cmd spring-boot:run
```

---

## ⚙️ Configuración

Configurar las credenciales de base de datos y wallet Oracle en:

```properties
src/main/resources/application.properties
```

Ejemplo:

```properties
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```

---

## 📌 Requisitos

- Java 17+
- Maven 3.9+
- Oracle Database
- Wallet Oracle configurado

---

## 👨‍💻 Autor

Proyecto desarrollado para plataforma e-commerce basada en microservicios.