package jmock;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;

/**
 * jMock Test.
 *
 * @author	Edward Lee
 * @version 2013-3-16
 */
public class AbstractJMockTest {
	/* Context Mock */
	protected Mockery context = new Mockery() {
		{
			// 允许模拟类型实例
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
}
