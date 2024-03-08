
# Reserva Ecológica - Desarrollo Backend

API - Reserva Ecológica registro y vista de avistamientos

# Indice

- [Tecnologías](#Tecnologías)
- [Swagger (probar API)](#Swagger)
- [Diagrama del modelo de datos](#Diagrama-del-modelo-de-datos-MySQL)
- [Roles](#Roles)
- [Endpoints](#Endpoints)


## Tecnologías
<p align="left"> <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a> <a href="https://www.mysql.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/mysql/mysql-original-wordmark.svg" alt="mysql" width="40" height="40"/> </a> <a href="https://postman.com" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/getpostman/getpostman-icon.svg" alt="postman" width="40" height="40"/> </a> <a href="https://spring.io/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="40" height="40"/> </a> </p>

## Swagger
Despues de haber ejecutado la aplicacion para abrir correctamente swagger:

```
  http://localhost:8000/swagger-ui/index.html#/
```
Para poder probar todos los endpoints necesitas iniciar sesion con el siguiente usuario de prueba:

```http
  POST /account/login
```
```
Request:
  {
  "usernameOrEmail": "admin",
  "password": "admin"
  }

Response:
  {
  "username": "admin",
  "role": "ROLE_PERSONAL_RESERVA",
  "accessToken": "bearer_generated",
  "typeToken": "bearer"
  }
```
Ya con el accesToken disponible hay que copiarlo y pegarlo en la seccion *Available authorizations* de swagger
## Diagrama del modelo de datos `MySQL`

![App Screenshot](bd.png)

## Roles

En principio, los roles se crean directamente desde la base de datos. Quizás más adelante estaría bueno agregar alguna funcionalidad para no tener que harcodearlos, pero, como en principio los roles requeridos son solamente dos, opté por hardcodearlos. 

#### Data:

| Id        | Name                    | Permissions                |
| :-------- | :---------------------- | :------------------------- |
| `1`       | `ROLE_PERSONAL_RESERVA` | **all**.                   |
| `2`       | `ROLE_USER`             | **limited**.               |

## Endpoints
