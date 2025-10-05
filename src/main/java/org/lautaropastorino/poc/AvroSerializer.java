package org.lautaropastorino.poc;

import com.amazonaws.services.schemaregistry.serializers.avro.AWSKafkaAvroSerializer;
import com.amazonaws.services.schemaregistry.utils.AWSSchemaRegistryConstants;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;
import java.util.function.Supplier;

public final class AvroSerializer
        implements Serializer<Supplier<Transactions>> {

    private final Map<String, Object> configs = Map.of(
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AWSKafkaAvroSerializer.class,
            AWSSchemaRegistryConstants.AWS_REGION, Configs.AWS_REGION,
            AWSSchemaRegistryConstants.SCHEMA_NAME, Configs.SCHEMA_NAME,
            AWSSchemaRegistryConstants.REGISTRY_NAME, Configs.REGISTRY_NAME,
            AWSSchemaRegistryConstants.SCHEMA_AUTO_REGISTRATION_SETTING, true
    );

    private final AWSKafkaAvroSerializer awsKafkaAvroSerializer = new AWSKafkaAvroSerializer(configs);

    @Override
    public byte[] serialize(String s, Supplier<Transactions> transactionAvroSchema) {
        return awsKafkaAvroSerializer.serialize(s, transactionAvroSchema.get());
    }
}
