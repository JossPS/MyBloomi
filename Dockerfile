# Usa una imagen base de JDK 17
FROM eclipse-temurin:21-jdk

# Crea una carpeta de trabajo
WORKDIR /app

# Define una variable de entorno de build
ARG MONGODB_URI

# Exporta la variable para que esté disponible en tiempo de build
ENV MONGODB_URI=$MONGODB_URI

# Copia todos los archivos del proyecto
COPY . .

# Da permisos a gradlew (por si no tiene)
RUN chmod +x ./gradlew

# Construye el proyecto con Gradle
RUN ./gradlew build --no-daemon

# Expone el puerto definido en application.yml
EXPOSE 8081

# Ejecuta el .jar generado (ajusta el nombre del jar si es distinto)
CMD ["java", "-jar", "build/libs/demo-0.0.1-SNAPSHOT.jar"]
