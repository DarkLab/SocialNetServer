package com.socialnet.domain.models;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Profile {

	public enum IdentityProvider {
		FB, VK;
	};

	public Profile() {

	}

	public Profile(IdentityProvider identityProvider,
			String providerSpecificId, double lat, double lon) {
		this.provider = identityProvider;
		this.providerSpecificId = providerSpecificId;
		this.identifier = String.format("%s-%s", identityProvider.name(),
				this.providerSpecificId);

		if (lat != 0 && lon != 0) {
			this.lon = lon;
			this.lat = lat;
			this.wkt = String.format("POINT(%f %f)", lon, lat)
					.replace(",", ".");
		}
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

	double lat, lon;
	// @Indexed(indexType = IndexType.POINT, indexName = "locations")
	String wkt;

	/*
	 * protected Station() { }
	 * 
	 * public Station(Short stationId, String terminalName, String name, double
	 * lat, double lon) { this.stationId = stationId; this.name = name;
	 * this.terminalName = terminalName; this.lon = lon; this.lat = lat;
	 * this.wkt = String.format("POINT(%f %f)",lon,lat).replace(",","."); }
	 */

}
