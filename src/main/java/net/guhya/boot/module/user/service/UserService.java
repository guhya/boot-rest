package net.guhya.boot.module.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import net.guhya.boot.common.data.Box;
import net.guhya.boot.common.service.GenericService;
import net.guhya.boot.module.user.dao.UserDao;

@Service
public class UserService extends GenericService<Box>{
		
	private UserDao userDao;

	public UserService(UserDao userDao) {
		super(userDao);
		this.userDao = userDao;
		setEntityName("user");
	}

	/**
	 * List roles of the user
	 * @param paramBox HTTP Request parameters wrapped in HashMap
	 * @return list of current user role
	 * @throws Exception
	 */
	public List<String> listUserRoles(Map<String, Object> parameterMap) throws Exception{
		return (List<String>) userDao.listUserRoles(parameterMap);
	}
	

}
