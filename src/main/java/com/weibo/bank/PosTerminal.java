package com.weibo.bank;

/**
 * POS Terminal.
 *
 * @author	Edward Lee
 * @version 2013-3-15
 */
public class PosTerminal {
	private static final String SHOP_ACCOUNT_NUMBER = "00000000-00000000"; // NOPMD by Administrator on 13-3-16 ����3:54
	
	private final transient BankConnection bankConnection;
	private final transient ReceiptPrinter receiptPrinter;
	
	public PosTerminal(final BankConnection bankConnection,
			final ReceiptPrinter receiptPrinter) {
		super();
		this.bankConnection = bankConnection;
		this.receiptPrinter = receiptPrinter;
	}
	
	public boolean buyWithCard(final int amountOfMoney, final String cardNumber) {
		final IAccount customerAccount = bankConnection.getAccountByCardNumber(cardNumber);
		boolean success = customerAccount.enter(amountOfMoney, SHOP_ACCOUNT_NUMBER);
		if (success) {
			customerAccount.commit();
			receiptPrinter.print("Successful Transaction.");
		} else {
			final IAccount shopAccount = bankConnection.getAccountByAccountNumber(SHOP_ACCOUNT_NUMBER);
			success = shopAccount.withdraw(amountOfMoney, cardNumber);
			if (success) {
				shopAccount.commit();
				receiptPrinter.print("Successful withdrawal.");
			} else {
				customerAccount.rollback();
				shopAccount.rollback();
				receiptPrinter.print("Fail withdrawal!");
			}
			
			receiptPrinter.print("Fail Transaction.");
			success = false; // mark the transaction is fail.
		}
		return success;
	}

	public BankConnection getBankConnection() {
		return bankConnection;
	}

	public ReceiptPrinter getReceiptPrinter() {
		return receiptPrinter;
	}
	
}
