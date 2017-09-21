package org.jie.dao;

import org.jie.model.User;

public interface UserDao {

	User queryUserByUserName(String userName);

}
