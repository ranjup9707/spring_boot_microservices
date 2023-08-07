package com.microservices.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication 
{
	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}
	
	//Kafka Listener
	@KafkaListener(topics = "notificationTopic") //notificationTopic is the name given in OrderService class
	public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
		//Send email notification
		log.info("Received notification for order - {}", orderPlacedEvent.getOrderNumber());
	}
}
