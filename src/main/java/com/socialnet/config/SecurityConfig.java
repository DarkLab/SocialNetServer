package com.socialnet.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.socialnet.config.security.AppTokenFilter;
import com.socialnet.config.security.HeaderAuthenticationFilter;
import com.socialnet.config.security.HeaderUtil;
import com.socialnet.config.security.SNAuthenticationProvider;
import com.socialnet.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String ACCESS_DENIED_JSON = "{\"message\":\"You are not privileged to request this resource.\", \"access-denied\":true,\"cause\":\"AUTHORIZATION_FAILURE\"}";
	private static final String UNAUTHORIZED_JSON = "{\"message\":\"Full authentication is required to access this resource.\", \"access-denied\":true,\"cause\":\"NOT AUTHENTICATED\"}";

	public static final String V1_0 = "application/socialnet-v1.0+json";

	@Autowired
	private HeaderUtil headerUtil;

	@Autowired
	CustomUserDetailsService userDetailsService;

	@Autowired
	SNAuthenticationProvider authenticationProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		CustomAuthenticationSuccessHandler successHandler = new CustomAuthenticationSuccessHandler();
		successHandler.headerUtil(headerUtil);
		/*
		 * http .authorizeRequests() .antMatchers("/**").hasRole("USER") .and()
		 * .formLogin();
		 */

		http
			.addFilterBefore(appTokenFilter(), LogoutFilter.class)
		// .addFilterBefore(authenticationFilter(), LogoutFilter.class)
			.csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				.and().formLogin().successHandler(successHandler)
				.loginProcessingUrl("/login")

				.and().logout().logoutSuccessUrl("/logout")

				.and().exceptionHandling()
				.accessDeniedHandler(new CustomAccessDeniedHandler())
				.authenticationEntryPoint(new CustomAuthenticationEntryPoint())

				.and().authorizeRequests()
				.antMatchers(HttpMethod.POST, "/loginWithSocialId").permitAll()
				.antMatchers(HttpMethod.GET, "/test0").permitAll()
				.antMatchers(HttpMethod.POST, "/login").permitAll()
				// .antMatchers(HttpMethod.POST, "/logout").authenticated()
				// .antMatchers("/findAllUsers").permitAll()
				// .antMatchers("/test2").permitAll()
				// .anyRequest().authenticated();
				// http.authorizeUrls()
				.antMatchers("/**").hasRole("USER").and().httpBasic();

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.//authenticationProvider(authenticationProvider);
		userDetailsService(userDetailsService);
		/*
		 * .inMemoryAuthentication() .withUser("user") .password("password")
		 * .roles("USER") .and() .withUser("admin") .password("password")
		 * .roles("ADMIN","USER");
		 */
	}

	/*
	 * @Autowired public void configureGlobal(AuthenticationManagerBuilder auth)
	 * throws Exception { auth.inMemoryAuthentication().
	 * 
	 * withUser("user").password("password").roles("USER").
	 * 
	 * and().
	 * 
	 * withUser("admin").password("password").roles("USER", "ADMIN"); }
	 */

	private Filter appTokenFilter() {
		AppTokenFilter appTokenFilter = new AppTokenFilter();
		return appTokenFilter;
	}

	private Filter authenticationFilter() {
		HeaderAuthenticationFilter headerAuthenticationFilter = new HeaderAuthenticationFilter();
		headerAuthenticationFilter.userDetailsService(userDetailsService());
		headerAuthenticationFilter.headerUtil(headerUtil);
		return headerAuthenticationFilter;
	}

	private static class CustomAccessDeniedHandler implements
			AccessDeniedHandler {
		@Override
		public void handle(HttpServletRequest request,
				HttpServletResponse response,
				AccessDeniedException accessDeniedException)
				throws IOException, ServletException {

			response.setContentType(V1_0);
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			PrintWriter out = response.getWriter();
			out.print(ACCESS_DENIED_JSON);
			out.flush();
			out.close();

		}
	}

	private static class CustomAuthenticationEntryPoint implements
			AuthenticationEntryPoint {
		@Override
		public void commence(HttpServletRequest request,
				HttpServletResponse response,
				AuthenticationException authException) throws IOException,
				ServletException {

			// response.setContentType(V1_0);
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			PrintWriter out = response.getWriter();
			out.print(UNAUTHORIZED_JSON);
			out.flush();
			out.close();
		}
	}

	private static class CustomAuthenticationSuccessHandler extends
			SimpleUrlAuthenticationSuccessHandler {

		private HeaderUtil headerUtil;

		@Override
		public void onAuthenticationSuccess(HttpServletRequest request,
				HttpServletResponse response, Authentication authentication)
				throws ServletException, IOException {
			try {
				String token = headerUtil
						.createAuthToken(((User) authentication.getPrincipal())
								.getUsername());
				ObjectMapper mapper = new ObjectMapper();
				ObjectNode node = mapper.createObjectNode().put("token", token);
				PrintWriter out = response.getWriter();
				out.print(node.toString());
				out.flush();
				out.close();
			} catch (GeneralSecurityException e) {
				throw new ServletException("Unable to create the auth token", e);
			}
			clearAuthenticationAttributes(request);

		}

		private void headerUtil(HeaderUtil headerUtil) {
			this.headerUtil = headerUtil;
		}
	}
}
