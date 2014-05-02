package com.socialnet.domain.repositories;

import org.jboss.logging.Param;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.socialnet.domain.models.Profile;
import com.socialnet.domain.models.Profile.IdentityProvider;

public interface ProfileRepository extends GraphRepository<Profile>{
	Profile findByIdentifier(String identifier);
	
	@Query("MATCH (n:SocialIdentity {n.provider:p, n.providerSpecificId:id}) RETURN n")
	public Profile findByProviderAndProviderSpecificId(
			@Param() IdentityProvider identityProvider, 
			@Param() String providerSpecificId);
}
