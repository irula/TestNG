package com.weibo;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeSuite;

/**
 * Abstract base test class for TestNG.
 *
 * @author	Bert Lee
 * @version 2014-8-19
 */
@ContextConfiguration("classpath:META-INF/spring/test-context.xml") // 集成应用上下文并加载默认的beans XML配置
public abstract class AbstractTestNGTest extends AbstractTestNGSpringContextTests { // 集成TestNG

	/**
	 * Initializes the test context.
	 */
	@BeforeSuite(alwaysRun = true)
	public void init() {
//		MockitoAnnotations.initMocks(this); // 基于Spring自动装配注解，这里不再需要初始化
	}

}
