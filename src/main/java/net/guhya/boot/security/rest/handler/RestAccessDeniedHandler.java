package net.guhya.boot.security.rest.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
	
	private static Logger log = LoggerFactory.getLogger(RestLoginSuccessHandler.class);

    @Autowired
    @Qualifier("handlerExceptionResolver") 
    private HandlerExceptionResolver resolver;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
	
		log.info("#####  [Authentication] failed : " + accessDeniedException.getMessage());
		
        resolver.resolveException(request, response, null, accessDeniedException);
	}


}
