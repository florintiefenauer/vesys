package bank.rmi;

import java.io.IOException;

import bank.IAccount;
import bank.InactiveException;
import bank.OverdrawException;

public class AccountRMI extends java.rmi.server.UnicastRemoteObject implements IAccountRMI {

	private IAccount acc;
	public AccountRMI(IAccount acc) throws java.rmi.RemoteException {
		super();
		this.acc = acc;
	}
	@Override
	public String getNumber() throws IOException {
		return acc.getNumber();
	}

	@Override
	public String getOwner() throws IOException {
		return acc.getOwner();
	}

	@Override
	public boolean isActive() throws IOException {
		return acc.isActive();
	}

	@Override
	public void deposit(double amount) throws IOException,
			IllegalArgumentException, InactiveException {
		acc.deposit(amount);
		
	}

	@Override
	public void withdraw(double amount) throws IOException,
			IllegalArgumentException, OverdrawException, InactiveException {
		acc.withdraw(amount);
		
	}

	@Override
	public double getBalance() throws IOException {
		return acc.getBalance();
	}

}
