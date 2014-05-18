package com.socialnet.domain.models;

import java.util.Date;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Day {
	
	@GraphId
	private Long id;
	
	private static DateTimeFormatter formatter = 
	        DateTimeFormat.forPattern("yyyyMMdd");
	
	@Indexed(unique = true)
	private String date;

	public Day() {}
	
	public Day(Date mDate){
		date = formatter.print(mDate.getTime());
	}

	public String getDate() {
		return date;
	}

}
