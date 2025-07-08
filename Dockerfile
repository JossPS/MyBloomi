FROM eclipse-temurin:21-jdk

WORKDIR /app

#Copia todos los archivos del proyecto
COPY . .

# Da permisos a gradlew (por si no tiene)
RUN chmod +x ./gradlew

# Construye el proyecto con Gradle
RUN ./gradlew build -x test --no-daemon

# Expone el puerto definido en application.yml
EXPOSE 8081

# Ejecuta el .jar generado (ajusta el nombre del jar si es distinto)
CMD ["java", "-jar", "build/libs/demo-0.0.1-SNAPSHOT.jar"]
