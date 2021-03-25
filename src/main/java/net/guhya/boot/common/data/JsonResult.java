package net.guhya.boot.common.data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"timestamp","status", "meta","data", "error", "message"})
public class JsonResult {
	
	private Map<String, Object> meta;
	private Map<String, Object> data;
	private String status;
	private String error;
	private String message;
	private String timestamp;
	
	/**
	 * Response for list operation
	 * @param totalRecords
	 * @param currentPage
	 * @param result
	 */
	public JsonResult(int totalRecords, 
			int currentPage, 
			Object result) {
		this.status = "success";
		this.meta = new HashMap<>();
		this.meta.put("totalRecords", String.valueOf(totalRecords));
		this.meta.put("currentPage", String.valueOf(currentPage));
		
		this.data = new HashMap<>();
		this.data.put("attributes", result);
		this.timestamp = LocalDateTime.now().toString();
	}

	/**
	 * Response for successful operation
	 * @param result
	 */
	public JsonResult(Object result) {
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

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
}
