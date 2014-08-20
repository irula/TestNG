package com.weibo.service.impl;

import com.weibo.bean.User;
import com.weibo.service.UserQueryService;
import com.weibo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User query service implementation.
 *
 * @author	Bert Lee
 * @version 2014-7-25
 */
@Service
public class UserQueryServiceImpl implements UserQueryService {

	@Autowired
	private UserService userService;

	@Override
	public String getUserName(long userId) {
		User user = this.userService.getUserInfo(userId);
		return user != null ? user.getName() : "";
	}

	@Override
	public int updateUserName(long userId, String userName) {
		User user = new User(userId, userName);
		int udpateResult = this.userService.updateUserInfo(user);
		return udpateResult;
	}

}
