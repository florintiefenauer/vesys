package bank.soap;

import java.io.IOException;
import java.util.Set;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;

public interface BankSoap{

	String createAccount(String owner) throws IOException;

	boolean closeAccount(String number) throws IOException;

	Set<String> getAccountNumbers() throws IOException;

	Account getAccount(String number) throws IOException;

	void transfer(Account a, Account b, double amount)
			throws IOException, IllegalArgumentException, OverdrawException,
			InactiveException;
}