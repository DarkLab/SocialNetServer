package com.socialnet.controllers;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.socialnet.domain.models.Alias;
import com.socialnet.domain.models.Profile;
import com.socialnet.domain.models.SNUser;
import com.socialnet.domain.models.Profile.IdentityProvider;
import com.socialnet.domain.repositories.UserRepository;
import com.socialnet.service.SNUserDetails;

@RestController
public class HelloWorldController {

	@Autowired
	UserRepository userRepository;

	// @Autowired
	// GraphDatabase graphDatabase;

	@Autowired
	Neo4jTemplate neo4jTemplate;

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping(value = "/test0", method = RequestMethod.GET)
	public SNUser test0(
			@RequestParam(value = "name", required = false, defaultValue = "Bubba") String name) {
		// SNUser greg = new SNUser();
		// greg.setFirstName(name);
		// greg.setLastName("Bubs");
		return new SNUser(UUID.randomUUID());
	}

	@RequestMapping(value = "/findAllUsers", method = RequestMethod.POST)
	public List<SNUser> findAllUsers(
			@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof SNUserDetails) {
			SNUserDetails userDetails = (SNUserDetails) principal;
	        SNUser user = userDetails.getUser();
	      }
		
		Page<SNUser> users = userRepository.findAll(new PageRequest(start,
				pageSize));

		return users.getContent();
	}

	@Transactional
	@RequestMapping(value = "/test1", method = RequestMethod.POST)
	public SNUser test1(
			@RequestParam(value = "name", required = false, defaultValue = "Zoe") String name) {
		SNUser greg = null;// new SNUser();
		// greg.setFirstName(name);
		// greg.setLastName("Love");
		return greg;
	}

	/*
	 * @Transactional
	 * 
	 * @RequestMapping(value = "/login", method = RequestMethod.POST) public
	 * SNUser login(
	 * 
	 * @RequestParam(value="name", required=false, defaultValue="Zoe") String
	 * name) { SNUser greg = new SNUser(); greg.setFirstName(name);
	 * greg.setLastName("Love"); return greg; }
	 */

	@Transactional
	@RequestMapping(value = "/test2", method = RequestMethod.GET)
	public SNUser test2(
			@RequestParam(value = "name", required = false, defaultValue = "Greg") String name) {

		SNUser greg = new SNUser(UUID.randomUUID());
		// greg.setFirstName(name);
		// greg.setLastName("Simmons");

		Profile facebookIdentity = new Profile(
				IdentityProvider.FB, "1234567", 0, 0);

		greg.knownAs(facebookIdentity);

//		userRepository.save(greg);

		// KnownAs knownAs =
		// neo4jTemplate.createRelationshipBetween(greg, facebookIdentity,
		// KnownAs.class, "KNOWN_AS", false);

		// userRepository.findByFirstName(greg.getFirstName());

		return greg;// new Greeting(counter.incrementAndGet(),
		// String.format(template, name));
	}
}
