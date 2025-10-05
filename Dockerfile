FROM eclipse-temurin:21-jre

WORKDIR /app
COPY target/transactions-loader-1.0-SNAPSHOT-jar-with-dependencies.jar /app/app.jar

CMD ["java", "-jar", "app.jar", "-s", "org.lautaropastorino.poc.TransactionsSimulation"]