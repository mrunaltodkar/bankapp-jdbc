package com.capgemini.bankapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.capgemini.bankapp.dao.BankAccountDao;
import com.capgemini.bankapp.util.DbUtil;

public class BankAccountDaoImpl implements BankAccountDao {

	@Override
	public double getBalance(long accountId) {
		String query = "SELECT account_balance FROM bankaccounts WHERE account_id = " + accountId;
		double balance = 0;

		try (Connection connection = DbUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet result = statement.executeQuery()) {

			balance = result.getDouble(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return balance;
	}

	@Override
	public void updateBalance(long accountId, double newBalance) {
		String query = "UPDATE bankaccounts SET account_balance = ? WHERE account_id = ?";

		try (Connection connection = DbUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setDouble(1, newBalance);
			statement.setLong(2, accountId);

			int result = statement.executeUpdate();
			System.out.println("No. of rows updated:" + result);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean deleteAccount(long accountId) {
		String query = "DELETE FROM bankacconts WHERE accountId =" + accountId;
		int result;

		try (Connection connection = DbUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			result = statement.executeUpdate();
			if (result == 1)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
