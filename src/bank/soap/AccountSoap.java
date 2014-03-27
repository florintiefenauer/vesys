package bank.soap;

import java.io.IOException;

import javax.jws.WebParam;
import javax.jws.WebService;

import bank.InactiveException;
import bank.OverdrawException;

@WebService
public interface AccountSoap {
	
	String sayHello(@WebParam(name = "name") String name);

	String getNumber() throws IOException;

	String getOwner() throws IOException;

	boolean isActive() throws IOException;

	void deposit(double amount) throws IOException,
			IllegalArgumentException, InactiveException;

	void withdraw(double amount) throws IOException,
			IllegalArgumentException, OverdrawException, InactiveException;

	double getBalance() throws IOException;
}

