package com.socialnet.domain.models;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@RelationshipEntity(type="ORIGINATED")
public class Originated {
	@GraphId private Long id;
	
	@StartNode
	@Fetch
	Profile profile;
	
	@EndNode
	@Fetch
	Event event;
	
	public Originated(){
		
	}

	public Originated(Profile profile, Event event) {
		this.profile = profile;
		this.event = event;
	}

	@JsonIgnore
	public Profile getProfile() {
		return profile;
	}

	public Event getEvent() {
		return event;
	}

}
