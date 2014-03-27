package bank.soap.server;

import java.io.IOException;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import bank.InactiveException;
import bank.OverdrawException;

@WebService
@SOAPBinding(style = Style.RPC)
public interface BankService {

	String createAccount(String owner) throws IOException;

	boolean closeAccount(String number) throws IOException;

	String[] getAccountNumbers() throws IOException;

	String getAccount(String number) throws IOException;

	void transfer(String from, String to, double amount) throws IOException,
			IllegalArgumentException, OverdrawException, InactiveException;

	String getNumber() throws IOException;

	String getOwner(String number) throws IOException;

	boolean isActive(String number) throws IOException;

	void deposit(String number, double amount) throws IOException, IllegalArgumentException,
			InactiveException;

	void withdraw(String number, double amount) throws IOException, IllegalArgumentException,
			OverdrawException, InactiveException;

	double getBalance(String number) throws IOException;

}
