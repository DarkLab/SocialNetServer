package com.socialnet.domain.models;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.support.index.IndexType;

@NodeEntity
public class Profile {

	public enum IdentityProvider {
		FB, VK;
	};

	public Profile() {

	}

	public Profile(IdentityProvider identityProvider,
			String providerSpecificId) {
		this.provider = identityProvider;
		this.providerSpecificId = providerSpecificId;
		this.identifier = String.format("%s-%s", identityProvider.name(),
				this.providerSpecificId);

		
	}

	@GraphId
	private Long id;

	private IdentityProvider provider;
	private String providerSpecificId;

	// @RelatedTo(type="OWNED_BY", direction=Direction.OUTGOING)
	// private SNUser user;

	@Indexed(unique = true)
	// , indexType = IndexType.FULLTEXT, indexName = "socialId")
	private String identifier;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String id) {
		this.identifier = id;
	}

	

	/*
	 * protected Station() { }
	 * 
	 * public Station(Short stationId, String terminalName, String name, double
	 * lat, double lon) { this.stationId = stationId; this.name = name;
	 * this.terminalName = terminalName; this.lon = lon; this.lat = lat;
	 * this.wkt = String.format("POINT(%f %f)",lon,lat).replace(",","."); }
	 */

}
