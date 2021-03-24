package net.guhya.boot.security.data;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User; 

public class UserInfoWrapper extends User {
	
	private static final long serialVersionUID = 1224446238251443976L;

	private UserInfo userInfo;

	public UserInfoWrapper(String username, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities, UserInfo userInfo) {
		super(username, password, enabled, true, true, true, authorities);
		this.userInfo = userInfo;
	}
	
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}	
	

}
