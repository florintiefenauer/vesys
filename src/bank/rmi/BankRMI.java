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
		return bank.createAccount(owner);
	}

	@Override
	public boolean closeAccount(String number) throws IOException {
		return bank.closeAccount(number);
	}

	@Override
	public Set<String> getAccountNumbers() throws IOException {
		return bank.getAccountNumbers();
	}

	@Override
	public IAccount getAccount(String number) throws IOException {
		return bank.getAccount(number);
	}

	@Override
	public void transfer(IAccount a, IAccount b, double amount)
			throws IOException, IllegalArgumentException, OverdrawException,
			InactiveException {
		bank.transfer(a, b, amount);
	
	}

}
