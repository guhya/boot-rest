package net.guhya.boot.security.rest.service;

import java.util.List;
import java.util.Map;

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

import net.guhya.boot.common.web.request.Box;
import net.guhya.boot.module.user.service.UserService;
import net.guhya.boot.security.data.UserInfo;

@Service("loginService")
public class RestLoginService {
	
	private static Logger log = LoggerFactory.getLogger(RestLoginService.class);

	@Autowired
	private UserService userService;

	public UserInfo getLoggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null) return new UserInfo();
		
		Object principal = auth.getPrincipal();
		if(principal.equals("anonymousUser")) return new UserInfo();
		
		UserInfo loggedInUser = (UserInfo) principal;		
		return loggedInUser;
	}
	
	public UserInfo buildPrincipal(String userId) {
		
		log.info("##### [Login Service] Build principal");		
		Box userBox = new Box();
		userBox.put("userId", userId);
		
		UserInfo userInfo = new UserInfo();
		
		try {
			Map<String, String> result = userService.select(userBox);
			List<String> userRoles = userService.listUserRoles(userBox);
			
			String sRoles 			= StringUtils.join(userRoles, ",");
			String password			= result.get("password");
			String firstName		= result.get("firstName");
			String lastName			= result.get("lastName");
			String email			= result.get("email");
			String enabled			= result.get("enabled");
					
			userInfo.setUserId(userId);
			userInfo.setPassword(password);
			userInfo.setFirstName(firstName);
			userInfo.setLastName(lastName);
			userInfo.setRoleString(sRoles);
			userInfo.setEmail(email);
			userInfo.setEnabled(enabled);
			
		} catch (Exception ex) {
			return new UserInfo();
		}
		
		return userInfo;		
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
