package net.guhya.boot.common.exception.advice;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import net.guhya.boot.common.exception.GeneralRestException;
import net.guhya.boot.common.exception.ItemNotFoundException;
import net.guhya.boot.common.web.response.JsonResult;

@RestControllerAdvice
public class RestExceptionAdvice extends ResponseEntityExceptionHandler {
	
	private static Logger log = LoggerFactory.getLogger(RestExceptionAdvice.class);

	@ExceptionHandler(BadCredentialsException.class)
	public JsonResult<Object> handleBadCredentialsException(BadCredentialsException ex
			, HttpServletResponse response) {

		log.info("## BadCredentialsException : " + ex.getMessage());
		
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		response.setStatus(status.value());
		return new JsonResult<>(HttpStatus.UNAUTHORIZED.toString()
				, "Incorrect login information");
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public JsonResult<Object> handleAuthenticationException(AuthenticationException ex
			, HttpServletResponse response) {
		
		log.info("## AuthenticationException : " + ex.getMessage());

		HttpStatus status = HttpStatus.UNAUTHORIZED;
		response.setStatus(status.value());
		return new JsonResult<>(HttpStatus.UNAUTHORIZED.toString()
				, "Restricted item, need login");
	}

	@ExceptionHandler(AccessDeniedException.class)
	public JsonResult<Object> handleAccessDeniedException(AccessDeniedException ex
			, HttpServletResponse response) {
		
		log.info("## AccessDeniedException : " + ex.getMessage());

		HttpStatus status = HttpStatus.FORBIDDEN;
		response.setStatus(status.value());
		return new JsonResult<>(HttpStatus.FORBIDDEN.toString()
				, "You don't have enough privilege");
	}
	
	@ExceptionHandler(ItemNotFoundException.class)
	public JsonResult<Object> handleItemNotFoundException(ItemNotFoundException ex
			, HttpServletResponse response) {
		
		log.info("## ItemNotFoundException : " + ex.getMessage());

		HttpStatus status = HttpStatus.NOT_FOUND;
		response.setStatus(status.value());
		return new JsonResult<>(HttpStatus.NOT_FOUND.toString()
				, "Requested item not found");
	}

	@ExceptionHandler(SQLException.class)
	public JsonResult<Object> handleSQLException(SQLException ex
			, HttpServletResponse response) {
		
		log.info("## SQLException : " + ex.getMessage());

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		response.setStatus(status.value());
		return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR.toString()
				, "Encountered SQL error");
	}

	@ExceptionHandler(SQLSyntaxErrorException.class)
	public JsonResult<Object> handleSQLSyntaxErrorException(SQLSyntaxErrorException ex
			, HttpServletResponse response) {
		
		log.info("## SQLException : " + ex.getMessage());

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		response.setStatus(status.value());
		return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR.toString()
				, "Encountered SQL syntax error");
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		log.info("## MethodArgumentNotValidException : " + ex.getMessage());
		
		Map<String, String> errMap = new HashMap<>();
		for(FieldError err : ex.getBindingResult().getFieldErrors()) {
			errMap.put(err.getField(), err.getDefaultMessage());
		}

		JsonResult<Object> res = new JsonResult<>(HttpStatus.BAD_REQUEST.toString() 
				, errMap);
		
		return new ResponseEntity<>(res, headers, HttpStatus.BAD_REQUEST);
	}
		
	@ExceptionHandler(GeneralRestException.class)
	public JsonResult<Object> handleGeneralRestException(GeneralRestException ex
			, HttpServletResponse response) {
		
		log.info("## GeneralRestException : " + ex.getMessage());

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		response.setStatus(status.value());
		return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR.toString() 
				, "Error occured");
	}

	@ExceptionHandler(Exception.class)
	public JsonResult<Object> handleGeneralException(Exception ex
			, HttpServletResponse response) {
				
		log.info("## Exception : " + ex.getMessage());

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		response.setStatus(status.value());
		return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR.toString() 
				, ex.getCause().getClass().getSimpleName() + " occured");
	}

}
