package bank.soap;

import java.io.IOException;

import bank.InactiveException;
import bank.OverdrawException;

public interface AccountSoap {

	String getNumber() throws IOException;

	String getOwner() throws IOException;

	boolean isActive() throws IOException;

	void deposit(double amount) throws IOException,
			IllegalArgumentException, InactiveException;

	void withdraw(double amount) throws IOException,
			IllegalArgumentException, OverdrawException, InactiveException;

	double getBalance() throws IOException;
}

