package com.socialnet.domain.repositories;

import java.util.Date;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.socialnet.domain.models.Day;

public interface DayRepository extends GraphRepository<Day>{
	public Day findByDate(String date);
}
