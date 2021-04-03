package net.guhya.boot.module.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import net.guhya.boot.common.service.GenericService;
import net.guhya.boot.module.user.dao.UserDao;
import net.guhya.boot.module.user.data.UserData;

@Service
public class UserService extends GenericService<UserData>{
		
	private UserDao userDao;

	public UserService(UserDao userDao) {
		super(userDao);
		this.userDao = userDao;
		setEntityName("user");
	}


	public List<String> listUserRoles(UserData dto) throws Exception{
		return (List<String>) userDao.listUserRoles(dto);
	}
	

}
