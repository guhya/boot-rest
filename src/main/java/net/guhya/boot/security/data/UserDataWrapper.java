package net.guhya.boot.security.data;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import net.guhya.boot.module.user.data.UserData; 

public class UserDataWrapper extends User {
	
	private static final long serialVersionUID = 1224446238251443976L;

	private UserData userData;

	public UserDataWrapper(String username, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities, UserData userData) {
		super(username, password, enabled, true, true, true, authorities);
		this.userData = userData;
	}
	
	public UserData getUserInfo() {
		return userData;
	}

	public void setUserInfo(UserData userData) {
		this.userData = userData;
	}	
	

}
