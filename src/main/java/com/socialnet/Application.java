package com.socialnet;



import java.io.File;
import java.io.IOException;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.FileUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.support.Neo4jTemplate;

import com.socialnet.config.Neo4jConfig;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.socialnet")
public class Application{

    public static void main(String[] args) throws IOException {
    //Todo don't delete database  
//    	FileUtils.deleteRecursively(new File(Neo4jConfig.DATABASE_LOCATION));
        SpringApplication.run(Application.class, args);
    }

} 