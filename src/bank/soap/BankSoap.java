package bank.soap;

import java.io.IOException;
import java.util.Set;

import javax.jws.WebParam;
import javax.jws.WebService;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;

@WebService
public interface BankSoap{

	String sayHello(@WebParam(name = "name") String name);
	
	String createAccount(String owner) throws IOException;

	boolean closeAccount(String number) throws IOException;

	Set<String> getAccountNumbers() throws IOException;

//	@WebMethod(exclude = true)
	Account getAccount(String number) throws IOException;

//	@WebMethod(exclude = true)
	void transfer(Account a, Account b, double amount)
			throws IOException, IllegalArgumentException, OverdrawException,
			InactiveException;
}