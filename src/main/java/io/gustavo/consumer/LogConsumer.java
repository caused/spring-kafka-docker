package io.gustavo.consumer;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

import io.gustavo.model.Log;

@Configuration
public class LogConsumer {
	
	@KafkaListener(topics = "logger", groupId = "consumer-log")
	public void listenGroupFoo(Log log) {
	    System.out.println("Received Message in group consumer-log: " + log.getStatus() + " - " +log.getMessage());
	}
}
