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
El proyecto está configurado para ejecutarse localmente usando Quarkus Dev Services (que levanta un contenedor Docker automáticamente si está disponible). 
Sin embargo, para ejecutarlo con la misma base de datos de producción o sin Docker, crea un archivo `.env` en el mismo directorio (o exporta las variables) con los siguientes valores exactos:

```env
DB_USERNAME=root
DB_PASSWORD=mcQ.r-d#&2f;rU5-
DB_JDBC_URL=jdbc:mysql://34.46.248.50:3306/todogrupo1
DB_SCHEMA_STRATEGY=update
PORT=8080
```
*(Nota: Para el funcionamiento completo de validación de Auth se espera que exista un archivo `medsync-firebase-adminsdk.json` en la carpeta base si se requiere el modo Dev puro).*

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
