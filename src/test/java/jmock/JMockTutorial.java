package jmock;

import org.jmock.Expectations;
import org.testng.annotations.*;

import com.weibo.bank.*;

import static org.testng.Assert.*;

/**
 * jMock代码示例
 *
 * @author	Edward Lee
 * @version 2013-3-16
 */
public class JMockTutorial extends AbstractJMockTest {
	private BankConnection bankConnection;
	private ReceiptPrinter receiptPrinter;
	
	private IAccount customerAccount;
	private IAccount shopAccount;
	
	@BeforeClass
	public void oneTimeSetUp() {
		// 不建议：模拟类型实例！
		this.bankConnection = super.context.mock(BankConnection.class);
		this.receiptPrinter = super.context.mock(ReceiptPrinter.class);
		
		// 模拟同一接口的两个实例
		customerAccount = 
				super.context.mock(IAccount.class, "customer account");
		shopAccount = 
				super.context.mock(IAccount.class, "shop account");
	}
	
	@Test
	public void buyWithCard() {
		PosTerminal posTerminal = new PosTerminal(bankConnection, receiptPrinter);
		
		final String cardNumber = "1000-0000-0001-0003";
		final int amountOfMoney = 100;
		
		// 调用验证与返回值处理
		super.context.checking(new Expectations() {
			{
				oneOf(bankConnection).getAccountByCardNumber(cardNumber);
				will(returnValue(customerAccount));
				// 付款给商家
				oneOf(customerAccount).enter(with(amountOfMoney), 
						with(any(String.class))); // 参数匹配：POS机上无需知道收款人账号
				will(returnValue(true));
				// 付款成功
				oneOf(customerAccount).commit();
				// 打印交易凭据（交易凭据内容必须规范化，不能任意变更）
				oneOf(receiptPrinter).print("Successful Transaction."); 
				
				
				oneOf(bankConnection).getAccountByCardNumber(cardNumber);
				will(returnValue(customerAccount));
				oneOf(customerAccount).enter(with(amountOfMoney), 
						with(any(String.class)));
				will(returnValue(false));
				// 退款给客户
				oneOf(bankConnection).getAccountByAccountNumber(with(any(String.class)));
				will(returnValue(shopAccount));
				// 退款成功
				oneOf(shopAccount).withdraw(amountOfMoney, cardNumber);
				will(returnValue(true));
				oneOf(shopAccount).commit();
				oneOf(receiptPrinter).print("Successful withdrawal.");
				oneOf(receiptPrinter).print("Fail Transaction.");
			}
		});
		
		boolean success = posTerminal.buyWithCard(amountOfMoney, cardNumber);
		assertEquals(success, true);
		success = posTerminal.buyWithCard(amountOfMoney, cardNumber);
		assertEquals(success, false);
	}
	
	@Test(description = "异常处理", 
			expectedExceptions = { RuntimeException.class })
	public void exceptionHandle() {
		PosTerminal posTerminal = new PosTerminal(bankConnection, receiptPrinter);
		
		super.context.checking(new Expectations() {
			{
				oneOf(bankConnection).getAccountByCardNumber(with(any(String.class)));
				will(throwException(new RuntimeException()));
			}
		});
		
		posTerminal.buyWithCard(0, null);
	}
}
