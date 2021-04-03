package net.guhya.boot.common.web.response;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"timestamp","status", "meta","data", "error", "message"})
public class JsonResult<T> {
	
	private Map<String, T> data;
	private String status;
	private String error;
	private String message;
	private String timestamp;
	
	public JsonResult() {
	}

	/**
	 * Response for successful operation
	 * @param result
	 */
	public JsonResult(T result) {
		this.status = "success";
		this.data = new HashMap<>();
		this.data.put("attributes", result);
		this.timestamp = LocalDateTime.now().toString();
	}

	/**
	 * Response for error
	 * @param errorCode
	 * @param ex
	 */
	public JsonResult(String errorCode, Exception ex) {
		this.status = "error";
		this.error = errorCode;
		this.message = ex.getMessage();
		this.timestamp = LocalDateTime.now().toString();
	}


	public Map<String, T> getData() {
		return data;
	}

	public void setData(Map<String, T> data) {
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

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
}
