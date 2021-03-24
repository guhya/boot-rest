package net.guhya.boot.module.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.guhya.boot.common.web.request.Box;
import net.guhya.boot.module.user.dao.UserDao;

@Service
public class UserService {
		
	@Autowired
	private UserDao userDao;

	/**
	 * Select user
	 * @param paramBox
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> select(Box paramBox) throws Exception{
		return userDao.select(paramBox);
	}
	
	/**
	 * List roles of the user
	 * @param paramBox HTTP Request parameters wrapped in HashMap
	 * @return list of current user role
	 * @throws Exception
	 */
	public List<String> listUserRoles(Box paramBox) throws Exception{
		return (List<String>) userDao.listUserRoles(paramBox);
	}
	

}
