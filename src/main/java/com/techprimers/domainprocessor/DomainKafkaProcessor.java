package com.techprimers.domainprocessor;

import org.apache.kafka.streams.kstream.KStream;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techprimers.domainprocessor.model.Person;

import java.io.IOException;
import java.util.function.Function;

@Configuration
public class DomainKafkaProcessor {

	@Autowired
	StreamBridge streamBridge;

	@Autowired
	RestHighLevelClient highLevelClient;
	
    @Autowired
    private ObjectMapper objectMapper;

	@Bean
	public Function<KStream<String, String>, KStream<String, String>> domainProcessor() {
		return kstream -> kstream.mapValues(value -> {
			Person person = this.getPerson(value);
			if(person == null) {
				return null;
			}
			return person.toString();
		});
	}
	
	public Person getPerson(String id) {
		GetRequest  request = new GetRequest ("dev2").id(id);
		GetResponse response = null;
		Person person = null;
		try {
			response = highLevelClient.get(request, RequestOptions.DEFAULT);
			if(!response.isExists()){
				return null;
			}
			String personString  = response.getSourceAsString();
			person = objectMapper.readValue(personString, Person.class);				
		} catch (IOException e) {
			e.printStackTrace();
		}
		return person;
	}
	
	
	public String percolate(String message) {
		GetRequest  request = new GetRequest ("dev2").id(id);
		GetResponse response = null;
		Person person = null;
		try {
			response = highLevelClient.get(request, RequestOptions.DEFAULT);
			if(!response.isExists()){
				return null;
			}
			String personString  = response.getSourceAsString();
			person = objectMapper.readValue(personString, Person.class);				
		} catch (IOException e) {
			e.printStackTrace();
		}
		return person;
	}
	
	


}
