package com.capgemini.bankapp.model;

public class BankAccount {

	private long accountId;
	private String accountHolderName;
	private String accountType;
	private double accountBalance;
	
	public BankAccount() {
		super();
		
	}
	public BankAccount(String accountHOlderName, String accountType, double accountBalance) {
		super();
		this.accountHolderName = accountHOlderName;
		this.accountType = accountType;
		this.accountBalance = accountBalance;
	}
	
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public String getAccountHOlderName() {
		return accountHolderName;
	}
	public void setAccountHOlderName(String accountHOlderName) {
		this.accountHolderName = accountHOlderName;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	@Override
	public String toString() {
		return "BankAccount [accountId=" + accountId + ", accountHOlderName=" + accountHolderName + ", accountType="
				+ accountType + ", accountBalance=" + accountBalance + "]";
	}
	
	
}
