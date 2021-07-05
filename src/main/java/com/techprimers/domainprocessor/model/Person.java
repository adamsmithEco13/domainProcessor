package com.techprimers.domainprocessor.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Person {

	@JsonProperty("name")
	String name;
	
	@JsonProperty("topic")
	String topic;
	
	@Override
	public String toString() {
		return String.format("name: %s, topic: %s .", name, topic);
	}
	
}
