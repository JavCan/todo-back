# ToDo Backend (Quarkus)

Este es el backend oficial del ecosistema de la aplicación ToDo, que da soporte tanto a clientes web como móviles. Está construido para ser rápido, eficiente en memoria y escalable mediante Quarkus.

## 🚀 Tecnologías Utilizadas
- **Java 21**
- **Quarkus** (Framework Java nativo de Kubernetes)
- **Hibernate ORM con Panache** (Capa de datos)
- **MySQL** (Base de datos en producción)
- **H2** (Base de datos en memoria para testing)
- **Firebase Admin SDK** (Validación de tokens JWT)
- **Maven** (Gestor de dependencias)

## 🛠 Instalación

1. Clona el repositorio.
2. Asegúrate de tener instalado Java 21.
3. Descarga las dependencias e inicializa el proyecto:
   ```bash
   ./mvnw clean install
   ```

## ⚙️ Variables de Entorno
Para ejecutar el proyecto, especialmente en producción, se deben configurar las siguientes variables de entorno:

- `DB_USERNAME`: Usuario de la base de datos MySQL (por defecto `root`).
- `DB_PASSWORD`: Contraseña de la base de datos MySQL.
- `DB_JDBC_URL`: URL de conexión a la base de datos (ej: `jdbc:mysql://34.46.248.50:3306/todogrupo1`).
- `DB_SCHEMA_STRATEGY`: Estrategia de inicialización de esquema (por defecto `update`).
- `FIREBASE_SERVICE_ACCOUNT_LOCATION`: Ruta al archivo JSON con las credenciales del servicio de Firebase Admin SDK.
- `APP_VERSION`: Versión de la app (útil para el endpoint de status).
- `PORT`: Puerto en el que corre la aplicación (por defecto `8080`).

## 🏃 Cómo Ejecutar el Proyecto

Para correr la aplicación en modo desarrollo (con recarga en vivo):
```bash
./mvnw compile quarkus:dev
```
La API estará disponible en `http://localhost:8080`.

## 🔗 Links Deployados
- **URL Base de la API (Producción):** `https://todo-backend-424512506456.europe-west1.run.app`

## 👥 Usuarios de Prueba
Al utilizar Firebase Authentication, puedes crear un nuevo usuario directamente desde la aplicación móvil o web. Si prefieres usar un usuario de prueba existente (dependiendo de la configuración de tu entorno de Firebase), inicia sesión en el frontend para generar y pasar automáticamente el JWT a este backend.
