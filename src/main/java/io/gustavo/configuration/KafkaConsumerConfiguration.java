package io.gustavo.configuration;

import java.util.HashMap;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import io.gustavo.model.Log;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {

	@Value(value = "${kafka.bootstrap.server}")
	private String bootstrapAddress;

	@Bean
	public ConsumerFactory<Integer, Log> consumerFactory() {
		var deserializer = new JsonDeserializer<>(Log.class);
	    deserializer.setRemoveTypeHeaders(false);
	    deserializer.addTrustedPackages("*");
	    deserializer.setUseTypeMapperForKey(true);
		
	    var props = new HashMap<String, Object>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-log");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);

		return new DefaultKafkaConsumerFactory<Integer, Log>(props, new IntegerDeserializer(), deserializer);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<Integer, Log> kafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<Integer, Log> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
}
