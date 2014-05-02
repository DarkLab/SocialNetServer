package com.socialnet.domain.models;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@RelationshipEntity(type = "KNOWN_AS")
public class Alias {
	@GraphId
	private Long id;
	@StartNode
	@Fetch
	SNUser user;
	@EndNode
	@Fetch
	Profile profile;

	public Alias() {
	}

	public Alias(SNUser user, Profile socialIdentity) {
		this.user = user;
		this.profile = socialIdentity;
	}
	
	@JsonIgnore
	public SNUser getUser(){
		return user;
	}
	
	public Profile getProfile(){
		return profile;
	}

	@Override
	public String toString() {
		return String.format("%s-[%s]->%s", this.user, "known as",
				this.profile.getIdentifier());
	}
}
