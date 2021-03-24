package net.guhya.boot.security.service;

import javax.annotation.Resource;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.guhya.boot.security.data.UserInfo;
import net.guhya.boot.security.data.UserInfoWrapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Resource(name = "loginService")
	private LoginService loginService;
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		
		UserInfo userInfo = new UserInfo();
		userInfo = loginService.buildPrincipal(userId);
		boolean enabled = userInfo.getEnabled().equals("Y");
		
		return new UserInfoWrapper(userInfo.getUserId(), userInfo.getPassword(), enabled, AuthorityUtils.commaSeparatedStringToAuthorityList(userInfo.getRoleString()), userInfo);
	}
}
