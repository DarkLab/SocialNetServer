package com.socialnet.domain.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

@NodeEntity
public class SNUser {

	@GraphId private Long id;
	@Indexed
	private UUID userId;
	
	public SNUser(UUID userId){
		this.setUserId(userId);
	}
	
	public SNUser(){
	}
	
	@RelatedToVia
	@Fetch
	Set<Alias> aliases = new HashSet<Alias>();
    

	public Alias knownAs(Profile socialIdentity) {
    	Alias knownAs=new Alias(this,socialIdentity);
    	aliases.add(knownAs);
        return knownAs;
    }
	//@RelatedTo(type="KNOWN_AS", direction=Direction.OUTGOING)
    //public @Fetch Set<SocialIdentity> identities;
	public UUID getUserId() {
		return userId;
	}
	private void setUserId(UUID userId) {
		this.userId = userId;
	}
	
	public Set<Alias> getAliases() {
		return aliases;
	}

	public void setAliases(Set<Alias> aliases) {
		this.aliases = aliases;
	}
    
	
	/*public void knownAs(SocialIdentity socialIdentity) {
        if (identities == null) {
        	identities = new HashSet<SocialIdentity>();
        }
        identities.add(socialIdentity);
    }*/
}
