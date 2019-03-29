package com.capgemini.bankapp.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.capgemini.bankapp.dao.BankAccountDao;
import com.capgemini.bankapp.dao.impl.BankAccountDaoImpl;
import com.capgemini.bankapp.exception.AccountNotFoundException;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;
import com.capgemini.bankapp.util.DbUtil;

public class BankAccountServiceImpl implements BankAccountService {

	private BankAccountDao bankAccountDao;
	static final Logger logger = Logger.getLogger(BankAccountServiceImpl.class);

	public BankAccountServiceImpl() {
		bankAccountDao = new BankAccountDaoImpl();
	}

	@Override
	public double checkBalance(long accountId) throws AccountNotFoundException {
		double balance = bankAccountDao.getBalance(accountId);
		if (balance > 0)
			return balance;
		else
			throw new AccountNotFoundException("BankAccount does not exists...");
	}

	@Override
	public double withdraw(long accountId, double amount) throws LowBalanceException, AccountNotFoundException {
		double balance = bankAccountDao.getBalance(accountId);

		if (balance < 0)
			throw new AccountNotFoundException("Bank account does not exist...");
		else if (balance - amount >= 0) {
			balance = balance - amount;
			bankAccountDao.updateBalance(accountId, balance);
			DbUtil.commit();
			return balance;
		} else
			throw new LowBalanceException("You don't have sufficient balance.");
	}

	@Override
	public double deposit(long accountId, double amount) throws AccountNotFoundException {
		double balance = bankAccountDao.getBalance(accountId);
		if (balance < 0)
			throw new AccountNotFoundException("Bank account does not exist...");
		balance = balance + amount;
		bankAccountDao.updateBalance(accountId, balance);
		DbUtil.commit();
		return balance;

	}

	@Override
	public boolean deleteBankAccount(long accountId) throws AccountNotFoundException {
		boolean result = bankAccountDao.deleteBankAccount(accountId);
		if(result) {
			DbUtil.commit();
			return result;
		}
		throw new AccountNotFoundException("Bank account does not exist...");
	}

	@Override
	public double fundTransfer(long fromAccount, long toAccount, double amount) throws LowBalanceException, AccountNotFoundException {
		
		try {
			double newBalance = withdrawForFundTransfer(fromAccount, amount);
			deposit(toAccount, amount);
			DbUtil.commit();
			return newBalance;
		}
		catch(LowBalanceException | AccountNotFoundException e) {
			logger.error("Exception", e);
			DbUtil.rollback();
			throw e;
		}

	}

	public double withdrawForFundTransfer(long accountId, double amount) throws AccountNotFoundException, LowBalanceException {
		double balance = bankAccountDao.getBalance(accountId);

		if (balance < 0)
			throw new AccountNotFoundException("Bank account does not exist...");
		else if (balance - amount >= 0) {
			balance = balance - amount;
			bankAccountDao.updateBalance(accountId, balance);
			return balance;
		} else
			throw new LowBalanceException("You don't have sufficient balance.");
	}

	@Override
	public boolean addNewBankAccount(BankAccount account) {
		boolean result = bankAccountDao.addNewBankAccount(account);
		if(result)
			DbUtil.commit();
		return result;
	}

	@Override
	public List<BankAccount> findAllBankAccounts() {

		return bankAccountDao.findAllBankAccounts();

	}

	@Override
	public BankAccount searchBankAccount(long accountId) throws AccountNotFoundException {

		BankAccount account = bankAccountDao.searchBankAccount(accountId);
		if(account != null)
			return account;
		else
			throw new AccountNotFoundException("Bank account does not exist");
		
		
			
	}

	@Override
	public boolean updateBankAccount(BankAccount account) {

		boolean result = bankAccountDao.updateBankAccount(account);
		if(result)
			DbUtil.commit();
		return result;
	}

}
