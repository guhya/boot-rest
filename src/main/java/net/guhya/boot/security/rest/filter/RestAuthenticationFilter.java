package net.guhya.boot.security.rest.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.guhya.boot.module.board.BoardRestController;

public class RestAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private static Logger log = LoggerFactory.getLogger(BoardRestController.class);

	private AuthenticationManager authenticationManager;
	private AuthenticationFailureHandler failureHandler;
	private AuthenticationSuccessHandler successHandler;
	
	public RestAuthenticationFilter(AuthenticationManager authenticationManager
			, AuthenticationSuccessHandler successHandler
			, AuthenticationFailureHandler failureHandler) {
		
		this.authenticationManager = authenticationManager;
		this.successHandler = successHandler;
		this.failureHandler = failureHandler;
		
		setFilterProcessesUrl("/v1/users/login");
	}
	
	@SuppressWarnings("unchecked")
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		try {
			
			if (!request.getMethod().equals("POST")) {
				throw new AuthenticationServiceException(
						"Authentication method not supported: " + request.getMethod());
			}

			Map<String, String> creds = new ObjectMapper()
					.readValue(request.getInputStream(), HashMap.class);
			for(Map.Entry<String, String> entry : creds.entrySet()) {
				log.info("Credential : [" + entry.toString() + "]");
			}
			
			String username = creds.get("username").toString();
			String password = creds.get("password").toString();

			if (username == null) {
				username = "";
			}

			if (password == null) {
				password = "";
			}

			username = username.trim();

			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
					username, password);

			// Allow subclasses to set the "details" property
			setDetails(request, authRequest);

			return authenticationManager.authenticate(authRequest);
			
		}catch(IOException ex) {
			throw new RuntimeException("Could not read request");
		}
	}
	
	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		SecurityContextHolder.clearContext();

		failureHandler.onAuthenticationFailure(request, response, failed);
	}
	
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain, Authentication authResult)
			throws IOException, ServletException {

		successHandler.onAuthenticationSuccess(request, response, authResult);
	}
	
}

