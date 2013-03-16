package junit;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.Assert.*;

/**
 * Suite Test
 *
 * @author	Edward Lee
 * @version 2013-3-4
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	/*
	 * 缺点：若需变更测试范围，则需重新编译打包！
	 */
	JUnit4Tutorial.class
})

public class SuiteTest {
	
	@Test
	public void suiteTest() {
		assertEquals(true, true);
	}
}
