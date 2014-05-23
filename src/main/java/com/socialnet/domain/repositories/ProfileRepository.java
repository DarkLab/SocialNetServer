package com.socialnet.domain.repositories;

import org.jboss.logging.Param;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.socialnet.domain.models.Profile;
import com.socialnet.domain.models.Profile.IdentityProvider;

public interface ProfileRepository extends GraphRepository<Profile> {
	Profile findByIdentifier(String identifier);

//	@Query("MATCH (n:Profile {n.provider:p, n.externalId:id}) RETURN n")
//	public Profile findByProviderAndProviderSpecificId(
//			@Param(value = "p") IdentityProvider identityProvider,
//			@Param(value = "id") String providerSpecificId);

	public Profile findByProviderAndExternalId(
			IdentityProvider identityProvider, String externalId);
}
