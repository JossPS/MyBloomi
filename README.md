🌱 Bloomi - Backend

Bloomi es una aplicación que conecta personas interesadas en apoyar actividades ambientales 
como reforestaciones y mantenimientos, con fundaciones ecológicas registradas. 
Esta API fue desarrollada con Spring Boot y MongoDB Atlas, 
siguiendo principios REST para facilitar su integración con la app móvil.

---

## Tecnologías Utilizadas

- Kotlin** + Spring Boot 3.5
- MongoDB Atlas
- Gradle
- Docker & Docker Compose 
- Postman** (para pruebas de endpoints)

---

##  Estructura del Proyecto

```
src/
 └── main/
     └── kotlin/
         └── com.bloomi.demo/
             ├── controllers/
             ├── exceptions/
             ├── mappers/
             ├── models/
             │   ├── entities/
             │   ├── requests/
             │   └── responses/
             ├── repositories/
             ├── routes/
             └── services/
```

---

##  Endpoints disponibles

### Usuarios

- Crear usuario
    - `POST /api/users`
    - Body:
      ```json
      {
        "name": "Josselyn S",
        "email": "pampam@gmail.com",
        "phone": "0998765432"
      }
      ```

- Listar todos
 - `GET /api/users`

- Obtener por ID
 - `GET /api/users/{id}`

- Eliminar usuario
 - `DELETE /api/users/{id}`

- Inscribir a actividad
 - `POST /api/users/{userId}/join/{activityId}`

- Ver actividades inscritas
 - `GET /api/users/{userId}/activities`

---

### 🔸 Fundaciones

- Crear fundación
- `POST /api/foundations`
  - Body:
    ```json
    {
      "name": "Fundación Árbol Verde",
      "email": "fundacion@verde.org",
      "phone": "0987654321",
      "bankName": "Banco Pichincha",
      "accountType": "ahorros",
      "accountNumber": "1234567890"
    }
    ```

- Listar fundaciones
- `GET /api/foundations`

---

###  Actividades

- Crear actividad
- `POST /api/activities`
  - Body:
    ```json
    {
      "title": "Reforestación en Quito",
      "description": "Plantación de árboles en el parque Metropolitano",
      "type": "REFORESTATION",
      "date": "2025-07-05",
      "location": "Quito, Ecuador",
      "foundationId": "ID_DE_LA_FUNDACION"
    }
    ```

- Listar actividades
 - `GET /api/activities`

---

##  Pruebas en Postman

1. Crear un usuario.
2. Crear una fundación.
3. Crear una actividad asociada a la fundación.
4. Inscribir el usuario en la actividad.
5. Consultar las actividades en las que está inscrito el usuario.

Puedes importar los endpoints manualmente o generar una colección desde estos ejemplos.

---

## Licencia

Este proyecto es de uso académico y libre bajo fines educativos.
