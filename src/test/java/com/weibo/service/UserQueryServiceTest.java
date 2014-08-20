package com.weibo.service;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

import com.weibo.AbstractTestNGTest;
import com.weibo.bean.User;
import com.weibo.service.UserQueryService;
import com.weibo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test for {@link UserQueryService}.
 *
 * @author	Bert Lee
 * @version 2014-7-25
 */
public class UserQueryServiceTest extends AbstractTestNGTest {

	// tested service
	@Autowired
	private UserQueryService userQueryServiceTest;
	
	// mocked service (被依赖的服务)
	@Autowired
	private UserService userService;
	
	
	@Test(dataProvider = "getUserName")
	public void getUserName(User user, String expected) {
		// 1. 定义"被依赖的服务"的方法行为
		when(userService.getUserInfo(anyLong())).thenReturn(user);
		
		String userName = this.userQueryServiceTest.getUserName(3L);
		assertEquals(userName, expected);
	}
	@DataProvider(name = "getUserName")
	protected static final Object[][] getUserNameTestData() {
		Object[][] testData = new Object[][] {
				{ null, "" },
				{ new User(3L, ""), "" },
				{ new User(10L, "Edward Lee"), "Edward Lee" },
				{ new User(23L, "李华刚@!~#$%^&"), "李华刚@!~#$%^&" },
		};
		return testData;
	}

}
