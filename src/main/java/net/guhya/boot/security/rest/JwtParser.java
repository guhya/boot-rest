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
import net.guhya.boot.module.user.data.UserData;

public class JwtParser {

	private static Logger log = LoggerFactory.getLogger(JwtParser.class);

	public static String generateToken(UserData userData)
			throws IOException, ServletException {
		
        Claims claims = Jwts.claims().setSubject(userData.getUserId());
        claims.put("userId", userData.getUserId() + "");
        claims.put("role", userData.getRoleString());

		String token = Jwts.builder()
				.setSubject(userData.getUserId())
				.setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + 864_000_000))
				.signWith(SignatureAlgorithm.HS512, "SECRETKEY".getBytes())
				.compact();
		
		log.info("### Generated token : " + token);

		return token;
	}
	
	public static UserData parseToken(String token) {
		
		Jws<Claims> jwt = Jwts.parser()
					.setSigningKey("SECRETKEY".getBytes())
					.parseClaimsJws(token.replace("Bearer",""));
		
		log.info("### Parsed token : " + jwt);
		
    	Claims body = jwt.getBody();
    	
    	UserData userData = new UserData();
    	userData.setUserId((String) body.get("userId"));
    	userData.setRoleString((String) body.get("role"));
		
    	return userData;
	}
	
}
