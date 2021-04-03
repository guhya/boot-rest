package net.guhya.boot.module.user.data;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import net.guhya.boot.common.data.AbstractData;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties({"password","oldPassword"})
public class UserData extends AbstractData implements Serializable {

	private static final long serialVersionUID = 4264730370993026084L;
	
	private String userId;
	private String password;
	private String oldPassword;
	private String firstName;
	private String lastName;
	private String enabled;
	private String email;
	private String roleString;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getRoleString() {
		return roleString;
	}
	public void setRoleString(String roleString) {
		this.roleString = roleString;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserData [userId=");
		builder.append(userId);
		builder.append(", password=");
		builder.append(password);
		builder.append(", oldPassword=");
		builder.append(oldPassword);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", enabled=");
		builder.append(enabled);
		builder.append(", email=");
		builder.append(email);
		builder.append(", roleString=");
		builder.append(roleString);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
}
