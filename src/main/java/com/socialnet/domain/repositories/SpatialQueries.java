package com.socialnet.domain.repositories;

public class SpatialQueries {
	
	public static String withinDistanceQuery(Double lat, Double lon, Double distance){
		return "withinDistance:[" + lat +"," + lon + "," + distance + "]";
	}
}
