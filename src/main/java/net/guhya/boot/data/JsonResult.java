package net.guhya.boot.data;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class JsonResult {
	
	private Map<String, Object> meta;
	private Map<String, Object> data;
	private String status;
	
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
		}else {
			this.status = "fail";
		}
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
	
	
	
}
