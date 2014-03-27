package bank.soap;

import java.io.IOException;
import java.util.Date;

import javax.jws.WebParam;
import javax.jws.WebService;

import bank.InactiveException;
import bank.OverdrawException;

@WebService
public class Account implements AccountSoap {

	@Override
	public String sayHello(@WebParam(name = "name") String name) {
		return "Hello " + name + " from SOAP at " + new Date();
	}

	@Override
	public String getNumber() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOwner() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActive() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deposit(double amount) throws IOException,
			IllegalArgumentException, InactiveException {
		// TODO Auto-generated method stub

	}

	@Override
	public void withdraw(double amount) throws IOException,
			IllegalArgumentException, OverdrawException, InactiveException {
		// TODO Auto-generated method stub

	}

	@Override
	public double getBalance() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

}
