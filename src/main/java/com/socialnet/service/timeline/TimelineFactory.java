package com.socialnet.service.timeline;

import java.util.Date;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.helpers.collection.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialnet.domain.models.Day;

@Service("timelineFactory")
public class TimelineFactory {
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
}
