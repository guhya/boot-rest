package net.guhya.boot.security.rest.filter;

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

import net.guhya.boot.security.data.UserInfo;
import net.guhya.boot.security.rest.JwtParser;

public class RestAuthorizationFilter extends BasicAuthenticationFilter {
	
    public RestAuthorizationFilter(AuthenticationManager authenticationManager){
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
        	UserInfo userInfo = JwtParser.parseToken(token);
            if(userInfo != null){
                return new UsernamePasswordAuthenticationToken(
                		userInfo
                		, userInfo.getPassword()
                		, AuthorityUtils.commaSeparatedStringToAuthorityList(userInfo.getRoleString()));
            }
        }
        
        return null;
    }
    
}