package io.gustavo.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.gustavo.model.Log;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class LogController {
	
	private static final Logger logger = LogManager.getLogger(LogController.class);
	
	private KafkaTemplate<Integer, Log> kafkaTemplate;
	
	public LogController(KafkaTemplate<Integer, Log> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	@PostMapping
	public Mono<String> publishLog(@Valid @RequestBody Log log){
		return Mono.create(response ->{
			ListenableFuture<SendResult<Integer, Log>> future = kafkaTemplate.send("logger", log);
			
			future.addCallback(new ListenableFutureCallback<SendResult<Integer,Log>>(){

				@Override
				public void onSuccess(SendResult<Integer, Log> result) {
					logger.info("-------------------CONSUMER");
					logger.info("TOPIC NAME: "+result.getRecordMetadata().topic());
					logger.info("PARTITION: "+result.getRecordMetadata().partition());
					response.success(result.getProducerRecord().value().toString());
				}

				@Override
				public void onFailure(Throwable ex) {
					logger.error("Error to publish in topic: "+ex.getMessage());
					response.error(ex);
				}
				
			});
		});
	}
}
