package com.capgemini.bankapp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.apache.log4j.pattern.LogEvent;

import com.capgemini.bankapp.exception.AccountNotFoundException;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;
import com.capgemini.bankapp.service.impl.BankAccountServiceImpl;

public class BankAccountClient {

	static final Logger logger = Logger.getLogger(BankAccountClient.class);

	public static void main(String[] args) throws AccountNotFoundException {

		int choice;
		long accountId;
		String accountHolderName;
		String accountType;
		double accountBalance;
		double amount;
		long fromAccount;
		long toAccount;

		BankAccountService bankService = new BankAccountServiceImpl();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

			while (true) {
				System.out.println("1. Add new BankAccount\n2. Withdraw\n3. Deposit\n4. Fund Transfer");
				System.out.println("5. Delete BankAccount\n6. Display All BankAccount Details");
				System.out
						.println("7. Search BankAccount\n8. Check Balance\n9. Update BankAccount Details\n10. Exit\n");

				System.out.println("Please enter your choice = ");
				choice = Integer.parseInt(reader.readLine());

				switch (choice) {

				case 1:
					System.out.println("Enter account holder name: ");
					accountHolderName = reader.readLine();
					System.out.println("Enter account type: ");
					accountType = reader.readLine();
					System.out.println("Enter account balance: ");
					accountBalance = Double.parseDouble(reader.readLine());

					BankAccount account = new BankAccount(accountHolderName, accountType, accountBalance);
					if (bankService.addNewBankAccount(account))
						System.out.println("Account created successfully...\n");
					else
						System.out.println("failed to create new account...\n");
					break;

				case 2:
					System.out.println("Enter your account_id:");
					accountId = Long.parseLong(reader.readLine());
					System.out.println("Enter the amount you want to withdraw:");
					amount = Double.parseDouble(reader.readLine());

					try {
						System.out.println("Your current Balance is:" + bankService.withdraw(accountId, amount));

					} catch (LowBalanceException | AccountNotFoundException e) {
						// System.out.println(e.getMessage());
						logger.error("Exception:", e);
					}
					break;

				case 3:

					System.out.println("Enter your account Id:");
					accountId = Long.parseLong(reader.readLine());
					System.out.println("Enter the amount you want to Deposit:");
					amount = Double.parseDouble(reader.readLine());
					try {
						System.out.println("Your Current Balance is:" + bankService.deposit(accountId, amount));
					} catch (AccountNotFoundException e) {
						logger.error("Exception:", e);
					}

					break;

				case 4:
					System.out.println("Enter Sender account Id:");
					fromAccount = Long.parseLong(reader.readLine());
					System.out.println("Enter Reciver Account Id:");
					toAccount = Long.parseLong(reader.readLine());
					System.out.println("Enter amount you want to transfer:");
					amount = Double.parseDouble(reader.readLine());

					try {
						BankAccount searchAccount = bankService.searchBankAccount(toAccount);
						System.out.println(
								"Your Current Balance is:" + bankService.fundTransfer(fromAccount, toAccount, amount));
					} catch (LowBalanceException | AccountNotFoundException e) {
						// System.out.println(e.getMessage());
						logger.error("Exception:", e);
					}

					break;

				case 5:
					System.out.println("Enter accountId that you want to delete:");
					accountId = Integer.parseInt(reader.readLine());

					if (bankService.deleteBankAccount(accountId)) {
						System.out.println("Account is deleted.");
					} else
						System.out.println("Account cannot delete.");

					break;

				case 6:
					System.out.println("Bank account details are given below:");
					bankService.findAllBankAccounts();
					break;

				case 7:
					System.out.println("Enter your account_Id:");
					accountId = Long.parseLong(reader.readLine());

					try {
						BankAccount searchAccount = bankService.searchBankAccount(accountId);

						System.out.println("account_id is:" + searchAccount.getAccountId());
						System.out.println("customer_name is:" + searchAccount.getAccountHolderName());
						System.out.println("account_type is:" + searchAccount.getAccountType());
						System.out.println("account_balance is:" + searchAccount.getAccountBalance());

					}

					catch (AccountNotFoundException e) {
						// System.out.println(e.getMessage());
						logger.error("Exception:", e);

					}
					break;

				case 8:
					System.out.println("Enter your accountId:");
					accountId = Long.parseLong(reader.readLine());

					try {
						System.out.println("Your Current Balance is:" + bankService.checkBalance(accountId));

					} catch (AccountNotFoundException e) {
						logger.error("Exception:", e);
					}

					break;

				case 9:

					System.out.println("Enter account_id to update your account:");
					accountId = Long.parseLong(reader.readLine());

					System.out.println("Enter accountHolderName that you want to update:");
					accountHolderName = reader.readLine();

					System.out.println("Enter accountType that you want to update:");
					accountType = reader.readLine();

					BankAccount account1 = new BankAccount(accountId, accountHolderName, accountType);

					if (bankService.updateBankAccount(account1))
						System.out.println("Account updated successfully...\n");
					else
						System.out.println("failed to update account...\n");

					break;

				case 10:
					System.out.println("Thanks for banking with us...");
					System.exit(0);
				}

			}
		} catch (IOException e) {
			// e.printStackTrace();
			logger.error("Exception: ", e);
		}

	}
}
