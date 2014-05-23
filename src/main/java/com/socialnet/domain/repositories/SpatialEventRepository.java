package com.socialnet.domain.repositories;


import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.repository.SpatialRepository;

import com.socialnet.domain.models.Event;

public interface SpatialEventRepository  extends SpatialRepository<Event>{
		public Result<Event> findWithinDistance(final String indexName, final double lat, double lon, double distanceKm); 
}
