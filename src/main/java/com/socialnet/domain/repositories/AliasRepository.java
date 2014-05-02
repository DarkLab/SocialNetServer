package com.socialnet.domain.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.socialnet.domain.models.Alias;
import com.socialnet.domain.models.Profile;

public interface AliasRepository extends GraphRepository<Alias> {
	public Alias findByProfileIdentifier(String identifier);

	//@Query("start socialIdentity=node:SocialIdentity(provider={p}, providerSpecificId={id}) match (user)-[r:KNOWN_AS]->(socialIdentity) where socialIdentity.provider = {p} and socialIdentity.providerSpecificId = {id} return r")
	@Query("MATCH (n)-[r:KNOWN_AS]->(socialIdentity) WHERE socialIdentity.provider={p} and socialIdentity.providerSpecificId={id} RETURN r")
	public Alias findByProviderAndId(
			@Param("p") Profile.IdentityProvider provider,
			@Param("id") String id);
}
