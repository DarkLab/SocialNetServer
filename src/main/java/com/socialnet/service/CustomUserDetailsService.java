package com.socialnet.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.socialnet.domain.models.SNUser;
import com.socialnet.domain.repositories.SNUserRepository;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	SNUserRepository snUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserDetails user = null;
		//this.getUserFromSession();
		SNUser snUser = snUserRepository.findByUserId(username);
		if (snUser != null) {
			user = new User(username, "password", true, true, true, true, this.getAuthorities(false));
		}
		
		return user;
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(boolean isAdmin) {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);
        authList.add(new GrantedAuthorityImpl("ROLE_USER"));
        if (isAdmin) {
            authList.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
        }
        return authList;
    }
	
	public User getUserFromSession() {
	      SecurityContext context = SecurityContextHolder.getContext();
	      Authentication authentication = context.getAuthentication();
	      Object principal = authentication.getPrincipal();
	      /*if (principal instanceof CineastsUserDetails) {
	          CineastsUserDetails userDetails = (CineastsUserDetails) principal;
	          return userDetails.getUser();
	      }*/
	      return null;
	  }

}
