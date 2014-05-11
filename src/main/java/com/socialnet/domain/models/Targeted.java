package com.socialnet.domain.models;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@RelationshipEntity(type="TARGETED")
public class Targeted {
	@GraphId private Long id;
	
	@StartNode
	@Fetch
	Event event;
	
	@EndNode
	@Fetch
	Profile profile;
	
	public Targeted(){}

	public Targeted(Event event, Profile profile) {
		this.event = event;
		this.profile = profile;
	}

	@JsonIgnore
	public Event getEvent() {
		return event;
	}

	public Profile getProfile() {
		return profile;
	}

}
