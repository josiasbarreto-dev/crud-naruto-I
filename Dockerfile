# Estágio 1: Builder
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copie o arquivo pom.xml
COPY pom.xml .

# Copie o código fonte
COPY src ./src

# Adicione esta linha para copiar o arquivo .env
COPY .env .

# Execute o build do projeto Maven
RUN mvn clean package -DskipTests

# Estágio 2: JRE de execução
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copie o JAR gerado no estágio de build
COPY --from=builder /app/target/*.jar app.jar

# Especifique o comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]