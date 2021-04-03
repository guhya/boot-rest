package net.guhya.boot.module.main;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import net.guhya.boot.common.web.response.JsonResult;

@Controller
public class MainController {

	private static final Logger log = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	private RestTemplate rest;
	
	@RequestMapping("/")
    public String home(Model model) {
		return "user/main/datatables";
    }
	
	@RequestMapping("/client/board")
    public String board(Model model) {
		
		model.addAttribute("board", doGet("http://localhost:8080/v1/boards/get/120"));
		model.addAttribute("boardList", doGet("http://localhost:8080/v1/boards/list"));
		
		return "user/main/client";
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, Object> doGet(String url){
		HttpEntity<Map<String, String>> request = new HttpEntity<>(getSecuredHeader(getToken()));
		ResponseEntity<Map> response = rest.exchange(url, HttpMethod.GET, request, Map.class);
		
		log.info("Returning response from : " + url);
		
		return response.getBody();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, Object> doPost(String url, Map<String, String> body){
		HttpEntity<Map<String, String>> request = new HttpEntity<>(body, getSecuredHeader(getToken()));
		ResponseEntity<Map> response = rest.exchange(url, HttpMethod.POST, request, Map.class);
		
		log.info("Returning response from : " + url);
		
		return response.getBody();
	}
	
	@SuppressWarnings({ "unchecked" })
	private String getToken() {
		Map<String, String> body = new LinkedHashMap<String, String>();
		body.put("username", "username");
		body.put("password", "admin");
		
		String url = "http://localhost:8080/v1/users/login";
		HttpEntity<Map<String, String>> request = new HttpEntity<>(body, getHeader());
		ResponseEntity<JsonResult> response = rest.exchange(url, HttpMethod.POST, request, JsonResult.class);
		
		Map<String, Object> data = (Map<String, Object>) response.getBody().getData().get("attributes");
		String token = (String) data.get("token");
		log.info("Returning user data : " + token);
		
		return token;
	}

	private HttpHeaders getSecuredHeader(String token) {
		HttpHeaders headers = getHeader();
		headers.add("Authorization", "Bearer " + token);
		
		return headers;
	}
	
	private HttpHeaders getHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		return headers;
	}
	
	
} 
