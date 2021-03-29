package net.guhya.boot.security.rest;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.guhya.boot.security.data.UserInfo;

public class JwtParser {

	private static Logger log = LoggerFactory.getLogger(JwtParser.class);

	public static String generateToken(UserInfo userInfo)
			throws IOException, ServletException {
		
        Claims claims = Jwts.claims().setSubject(userInfo.getUserId());
        claims.put("userId", userInfo.getUserId() + "");
        claims.put("role", userInfo.getRoleString());

		String token = Jwts.builder()
				.setSubject(userInfo.getUserId())
				.setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + 864_000_000))
				.signWith(SignatureAlgorithm.HS512, "SECRETKEY".getBytes())
				.compact();
		
		log.info("### Generated token : " + token);

		return token;
	}
	
	public static UserInfo parseToken(String token) {
		
		Jws<Claims> jwt = Jwts.parser()
					.setSigningKey("SECRETKEY".getBytes())
					.parseClaimsJws(token.replace("Bearer",""));
		
		log.info("### Parsed token : " + jwt);
		
    	Claims body = jwt.getBody();
    	
    	UserInfo userInfo = new UserInfo();
    	userInfo.setUserId((String) body.get("userId"));
    	userInfo.setRoleString((String) body.get("role"));
		
    	return userInfo;
	}
	
}
