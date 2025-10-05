package org.lautaropastorino.poc;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.PopulationBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import org.apache.kafka.clients.producer.ProducerConfig;
import ru.tinkoff.gatling.kafka.javaapi.KafkaDsl;
import ru.tinkoff.gatling.kafka.javaapi.protocol.KafkaProtocolBuilder;

import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;


public class TransactionsSimulation extends Simulation {
    {
        List<PopulationBuilder> scenarios = List.of(
                TransactionsSimulation.scenario
                        .injectOpen(CoreDsl.atOnceUsers(Configs.MESSAGES_PER_SECOND))
                        .protocols(TransactionsSimulation.kafkaProtocol)
        );

        this.setUp(scenarios);
    }

    public static final KafkaProtocolBuilder kafkaProtocol = KafkaDsl.kafka()
            .topic(Configs.TOPIC)
            .properties(
                    Map.of(
                            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Configs.KAFKA_BROKERS,
                            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer",
                            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class
                    )
            );

    private static final Iterator<Map<String, Object>> infiniteKeyFeeder = new Iterator<>() {
        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Map<String, Object> next() {
            Map<String, Object> map = new HashMap<>();
            map.put("key", UUID.randomUUID().toString());
            return map;
        }
    };

    public static final ScenarioBuilder scenario = CoreDsl.scenario("transactions")
            .during(Configs.SIM_DURATION)
            .on(
                CoreDsl.pace(Duration.ofSeconds(1))
                    .feed(infiniteKeyFeeder)
                    .exec(
                        KafkaDsl.kafka("publish-transactions")
                            .send(
                                    "#{key}",
                                    (Supplier<Transactions>) new TransactionBuilder()::buildAuthorizedTransaction
                            )
                    )
            );
}
