package org.jie.dao;

import org.jie.model.User;

public interface UserDao {

	User queryUserByUserName(String userName);

	boolean save(String userName, String userPwd);

}
