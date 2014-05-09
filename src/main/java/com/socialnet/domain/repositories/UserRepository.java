package com.socialnet.domain.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.socialnet.domain.models.SNUser;
import com.socialnet.domain.models.Profile;

public interface SNUserRepository extends GraphRepository<SNUser> {

	//SNUser findByAliasesIdentifier(String identifier);
    //SNUser findByFirstName(String firstName);
    public SNUser findByUserId(String userId);
	
	@Query("MATCH (user:SNUser)-[:KNOWN_AS]->(sid:Profile) " +
			"WHERE sid.provider={p} AND sid.providerSpecificId={id} " +
			"RETURN user;")
	public SNUser findByProviderAndId(
			@Param(value = "p") Profile.IdentityProvider provider,
			@Param(value = "id") String id);

    //Iterable<Person> findByTeammatesName(String name);

}

