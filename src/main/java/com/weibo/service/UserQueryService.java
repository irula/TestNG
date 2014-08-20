package com.weibo.service;

/**
 * User query service.
 *
 * @author	Bert Lee
 * @version 2014-7-25
 */
public interface UserQueryService {

	/**
	 * Gets user name for specified user ID.
	 * 
	 * @param userId user ID
	 * @return
	 */
	String getUserName(long userId);
	
	/**
	 * Updates user name for specified user ID.
	 * 
	 * @param userId
	 * @param userName
	 * @return -1 means fail, 0 means success.
	 */
	int updateUserName(long userId, String userName);

}
