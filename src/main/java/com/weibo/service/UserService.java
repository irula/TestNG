package com.weibo.service;

import com.weibo.bean.User;

/**
 * User service.
 *
 * @author	Bert Lee
 * @version 2014-7-25
 */
public interface UserService {

	/**
	 * Gets user info for specified user ID.
	 * 
	 * @param id user ID
	 * @return
	 */
	User getUserInfo(long id);
	
	/**
	 * Updates user info.
	 * 
	 * @param user user info
	 * @return -1 means fail, 0 means success.
	 */
	int updateUserInfo(User user);

}
