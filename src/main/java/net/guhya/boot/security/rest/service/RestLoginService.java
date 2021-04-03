package net.guhya.boot.security.rest.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.stereotype.Service;

import net.guhya.boot.module.user.data.UserData;
import net.guhya.boot.module.user.service.UserService;

@Service("loginService")
public class RestLoginService {
	
	private static Logger log = LoggerFactory.getLogger(RestLoginService.class);

	@Autowired
	private UserService userService;

	public UserData getLoggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null) return new UserData();
		
		Object principal = auth.getPrincipal();
		if(principal.equals("anonymousUser")) return new UserData();
		
		UserData loggedInUser = (UserData) principal;		
		return loggedInUser;
	}
	
	public UserData buildPrincipal(String userId) {
		
		log.info("##### [Login Service] Build principal");		
		UserData userData = new UserData();
		
		try {
			userData.setUserId(userId);
			userData = userService.select(userData);
			List<String> userRoles = userService.listUserRoles(userData);
			
			String sRoles 			= StringUtils.join(userRoles, ",");
			userData.setRoleString(sRoles);		
			
		} catch (Exception ex) {
			return new UserData();
		}
		
		return userData;		
	}

	public void doLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
			SecurityContextLogoutHandler logoutHandler =  new SecurityContextLogoutHandler();
			logoutHandler.setInvalidateHttpSession(true);
			logoutHandler.logout(request, response, auth);
			
		    CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY);
		    cookieClearingLogoutHandler.logout(request, response, auth);
		}
        SecurityContextHolder.getContext().setAuthentication(null);		
	}

}
