package com.socialnet.domain.models;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.support.index.IndexType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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

	private Date dateCreated;
	private String rant;
	private Feeling feeling;
	private Double latitude, longitude;
	
	@Indexed(indexType = IndexType.POINT, indexName = "locations")
	private String wkt;

	public Event(){};
	
	public Event(Feeling feeling, String rant, double lon, double lat) {
		this.dateCreated = new Date();
		this.feeling = feeling;
		this.rant = rant;
		
		if (lat != 0 && lon != 0) {
			this.longitude = lon;
			this.latitude = lat;
			//this.wkt = String.format("POINT(%f %f)", lon, lat).replace(",", ".");
			this.updateWkt();
		}
	}

	public String getRant() {
		return rant;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getDateCreated() {
		return dateCreated;
	}

	public Feeling getFeeling() {
		return feeling;
	}
	
	public String getWkt(){
		return wkt;
	}

	private void updateWkt(){
		this.wkt = String.format("POINT( %.2f %.2f )", this.getLongitude(), this.getLatitude());
	}
  
	public void setWkt(double longitude, double latitude){
		this.setLongitude(longitude);
		this.setLatitude(latitude);
    
		this.updateWkt();
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}
