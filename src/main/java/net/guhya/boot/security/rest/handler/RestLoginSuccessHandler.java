package net.guhya.boot.security.rest.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.guhya.boot.common.web.response.JsonResult;
import net.guhya.boot.module.user.data.UserData;
import net.guhya.boot.security.data.UserDataWrapper;
import net.guhya.boot.security.rest.JwtParser;

@Component
public class RestLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	private static Logger log = LoggerFactory.getLogger(RestLoginSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		logger.info("#### [Authenticated] Success");		
		
		Object principal = authentication.getPrincipal();
		log.info("### Principal " + principal.toString());
		
		UserData userData = ((UserDataWrapper) principal).getUserInfo();
		String token = JwtParser.generateToken(userData);
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("token", token);
		resMap.put("user", userData);
		
		log.info("### Token " + token);
		
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("Content-Type", "application/json");
		response.getWriter().println(new ObjectMapper()
				.writeValueAsString(new JsonResult(resMap)));
	}

}
