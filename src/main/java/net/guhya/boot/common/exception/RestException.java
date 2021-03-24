package net.guhya.boot.common.exception;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import net.guhya.boot.common.data.JsonResult;

@RestControllerAdvice
public class RestException extends ResponseEntityExceptionHandler {
	
	private static Logger log = LoggerFactory.getLogger(RestException.class);

	@ExceptionHandler(BadCredentialsException.class)
	public JsonResult handleBadCredentialsException(BadCredentialsException ex
			, HttpServletResponse response) {

		log.info("Exception : " + ex.getMessage());
		
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		response.setStatus(status.value());
		return new JsonResult(HttpStatus.UNAUTHORIZED.toString(), ex);
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public JsonResult handleAuthenticationException(AuthenticationException ex
			, HttpServletResponse response) {
		
		log.info("Exception : " + ex.getMessage());

		HttpStatus status = HttpStatus.UNAUTHORIZED;
		response.setStatus(status.value());
		return new JsonResult(HttpStatus.UNAUTHORIZED.toString(), ex);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public JsonResult handleAccessDeniedException(AccessDeniedException ex
			, HttpServletResponse response) {
		
		log.info("Exception : " + ex.getMessage());

		HttpStatus status = HttpStatus.FORBIDDEN;
		response.setStatus(status.value());
		return new JsonResult(HttpStatus.FORBIDDEN.toString(), ex);
	}
	
}
