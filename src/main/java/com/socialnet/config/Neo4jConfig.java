package com.socialnet.config;

import org.neo4j.gis.spatial.SpatialDatabaseService;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.core.GraphDatabase;

@Configuration
@EnableNeo4jRepositories("com.socialnet.domain")
public class Neo4jConfig extends Neo4jConfiguration{

	public static final String DATABASE_LOCATION = "/tmp/db/data/socialnet.db";
	
	public Neo4jConfig() {
		setBasePackage("com.socialnet.domain");
	}
	
	@Bean(destroyMethod = "shutdown")
	public GraphDatabaseService graphDatabaseService() {
		return new GraphDatabaseFactory().newEmbeddedDatabase(DATABASE_LOCATION);
	}
	
	@Bean
	public SpatialDatabaseService SpatialDatabaseService(){
		return new SpatialDatabaseService(graphDatabaseService());
	}
	
 
}
