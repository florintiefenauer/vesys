package bank.rmi;

import java.io.IOException;

import bank.IAccount;
import bank.InactiveException;
import bank.OverdrawException;

public class AccountRMI extends java.rmi.server.UnicastRemoteObject implements IAccountRMI {

	private IAccount acc;
	private IBankRMI bank;
	public AccountRMI(IAccount acc, IBankRMI bank) throws java.rmi.RemoteException {
		this.acc = acc;
		this.bank = bank;
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
		bank.notifyUpdateHandler(this.getNumber());
		
	}

	@Override
	public void withdraw(double amount) throws IOException,
			IllegalArgumentException, OverdrawException, InactiveException {
		acc.withdraw(amount);
		bank.notifyUpdateHandler(this.getNumber());		
	}

	@Override
	public double getBalance() throws IOException {
		return acc.getBalance();
	}

}
