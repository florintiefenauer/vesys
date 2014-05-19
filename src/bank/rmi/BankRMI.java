package bank.rmi;

import java.io.IOException;
import java.util.Set;

import bank.Bank;
import bank.IAccount;
import bank.InactiveException;
import bank.OverdrawException;

public class BankRMI extends java.rmi.server.UnicastRemoteObject implements IBankRMI{

	private Bank bank;
	
	public BankRMI() throws java.rmi.RemoteException {
		bank = new Bank();
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
	public Set<String> getAccountNumbers() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAccount getAccount(String number) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void transfer(IAccount a, IAccount b, double amount)
			throws IOException, IllegalArgumentException, OverdrawException,
			InactiveException {
		// TODO Auto-generated method stub
		
	}

}
