package com.socialnet.domain.models;

import org.joda.time.DateTime;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Event {
	public enum Feeling{
		Hatred,
		Dislike,
		Like,
		Love
	}

	@GraphId
	private Long id;

	private DateTime dateCreated;
	private String rant;
	private Feeling feeling;

	public Event(Feeling feeling, String rant) {
		this.dateCreated = new DateTime();
		this.feeling = feeling;
		this.rant = rant;
	}

	public String getRant() {
		return rant;
	}

	public DateTime getDateCreated() {
		return dateCreated;
	}

	public Feeling getFeeling() {
		return feeling;
	}

}
