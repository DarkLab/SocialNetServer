package com.socialnet.service.timeline;

import java.util.Date;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.helpers.collection.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;

import com.socialnet.domain.models.Day;
import com.socialnet.domain.repositories.DayRepository;

@Service("timelineFactory")
public class TimelineFactory {
	
	@Autowired
	Neo4jTemplate neo4jTemplate;
	
	@Autowired
	DayRepository dayRepository;
	
//	@Autowired
//	GraphDatabaseService graphDatabaseService;
//
//	final ExecutionEngine engine = new ExecutionEngine(graphDatabaseService);
//
//	public Day createDay(Date date) {
//		Day day = new Day(date);
//		ExecutionResult result = engine.execute(
//                "MERGE (d:Day {date:{date}}) ON CREATE SET d.created = timestamp() RETURN d", 
//                MapUtil.map( "date", day.getDate()));
//		return day;
//	}
	public Day getDay(Date date){
		//neo4jTemplate.repositoryFor(Day.class).findByPropertyValue(arg0, arg1)
		String dateRepresentation = Day.formatter.print(date.getTime());
		Day day = dayRepository.findByDate(dateRepresentation);
		if(day == null){
			day = new Day(date);
			dayRepository.save(day);
		}
		return day;
	}
}
