package net.guhya.boot.security.rest.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class RestLoginFailureHandler implements AuthenticationFailureHandler{
	
	private static Logger log = LoggerFactory.getLogger(RestLoginSuccessHandler.class);

    @Autowired
    @Qualifier("handlerExceptionResolver") 
    private HandlerExceptionResolver resolver;

    @Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		log.info("#####  [Authentication] failed : " + exception.getMessage());
		
        resolver.resolveException(request, response, null, 
        		new BadCredentialsException(exception.getMessage()));

	}


}
