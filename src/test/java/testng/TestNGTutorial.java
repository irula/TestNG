package testng;

import java.util.*;

import org.testng.annotations.*;

import static java.lang.System.out;

import static org.testng.Assert.*;

/**
 * TestNG代码示例
 * 
 * 体会，比JUnit做得更好的地方：
 * 	1，测试结果报告清晰而详细。
 * 	2，通过XML来配置管理测试套件、测试集、测试类、测试组，更灵活。
 * 	3，很多功能的实现方式更优雅。
 *
 * @author	Edward Lee
 * @version 2013-3-3
 */
@Test
public class TestNGTutorial {
	@BeforeClass
	public void oneTimeSetUp() {
		out.println("@BeforeClass - oneTimeSetUp");
		// 统一模拟JDBC、MC、服务各层等接口
	}
	
	/**
	 * Exception Test
	 */
	@Test(expectedExceptions = IndexOutOfBoundsException.class)
	public void indexOutOfBoundsException() {
		List<Object> list = new ArrayList<Object>(0);
		assertNull(list.get(0));
	}
	
	/**
	 * Ignore Test
	 */
	@Test(enabled = false)
	public void ignoreTest() {
		assertEquals(0, 1); // fail
	}
	
	/**
	 * Time Test
	 */
	@Test(timeOut = 1000)
	public void timeOutTest() {
		// HttpRequest
	}
	
	
	/*
	 * Group Test - new function for TestNG
	 * 
	 * group：可根据功能分类，让集成测试变成可能。
	 */
	@Test(groups = "jdbc")
	public void jdbc() {
		// JdbcTemplate
	}
	
	@Test(groups = "dao")
	public void dao() {
		// DB Tier
	}
	
	@Test(groups = "cache")
	public void cache() {
		// Cache Tier
	}
	
	@Test(groups = "service")
	public void service() {
		// Service Tier
	}
	
	/*
	 * @DataProvider for Parameterized Test
	 * 
	 * 提供所有可能的参数测试数据，包括边界值、特殊值。
	 */
	// This function will provide the parameter data
	@DataProvider(name= "Data-Provider-Function")
	protected static final Object[][] dataProvider() {
		return new Object[][] {
				{ List.class, new String[] { "java.util.AbstractList", "java.util.ArrayList"} }, 
				{ String.class, new String[] { "2", "3" } }
		};
	}
	
	@Test(dataProvider = "Data-Provider-Function")
	public void parameter(Class<?> clazz, String[] ss) {
		for (String s : ss) {
			out.println("Parameterized Number is : " + s);
		}
	}
	
	/*
	 * Dependency Test
	 */
	@Test(dependsOnMethods = { "jdbc", "dao", "cache" })
	public void dependency() {
		
	}
}
