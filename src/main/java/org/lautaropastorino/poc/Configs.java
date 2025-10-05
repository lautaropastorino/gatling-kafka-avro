package org.lautaropastorino.poc;

import java.time.Duration;
import java.util.Optional;

public class Configs {
    public static final Duration SIM_DURATION = Optional.ofNullable(System.getenv("SIM_DURATION"))
            .map(Duration::parse)
            .orElse(Duration.ofMinutes(5));

    public static final int MESSAGES_PER_SECOND = Optional.ofNullable(System.getenv("MESSAGES_PER_SECOND"))
            .map(Integer::valueOf)
            .orElse(1);

    public static final String KAFKA_BROKERS = Optional.ofNullable(System.getenv("KAFKA_BROKERS"))
            .orElse("kafka:29092");

    public static final String TOPIC = Optional.ofNullable(System.getenv("TOPIC"))
            .orElse("transactions");

    public static final String AWS_REGION = Optional.ofNullable(System.getenv("AWS_REGION"))
            .orElse("us-east-1");

    public static final String SCHEMA_NAME = Optional.ofNullable(System.getenv("SCHEMA_NAME"))
            .orElse("avro-transactions");

    public static final String REGISTRY_NAME = Optional.ofNullable(System.getenv("REGISTRY_NAME"))
            .orElse("concentrador-tx");
}
