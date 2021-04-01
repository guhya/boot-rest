package net.guhya.boot.common.exception.advice;

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
import net.guhya.boot.common.exception.GeneralRestException;
import net.guhya.boot.common.exception.ItemNotFoundException;

@RestControllerAdvice
public class RestExceptionAdvice extends ResponseEntityExceptionHandler {
	
	private static Logger log = LoggerFactory.getLogger(RestExceptionAdvice.class);

	@ExceptionHandler(BadCredentialsException.class)
	public JsonResult handleBadCredentialsException(BadCredentialsException ex
			, HttpServletResponse response) {

		log.info("BadCredentialsException : " + ex.getMessage());
		
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		response.setStatus(status.value());
		return new JsonResult(HttpStatus.UNAUTHORIZED.toString(), ex);
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public JsonResult handleAuthenticationException(AuthenticationException ex
			, HttpServletResponse response) {
		
		log.info("AuthenticationException : " + ex.getMessage());

		HttpStatus status = HttpStatus.UNAUTHORIZED;
		response.setStatus(status.value());
		return new JsonResult(HttpStatus.UNAUTHORIZED.toString(), ex);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public JsonResult handleAccessDeniedException(AccessDeniedException ex
			, HttpServletResponse response) {
		
		log.info("AccessDeniedException : " + ex.getMessage());

		HttpStatus status = HttpStatus.FORBIDDEN;
		response.setStatus(status.value());
		return new JsonResult(HttpStatus.FORBIDDEN.toString(), ex);
	}
	
	@ExceptionHandler(ItemNotFoundException.class)
	public JsonResult handleItemNotFoundException(ItemNotFoundException ex
			, HttpServletResponse response) {
		
		log.info("ItemNotFoundException : " + ex.getMessage());

		HttpStatus status = HttpStatus.NOT_FOUND;
		response.setStatus(status.value());
		return new JsonResult(HttpStatus.NOT_FOUND.toString(), ex);
	}

	@ExceptionHandler(GeneralRestException.class)
	public JsonResult handleGeneralRestException(GeneralRestException ex
			, HttpServletResponse response) {
		
		log.info("GeneralRestException : " + ex.getMessage());

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		response.setStatus(status.value());
		return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex);
	}

	@ExceptionHandler(Exception.class)
	public JsonResult handleGeneralException(Exception ex
			, HttpServletResponse response) {
		
		log.info("Exception : " + ex.getMessage());

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		response.setStatus(status.value());
		return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex);
	}
}
