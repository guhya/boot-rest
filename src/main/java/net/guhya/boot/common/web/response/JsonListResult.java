package net.guhya.boot.common.web.response;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import net.guhya.boot.common.data.PagingData;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"timestamp","status", "meta","data", "error", "message"})
public class JsonListResult<T> {
	
	private PagingData meta;
	private Map<String, List<T>> data;
	private String status;
	private String error;
	private String message;
	private String timestamp;
	
	public JsonListResult() {
	}
	
	/**
	 * Response for list operation
	 * @param totalRecords
	 * @param currentPage
	 * @param result
	 */
	public JsonListResult(PagingData pagingData,
			List<T> result) {
		this.status = "success";
		this.meta = pagingData;
		
		this.data = new HashMap<>();
		this.data.put("attributes", result);
		this.timestamp = LocalDateTime.now().toString();
	}

	public PagingData getMeta() {
		return meta;
	}

	public void setMeta(PagingData meta) {
		this.meta = meta;
	}

	public Map<String, List<T>> getData() {
		return data;
	}

	public void setData(Map<String, List<T>> data) {
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
