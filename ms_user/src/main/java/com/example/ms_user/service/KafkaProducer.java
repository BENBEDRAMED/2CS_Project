package com.example.ms_user.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private static final String TOPIC = "driver-availability";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendDriverAvailabilityEvent(String driverId, boolean available) {
        String message = String.format("{\"driverId\": \"%s\", \"available\": %b}", driverId, available);

        System.out.println(" \uD83D\uDCE4 Sending Kafka event: " + message); // ✅ Log here
        kafkaTemplate.send("driver-availability", message);
        kafkaTemplate.send(TOPIC, message);
    }
}