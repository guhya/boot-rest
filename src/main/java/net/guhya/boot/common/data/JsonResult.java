package net.guhya.boot.common.data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class JsonResult {
	
	private Map<String, Object> meta;
	private Map<String, Object> data;
	private String status;
	private String error;
	private String message;
	private LocalDateTime timestamp;
	
	public JsonResult(int totalRecords, 
			int currentPage, 
			Object result) {
		
		this.meta = new HashMap<>();
		this.meta.put("totalRecords", String.valueOf(totalRecords));
		this.meta.put("currentPage", String.valueOf(currentPage));
		
		this.data = new HashMap<>();
		this.data.put("attributes", result);
	}

	public JsonResult(boolean success) {
		if(success) {
			this.status = "success";
			this.message = HttpStatus.OK.name().toString();
		}else {
			this.status = "error";
			this.error = HttpStatus.INTERNAL_SERVER_ERROR.name().toString();
			this.message = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
		}
	}

	public JsonResult(String errorCode, Exception ex) {
		this.status = "error";
		this.error = errorCode;
		this.message = ex.getMessage();
	}

	public Map<String, Object> getMeta() {
		return meta;
	}

	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
}
