package net.guhya.boot.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import net.guhya.boot.security.data.UserInfo;

public class AuthorizationFilter extends BasicAuthenticationFilter {
	
    public AuthorizationFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);
    }

    protected void doFilterInternal(HttpServletRequest request
    		, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException{
    	
        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(token != null){
        	Claims body = Jwts.parser()
            		.setSigningKey("SECRETKEY".getBytes())
                    .parseClaimsJws(token.replace("Bearer",""))
                    .getBody();
        	
        	UserInfo userInfo = new UserInfo();
        	userInfo.setUserId((String) body.get("userId"));
        	userInfo.setRoleString((String) body.get("role"));
        	
            if(userInfo != null){
                return new UsernamePasswordAuthenticationToken(
                		userInfo.getUserId()
                		, userInfo.getPassword()
                		, AuthorityUtils.commaSeparatedStringToAuthorityList(userInfo.getRoleString()));
            }
        }
        
        return null;
    }
    
}