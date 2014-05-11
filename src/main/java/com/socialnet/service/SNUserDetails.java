package com.socialnet.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import com.socialnet.domain.models.SNUser;



@SuppressWarnings("serial")
public class SNUserDetails implements UserDetails {
	
	private final SNUser user;

    public SNUserDetails(SNUser user) {
        this.user = user;
    }
	
    public SNUser getUser() {
        return user;
    }
    
    @Override
    public String getPassword() {
        return "password";//user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserId().toString();
    }
	
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return this.createAuthorities(false);
	}
	
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	private Collection<GrantedAuthority> createAuthorities(boolean isAdmin) {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);
        authList.add(new GrantedAuthorityImpl("ROLE_USER"));
        if (isAdmin) {
            authList.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
        }
        return authList;
    }
	
	/*private SNUser snUser;

	public SNUserDetails(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
		// TODO Auto-generated constructor stub
	}

	public SNUser getSnUser() {
		return snUser;
	}

	public void setSnUser(SNUser snUser) {
		this.snUser = snUser;
	}*/

}
