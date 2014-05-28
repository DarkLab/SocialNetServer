package com.socialnet.domain.repositories;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.MapResult;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.annotation.ResultColumn;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.SpatialRepository;
import org.springframework.data.repository.query.Param;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.socialnet.domain.models.Event;
import com.socialnet.domain.models.Originated;
import com.socialnet.domain.models.Profile;
import com.socialnet.domain.models.SNUser;

//START n=node:stadiumsLocation('withinDistance:[53.489271,-2.246704, 5.0]')
//MATCH (n)<-[:LOCATED_IN]-(something)
//WHERE something.someProp=5
//RETURN something

//@Query(value = "start n=node:location('withinDistance:[51.526256,0.0,100.0]') MATCH user-[wa:WAS_HERE]-n WHERE wa.ts > {ts} return user"
//Page findByTimeAtLocation(@Param("ts") long ts);

public interface EventRepository extends GraphRepository<Event>, SpatialEventRepository{
	/*@Query("START n=node:locations('withinDistance:[{lat},122.3331, 100.0]') "
			+ "RETURN n;")
	//@Query("start n = node:locations(withinDistance:[41.99,-87.67,10.0]) return n;")
	public List<Event> findByDistanceAround(
			@Param(value = "lat") Double lat,
			@Param(value = "lon") Double lon,
			@Param(value = "distance") Double distance);*/
	
	@Query("START n=node:locations({distanceQuery}) "
			+ "RETURN n;")
	public List<Event> findWithDistanceQuery_(
			@Param(value = "distanceQuery") String query);
	
	
	//public Iterable<Event> findWithinDistance( final String indexName, final double lat, double lon, double distanceKm);
	
	
	@Query("START event=node:locations({distanceQuery}) "
			+ "MATCH (origin:Profile)-[:ORIGINATED]->event-[:TARGETED]-(target:Profile) " //(p:Profile)-[o:ORIGINATED]-(n)-[t:TARGETED] "
			+ "RETURN origin, event, target")
	public Page<FullEventPath>  findWithDistanceQuery(
			@Param(value = "distanceQuery") String query,
			Pageable page);
	
	
	@QueryResult
	public class FullEventPath { 
		
		@JsonProperty
		@ResultColumn("origin")
		Profile origin;
		
		@JsonProperty
		@ResultColumn("target")
		Profile target;
		
		@JsonProperty
		@ResultColumn("event")
		Event event;
		
		public Profile getOrigin() {
			return origin;
		}

		public void setOrigin(Profile origin) {
			this.origin = origin;
		}

		public Profile getTarget() {
			return target;
		}

		public void setTarget(Profile target) {
			this.target = target;
		}

		public Event getEvent() {
			return event;
		}

		public void setEvent(Event event) {
			this.event = event;
		}
	}
	
}
