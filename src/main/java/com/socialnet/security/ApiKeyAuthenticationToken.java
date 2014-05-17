package com.socialnet.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class ApiKeyAuthenticationToken extends UsernamePasswordAuthenticationToken{

	private String apiKey;
	
	public ApiKeyAuthenticationToken(String apiKey) {
		super(null, null);
		this.apiKey = apiKey;
	}
	
	@Override
	public boolean isAuthenticated(){
		return (this.apiKey != null && this.apiKey.equals("1234567"));
	}

}
