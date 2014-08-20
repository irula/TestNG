package com.weibo.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weibo.service.UserQueryService;
import com.weibo.service.request.UserIDRequest;
import com.weibo.service.request.UserInfoRequest;
import com.weibo.service.response.UserNameResponse;
import com.weibo.service.response.UserResultResponse;

/**
 * User Controller.
 *
 * @author	Bert Lee
 * @version 2014-8-19
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserQueryService userQueryService;
	
	
	@RequestMapping(value = "/get_user_name", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public UserNameResponse getUserName(@Valid UserIDRequest userIDRequest) {
		long userId = userIDRequest.getId();
		String userName = this.userQueryService.getUserName(userId);
		
		UserNameResponse response = new UserNameResponse();
		if (!StringUtils.isEmpty(userName)) {
			response.setName(userName);
		}
		
		return response;
	}
	
	@RequestMapping(value = "/update_user_name", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public UserResultResponse updateUserName(@Valid @RequestBody UserInfoRequest userInfoRequest) { // JSON request body map
		UserResultResponse response = new UserResultResponse();
		
		long userId = userInfoRequest.getId();
		String userName = userInfoRequest.getUserName();
		int result = this.userQueryService.updateUserName(userId, userName);
		if (result < 0) {
			response.setResult(result);
			response.setResultMessage("update operation is fail");
		}
		
		return response;
	}

}
