package io.gustavo.configuration;

import java.util.HashMap;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import io.gustavo.model.Log;

@Configuration
public class KafkaProducerConfiguration {

	@Value(value = "${kafka.bootstrap.server}")
    private String bootstrapAddress;


	@Bean
	public ProducerFactory<Integer, Log> producerFactory() {
		var serializer = new JsonSerializer<Log>();
		
		var props = new HashMap<String, Object>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, serializer);
		props.put(ProducerConfig.ACKS_CONFIG, "all");
		
	    return new DefaultKafkaProducerFactory<Integer, Log>(props,new IntegerSerializer(), serializer);
	}
	
	@Bean
	public KafkaTemplate<Integer, Log> kafkaTemplate() {
	    return new KafkaTemplate<Integer, Log>(producerFactory());
	}
}
