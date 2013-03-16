package mockito;

import org.mockito.*;
import org.testng.annotations.*;

import com.weibo.bank.*;

import static org.testng.Assert.*;

import static org.mockito.Mockito.*;

/**
 * Mockito代码示例
 * 
 * @author Edward Lee
 * @version 2013-3-16
 */
public class MockitoTutorial extends AbstractMockitoTest {
	// 优先使用：使用注解模拟实例
	@Mock
	private BankConnection bankConnection;
	@Mock
	private ReceiptPrinter receiptPrinter;
	@Mock
	private IAccount customerAccount;
	@Mock
	private IAccount shopAccount;

	@BeforeClass
	public void oneTimeSetUp() {
		// // 不建议：模拟类型实例！
		// bankConnection = mock(BankConnection.class);
		// receiptPrinter = mock(ReceiptPrinter.class);
		//
		// // 模拟同一接口的两个实例
		// customerAccount = mock(IAccount.class);
		// shopAccount = mock(IAccount.class);
	}

	@Test
	public void buyWithCard() {
		PosTerminal posTerminal = new PosTerminal(bankConnection,
				receiptPrinter);

		final String cardNumber = "1000-0000-0001-0003";
		final int amountOfMoney = 100;

		// 调用验证与返回值处理
		when(bankConnection.getAccountByCardNumber(cardNumber)).thenReturn(
				customerAccount);

		when(customerAccount.enter(eq(amountOfMoney), anyString())).thenReturn( // 参数匹配：POS机上无需知道收款人账号
				true);

		// stub a void method
		doNothing().when(customerAccount).commit();

		// Other when(...).thenReturn(...) calls ...

		boolean success = posTerminal.buyWithCard(amountOfMoney, cardNumber);

		// 验证调用次数（改造时，可避免不必要的多次函数调用）
		verify(receiptPrinter, times(1)).print("Successful Transaction."); // 交易操作的打印信息必须规范化，不能任意变更
		assertEquals(success, true);
	}

	@Test
	public void buyWithCard_Fail() {
		PosTerminal posTerminal = new PosTerminal(bankConnection,
				receiptPrinter);

		final String cardNumber = "1000-0000-0001-0003";
		final int amountOfMoney = 100;

		when(bankConnection.getAccountByCardNumber(cardNumber)).thenReturn(
				customerAccount);
		// 付款失败
		when(customerAccount.enter(eq(amountOfMoney), anyString())).thenReturn(
				false);
		// 退款给客户
		when(bankConnection.getAccountByAccountNumber(anyString())).thenReturn(
				shopAccount);
		// 退款成功
		when(shopAccount.withdraw(amountOfMoney, cardNumber)).thenReturn(
				true);
		doNothing().when(shopAccount).commit();
		
		boolean success = posTerminal.buyWithCard(amountOfMoney, cardNumber);
		
		// 验证交易凭据信息
		verify(receiptPrinter, times(1)).print("Successful withdrawal.");
		verify(receiptPrinter, times(1)).print("Fail Transaction.");
		
		assertEquals(success, false);
	}
	
	@Test
	public void buyWithCard_Fail_rollback() {
		PosTerminal posTerminal = new PosTerminal(bankConnection,
				receiptPrinter);

		final String cardNumber = "1000-0000-0001-0003";
		final int amountOfMoney = 100;

		when(bankConnection.getAccountByCardNumber(cardNumber)).thenReturn(
				customerAccount);
		// 付款失败
		when(customerAccount.enter(eq(amountOfMoney), anyString())).thenReturn(
				false);
		// 退款给客户
		when(bankConnection.getAccountByAccountNumber(anyString())).thenReturn(
				shopAccount);
		// 退款失败
		when(shopAccount.withdraw(amountOfMoney, cardNumber)).thenReturn(
				false);
		// 回滚数据库数据
		doNothing().when(customerAccount).rollback();
		doNothing().when(shopAccount).rollback();
		
		boolean success = posTerminal.buyWithCard(amountOfMoney, cardNumber);
		
		// 验证交易凭据信息
		verify(receiptPrinter, times(1)).print("Fail withdrawal!");
		verify(receiptPrinter, times(2)).print("Fail Transaction.");
		
		assertEquals(success, false);
	}
	
	@Test(description = "异常处理", 
			expectedExceptions = { RuntimeException.class })
	public void exceptionHandle() {
		PosTerminal posTerminal = new PosTerminal(bankConnection, receiptPrinter);
		
		when(bankConnection.getAccountByAccountNumber(anyString())).thenThrow(
				new RuntimeException());
		
		posTerminal.buyWithCard(0, null);
	}
}
