package com.weibo.bank;

/**
 * Bank Account Transaction Operation.
 *
 * @author	Edward Lee
 * @version 2013-3-15
 */
public interface IAccount {
	/**
	 * 退款给某账户。
	 * 
	 * @param amountOfMoney
	 *            交易金额
	 * @param destAccountNumber
	 *            退款账户
	 * @return <code>true</code>：交易成功；<code>false</code>：交易失败。
	 */
	boolean withdraw(int amountOfMoney, String destAccountNumber);

	/**
	 * 某账户付款给我。
	 * 
	 * @param amountOfMoney
	 *            交易金额
	 * @param srcAccountNumber
	 *            付款账户
	 * @return <code>true</code>：交易成功；<code>false</code>：交易失败。
	 */
	boolean enter(int amountOfMoney, String srcAccountNumber);

	/**
	 * 回滚本次交易。
	 */
	void rollback();

	/**
	 * 提交本次交易。
	 */
	void commit();
}
