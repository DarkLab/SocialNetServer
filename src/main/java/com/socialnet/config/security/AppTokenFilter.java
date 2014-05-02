package com.socialnet.config.security;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class AppTokenFilter extends AbstractAuthenticationProcessingFilter {
  
	private static final String FILTER_APPLIED = "FILTER_APPLIED";
	private static final String HEADER_NAME = "APPLICATION_TOKEN";
	
	public AppTokenFilter() {
	    super("/");
	  }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		try{
			this.attemptAuthentication(httpServletRequest, httpServletResponse);
			filterChain.doFilter(request, response);
		}catch (AuthenticationException failed) {
		      //unsuccessfulAuthentication(request, response, failed);
		      unsuccessfulAuthentication(httpServletRequest, httpServletResponse, failed);;
		}
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException,
			IOException, ServletException {
		String applicationTokenString = request.getHeader(HEADER_NAME);
		AbstractAuthenticationToken apiKeyToken = 
				this.authUserByToken(applicationTokenString);
		if(!apiKeyToken.isAuthenticated()){
		    throw new AuthenticationServiceException(MessageFormat.format("Error | {0}", "Bad Token"));
		}
		return apiKeyToken;
	}

	private AbstractAuthenticationToken authUserByToken(String applicationTokenString) {
		return new ApiKeyAuthenticationToken(applicationTokenString);
	}
}
