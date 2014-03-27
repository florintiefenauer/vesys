package bank.soap;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

import javax.jws.WebParam;
import javax.jws.WebService;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;

@WebService
public class Bank implements BankSoap {

	@Override
	public String sayHello(@WebParam(name = "name") String name) {
		return "Hello " + name + " from SOAP at " + new Date();
	}

	@Override
	public String createAccount(String owner) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean closeAccount(String number) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object[] getAccountNumbers() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account getAccount(String number) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void transfer(Account a, Account b, double amount)
			throws IOException, IllegalArgumentException, OverdrawException,
			InactiveException {
		// TODO Auto-generated method stub

	}

}
