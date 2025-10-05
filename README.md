# gatling-kafka-avro

## English

[[Leer en español]](#Español)

This project includes a proof of concept that uses Gatling to create events in a Kafka topic in Avro format through the AWS Glue Schema Registry.

## Execution

###  Requirements

- Maven and Java 21 or Docker
- AWS account with access to Glue Schema Registry

### Configuration

Execution requires the following environment variables:

```bash
AWS_ACCESS_KEY_ID=<access key>
AWS_SECRET_ACCESS_KEY=<secret key>
AWS_SESSION_TOKEN=<session token>
AWS_REGION=us-east-1
KAFKA_BROKERS=localhost:29092
TOPIC=avro-transactions
SCHEMA_NAME=avro-transactions
REGISTRY_NAME=concentrador-tx
SIM_DURATION=PT1M
```

The AWS user must have access to the Glue Schema Registry.

### Execution modes

#### 1. Using Maven

Run the following command:

```bash
mvn gatling:test
```

#### 2. Build and run the JAR

Package the project and generate the JAR with the command:

```bash
mvn clean package
```

Run the JAR with:

```bash
java -jar target/transactions-loader-1.0-SNAPSHOT-jar-with-dependencies.jar -s org.lautaropastorino.poc.TransactionsSimulation
```

#### 3. Run with Docker

Run using the `docker` command:

```bash
docker run --rm \
    -e AWS_ACCESS_KEY_ID="$AWS_ACCESS_KEY_ID" \
    -e AWS_SECRET_ACCESS_KEY="$AWS_SECRET_ACCESS_KEY" \
    -e AWS_REGION="$AWS_REGION" \
    -e KAFKA_BROKERS="$KAFKA_BROKERS" \
    -e TOPIC="$TOPIC" \
    -e SCHEMA_NAME="$SCHEMA_NAME" \
    -e REGISTRY_NAME="$REGISTRY_NAME" \
    -e SIM_DURATION="$SIM_DURATION" \
    lautaropastorino/gatling-kafka-avro:1.2
```

Run using docker-compose:

```yml
services:
  gatling:
    image: lautaropastorino/gatling-kafka-avro:1.2
    container_name: gatling
    env_file:
      - ./.env
    network_mode: "host"
```

## Code

The test generates Kafka events in the specified topic with a `key` of type String composed of a random UUID, and a `value` in Avro format defined by [transactionAvroSchema.avsc](src\main\resources\avro\transactionAvroSchema.avsc). The producer connects to AWS Glue Schema Registry to register and validate the schema's compatibility.

## Español

[[Read in english]](#English)

Este proyecto incluye una prueba de concepto donde se utiliza [Gatling](https://www.gatling.io/) para crear eventos en un tópico de Kafka en formato Avro a través del AWS Glue Schema Registry.

## Ejecución

###  Requerimientos
 
- Maven y Java 21 o Docker
- Cuenta de AWS con acceso a Glue Schema Registry

### Configuración

La ejecución requiere las siguientes variables de entorno:

```bash
AWS_ACCESS_KEY_ID=<access key>
AWS_SECRET_ACCESS_KEY=<secret key>
AWS_SESSION_TOKEN=<session token>
AWS_REGION=us-east-1
KAFKA_BROKERS=localhost:29092
TOPIC=avro-transactions
SCHEMA_NAME=avro-transactions
REGISTRY_NAME=concentrador-tx
SIM_DURATION=PT1M
```

El usuario de AWS debe tener acceso a Glue Schema Registry.

### Modos de ejecución

#### 1. Utilizando maven

Ejecutar mediante el comando 

```bash
mvn gatling:test
```

#### 2. Generar y ejecutar el jar

Empaquetar el proyecto generando el jar mediante el comando

```bash
mvn clean package
```

Ejecutar el jar mediante el comando

```bash
java -jar target/transactions-loader-1.0-SNAPSHOT-jar-with-dependencies.jar -s org.lautaropastorino.poc.TransactionsSimulation
```

#### 3. Ejecutar desde docker

Ejecutar mediante el comando `docker`

```bash
docker run --rm \
    -e AWS_ACCESS_KEY_ID="$AWS_ACCESS_KEY_ID" \
    -e AWS_SECRET_ACCESS_KEY="$AWS_SECRET_ACCESS_KEY" \
    -e AWS_REGION="$AWS_REGION" \
    -e KAFKA_BROKERS="$KAFKA_BROKERS" \
    -e TOPIC="$TOPIC" \
    -e SCHEMA_NAME="$SCHEMA_NAME" \
    -e REGISTRY_NAME="$REGISTRY_NAME" \
    -e SIM_DURATION="$SIM_DURATION" \
    lautaropastorino/gatling-kafka-avro:1.2
```

Ejecutar a través de docker-compose

```yml
services:
  gatling:
    image: lautaropastorino/gatling-kafka-avro:1.2
    container_name: gatling
    env_file:
      - ./.env
    network_mode: "host"
```

## Código

La prueba crea eventos de Kafka en el tópico indicado con una `key` de tipo String formada por un UUID aleatorio y con un `value` con formato Avro definido por el [transactionAvroSchema.avsc](src\main\resources\avro\transactionAvroSchema.avsc). El produtor se conecta a AWS Glue Schema Registry para registrar y validar la compatibilidad del esquema.