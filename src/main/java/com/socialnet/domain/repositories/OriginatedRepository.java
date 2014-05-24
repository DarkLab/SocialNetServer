package com.socialnet.domain.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.neo4j.annotation.MapResult;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.annotation.ResultColumn;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.socialnet.domain.models.Event;
import com.socialnet.domain.models.Originated;
import com.socialnet.domain.models.Profile;

public interface OriginatedRepository extends GraphRepository<Originated>{
	
	
	@Query("START n=node:locations({distanceQuery}) "
			+ "MATCH (po)-[or:ORIGINATED]->n-[:TARGETED]-(tp:Profile) " //(p:Profile)-[o:ORIGINATED]-(n)-[t:TARGETED] "
			+ "RETURN or;")
	public List<Originated>  findWithDistanceQuery_(
			@Param(value = "distanceQuery") String query);
	
	
	@Query("START n=node:locations({distanceQuery}) "
			+ "MATCH (po)-[or:ORIGINATED]->n-[:TARGETED]-(tp:Profile) " //(p:Profile)-[o:ORIGINATED]-(n)-[t:TARGETED] "
			+ "RETURN or, tp;")
	public List<EventTarget>  findWithDistanceQuery(
			@Param(value = "distanceQuery") String query);
	
	@MapResult
	interface EventTarget {
		@ResultColumn("or") Originated getOr();
		@ResultColumn("tp") Profile getTp();
		//@ResultColumn("count")
		//int getCount(); 
	}
}
