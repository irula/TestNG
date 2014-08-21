package com.weibo.web;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.weibo.AbstractControllerTestNGTest;
import com.weibo.service.UserQueryService;

/**
 * Test for {@link UserController}.
 *
 * @author	Bert Lee
 * @version 2014-8-19
 */
public class UserControllerTest extends AbstractControllerTestNGTest {

	// tested controller
	@Autowired
	private UserController userControllerTest;
	
	// mocked service (被依赖的服务)
	@Autowired
	private UserQueryService userQueryService;
	
	
	@Test(dataProvider = "getUserName")
	public void getUserName(Object[] params, String userName, String expectedContent) throws Exception {
		// 1. 定义"被依赖的服务"的方法行为
		when(this.userQueryService.getUserName(anyLong())).thenReturn(userName);
		
		this.getMock("/user/get_user_name?id={id}", params, expectedContent);
	}
	@DataProvider(name = "getUserName")
	protected static final Object[][] getUserNameTestData() {
		Object[][] testData = new Object[][] {
				{ new Object[] { "23" }, "Bert Lee", "{\"name\":\"Bert Lee\"}" },
		};
		return testData;
	}
	
	@Test(dataProvider = "updateUserName")
	public void updateUserName(String paramsJson, Integer result, String expectedContent) throws Exception {
		// 1. 定义"被依赖的服务"的方法行为
		when(this.userQueryService.updateUserName(anyLong(), anyString())).thenReturn(result);
		
		this.postMock("/user/update_user_name", paramsJson, expectedContent);
	}
	@DataProvider(name = "updateUserName")
	protected static final Object[][] updateUserNameTestData() {
		Object[][] testData = new Object[][] {
				{ "{\"id\":23,\"name\":\"Bert Lee\"}", 0, "{\"ret\":0,\"ret_msg\":\"ok\"}" },
		};
		return testData;
	}
	
	@Override
	public Object getController() {
		return this.userControllerTest;
	}

}
