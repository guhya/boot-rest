package net.guhya.boot.security.handler;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.guhya.boot.security.data.UserInfo;
import net.guhya.boot.security.data.UserInfoWrapper;
import net.guhya.boot.security.service.LoginService;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	private static Logger log = LoggerFactory.getLogger(LoginSuccessHandler.class);

	@Resource(name = "loginService")
	private LoginService loginService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		logger.info("#### [Authenticated] Success");		
		
		Object principal = authentication.getPrincipal();
		log.info("### Principal " + principal.toString());
		
		UserInfo userInfo = ((UserInfoWrapper) principal).getUserInfo();
        Claims claims = Jwts.claims().setSubject(userInfo.getUserId());
        claims.put("userId", userInfo.getUserId() + "");
        claims.put("role", userInfo.getRoleString());

		String token = Jwts.builder()
				.setSubject(userInfo.getUserId())
				.setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + 864_000_000))
				.signWith(SignatureAlgorithm.HS512, "SECRETKEY".getBytes())
				.compact();
		
		log.info("### Token " + token);
		
		response.addHeader("Authorization", "Bearer " + token);
	}

}
