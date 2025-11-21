# Tecnologías utilizadas

* Java 17
* Spring Boot 3
* Spring Web
* Spring Data JPA
* H2 Database
* Spring Security + JWT
* Lombok
* Swagger / OpenAPI
* JUnit 5 + Mockito

## Como probar?

En el proyecto se encuentra una carpeta de nombre postman que tiene un archivo JSON llamado Nisum.postman_collection.json que tiene los endpoint para probar el registro de usuarios.

Para importar sigue las siguientes instrucciones:
    
### 1. Abrir Postman

Inicia la aplicación Postman (versión desktop o web).

### 2. Ubica el botón Import

En la parte superior izquierda verás el botón:

👉 Import, 
haz clic.

### 3. Selecciona el archivo JSON

Aparecerá un modal con varias opciones:

* File
* Raw Text
* Link
* Code Repository

Elige File → Choose Files

Luego selecciona tu archivo .json desde tu computador.

### 4. Confirma la importación

Haz clic en:

👉 Import

Listo.
Tu colección o entorno aparecerá en el sidebar izquierdo.


NOTA: EL PROYECTO PREVIAMENTE DEBE ESTAR EN EJECUCION EN EL IDE O UN CONTENEDOR DOCKER.


# Diagrama de la solución.



                          ┌───────────────────────────────┐
                          │          CLIENTE              │
                          │ (Postman, Frontend, Mobile)   │
                          └───────────────┬───────────────┘
                                          │ HTTP/JSON
                                          ▼
                           ┌────────────────────────────────┐
                           │          CONTROLLERS           │
                           │  - AuthController              │
                           │  - UserController              │
                           │  - PruebaController            │
                           └───────────────┬────────────────┘
                                           │
                              Request DTO  │  Response DTO
                       RegisterRequest ◄───┘───► UserResponse
                       AuthRequest   
                                           │
                                           ▼
                          ┌─────────────────────────────────┐
                          │             SERVICE              │
                          │   UserService / AuthService     │
                          │                                 │
                          │  - Validación email/regex       │
                          │  - Validación password/regex    │
                          │  - Lógica de negocio            │
                          │  - Manejo de excepciones        │
                          │  - Generación JWT               │
                          └───────────────┬─────────────────┘
                                          │ (usa)
                                          ▼
                          ┌──────────────────────────────────┐
                          │            REPOSITORIES          │
                          │  - UserRepository                │
                          │  - PhoneRepository               │
                          │  - TokenRepository               │
                          └───────────────┬──────────────────┘
                                          │ (operaciones JPA)
                                          ▼
                          ┌──────────────────────────────────┐
                          │               JPA                │
                          │  Entities:                       │
                          │   User                           │
                          │   Phone                          │
                          │   Token                          │
                          │                                  │
                          └───────────────┬──────────────────┘
                                          │
                                          ▼
                           ┌─────────────────────────────────┐
                           │               BD H2             │
                           │   Tablas:                       │
                           │   USERS                         │
                           │   PHONES                        │
                           │   TOKENS                        │
                           └─────────────────────────────────┘

───────────────────────────────────────────────────────────────

Seguridad JWT (Intercepción)
───────────────────────────────────────────────────────────────

              ┌──────────┐           ┌───────────────────┐
    Request → │ Filter   │ → JWT →   │ Authentication    │ → Controller
              └──────────┘           │ (UserDetailsSrv)  │
                                     └───────────────────┘