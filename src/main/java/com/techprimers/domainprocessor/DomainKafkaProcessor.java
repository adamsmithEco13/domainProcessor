package com.techprimers.domainprocessor;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.cloudevent.CloudEventMessageBuilder;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

@Configuration
public class DomainKafkaProcessor {

	@Autowired
	StreamBridge streamBridge;

	@Bean
	public Function<KStream<String, String>, KStream<String, String>> domainProcessor() {
		return kstream -> kstream.mapValues(value -> {
			streamBridge.send("hello", value.toUpperCase());
			return value;
		});
	}

}
