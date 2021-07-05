package com.techprimers.domainprocessor;

import org.apache.kafka.streams.kstream.KStream;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.util.function.Function;

@Configuration
public class DomainKafkaProcessor {

	@Autowired
	StreamBridge streamBridge;

	@Autowired
	RestHighLevelClient highLevelClient;

	@Bean
	public Function<KStream<String, String>, KStream<String, String>> domainProcessor() {
		return kstream -> kstream.mapValues(value -> {
			this.get();
			return value.toUpperCase();
		});
	}
	
	public void get() {
		GetRequest  request = new GetRequest ("dev").id("1");
		GetResponse response = null;
		try {
			response = highLevelClient.get(request, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(response);
		
	}


}
