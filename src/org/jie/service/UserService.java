package org.jie.service;

import org.jie.model.User;

public interface UserService {

	User findUser(String userName, String userPwd);

	void add(String userName, String userPwd);

}
