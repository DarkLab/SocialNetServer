package com.socialnet.controllers;

import java.util.UUID;

import org.neo4j.kernel.api.exceptions.schema.UniqueConstraintViolationKernelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.socialnet.domain.models.Alias;
import com.socialnet.domain.models.Event.Feeling;
import com.socialnet.domain.models.Originated;
import com.socialnet.domain.models.SNUser;
import com.socialnet.domain.models.Profile;
import com.socialnet.domain.repositories.AliasRepository;
import com.socialnet.domain.repositories.UserRepository;
import com.socialnet.domain.repositories.ProfileRepository;
import com.socialnet.service.SNUserDetails;

@RestController
public class UserManagementController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AliasRepository aliasRepository;

	@Autowired
	ProfileRepository profileRepository;

	@Autowired
	Neo4jTemplate neo4jTemplate;
	
	protected SNUser getAuthenticatedUser(){
		SNUser user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof SNUserDetails) {
			SNUserDetails userDetails = (SNUserDetails) principal;
	        user = userDetails.getUser();
	    }
		return user;
	}

	@Transactional
	@RequestMapping(value = "/socialIdentity", method = RequestMethod.DELETE)
	public Profile deleteSI(
			@RequestParam(value = "sn", required = true) Profile.IdentityProvider sn,
			@RequestParam(value = "snid", required = true) String snid) {

		Profile socialIdentity = profileRepository
				.findByProviderAndProviderSpecificId(sn, snid);

		if (socialIdentity != null) {
			profileRepository.delete(socialIdentity);

		}

		return socialIdentity;
	}

	@Transactional
	@RequestMapping(value = "/loginWithSocialId", method = RequestMethod.POST)
	public SNUser loginWithSocialId(
			@RequestParam(value = "sn", required = true) Profile.IdentityProvider sn,
			@RequestParam(value = "snid", required = true) String snid) {
		
		SNUser user = null;

		SNUser authenticatedUser = this.getAuthenticatedUser();
		user = userRepository.findByProviderAndId(sn, snid);
		if (authenticatedUser != null) {
			//check profile exists
			if (user != null) {
				//Profile exists and belongs to other user
				if (!authenticatedUser.getUserId().equals(user.getUserId())) {
					//Message: Error
					//TODO Exception
					user = null;
				} else {
					user = authenticatedUser;
				}
			} else {
				//create profile and attach to SNUser
				user = authenticatedUser;
				createProfileForUser(user, sn, snid);
			}
		} else {
			//anonymous user
			//see if we can find a profile for provided credentials 
			
			if (user == null) {
				user = new SNUser(UUID.randomUUID());
				userRepository.save(user);
				createProfileForUser(user, sn, snid);
				
			}
		}
		
		

//		KnownAs knownAs = knownAsRepository.findByProviderAndId(sn, snid);
//
//		if (knownAs != null) {
//			user = knownAs.getUser();
//		} else {
//			SocialIdentity socialIdentity = socialIdentityRepository
//					.findByProviderAndProviderSpecificId(sn, snid);
//
//			if (socialIdentity == null) {
//				try {
//					socialIdentity = new SocialIdentity(sn, snid, 0, 0);
//					user = new SNUser(UUID.randomUUID());
//
//					// have to save nodes first for RevlatedVia
//					socialIdentityRepository.save(socialIdentity);
//					userRepository.save(user);
//
//					// KnownAs knownAs =
//					// neo4jTemplate.createRelationshipBetween
//					// (user, socialIdentity, KnownAs.class, "KNOWN_AS", false);
//
//					// KnownAs
//					knownAs = user.knownAs(socialIdentity);
//
//					// socialIdentity.setUser(user);
//					knownAsRepository.save(knownAs);
//
//					// neo4jTemplate.save(user);
//				} catch (DataIntegrityViolationException e) {
//					// System.out.println( e);
//					// identity already exists, look again
//					socialIdentity = socialIdentityRepository
//							.findByProviderAndProviderSpecificId(sn, snid);
//				}
//			}
//		}

		// neo4jTemplate.fetch(socialIdentity.getUser());

		return user;// socialIdentity.getUser();

	}
	
	@Transactional
	@RequestMapping(value = "/createEvent", method = RequestMethod.POST)
	public Originated createEvent(
			@RequestParam(value = "mySn", required = true) Profile.IdentityProvider mySn,
			@RequestParam(value = "mySnId", required = true) String mySnId,
			@RequestParam(value = "targetSn", required = true) Profile.IdentityProvider targetSn,
			@RequestParam(value = "targetSnId", required = true) String targetSnId,
			@RequestParam(value = "feeling", required = true) Feeling feeling,
			@RequestParam(value = "rant", required = false) String rant) {
		
		SNUser user = userRepository.findByProviderAndId(targetSn, targetSnId);
		SNUser authenticatedUser = this.getAuthenticatedUser();
		
		if (user == null) {
			//TODO Throw exception
			return null;
		} else {
			//find if event exists that authori
		}
		
		return null;
	}

	private void createProfileForUser(SNUser user, Profile.IdentityProvider sn, String snid) {
		Profile profile = new Profile(sn, snid, 0, 0);

		// have to save nodes first for RevlatedVia
		profileRepository.save(profile);
//				Alias knownAs =
//						 neo4jTemplate.createRelationshipBetween
//						 (user, socialIdentity, Alias.class, "KNOWN_AS", false);
		Alias alias = user.knownAs(profile);
		aliasRepository.save(alias);
	}
}
