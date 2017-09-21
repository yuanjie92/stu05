package org.jie.service.impl;

import org.jie.dao.UserDao;
import org.jie.dao.impl.UserDaoImpl;
import org.jie.model.User;
import org.jie.service.UserService;

public class UserServiceImpl implements UserService {
	
	private UserDao userDao = new UserDaoImpl();

	@Override
	public User findUser(String userName, String userPwd) {
		User user = userDao.queryUserByUserName(userName);
		return user;
	}

	@Override
	public void add(String userName, String userPwd) {
		userDao.save(userName,userPwd);
	}
	
	
}
