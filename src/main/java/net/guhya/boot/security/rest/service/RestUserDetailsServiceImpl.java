package net.guhya.boot.security.rest.service;

import javax.annotation.Resource;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.guhya.boot.module.user.data.UserData;
import net.guhya.boot.security.data.UserDataWrapper;

@Service
public class RestUserDetailsServiceImpl implements UserDetailsService {
	
	@Resource(name = "loginService")
	private RestLoginService loginService;
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		
		UserData userData = new UserData();
		userData = loginService.buildPrincipal(userId);
		boolean enabled = userData.getEnabled().equals("Y");
		
		return new UserDataWrapper(
				userData.getUserId()
				, userData.getPassword()
				, enabled
				, AuthorityUtils.commaSeparatedStringToAuthorityList(userData.getRoleString())
				, userData);
	}
}
