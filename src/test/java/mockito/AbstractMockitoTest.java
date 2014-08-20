package mockito;

import org.mockito.MockitoAnnotations;
import org.testng.annotations.*;

/**
 * Mockito Test.
 *
 * @author	Edward Lee
 * @version 2013-3-16
 */
public class AbstractMockitoTest {
	@BeforeSuite
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
}
