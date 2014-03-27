package bank.soap;

import java.io.IOException;

import bank.InactiveException;
import bank.OverdrawException;

public class BankServiceImpl implements BankService {

	private Bank bank;

	public BankServiceImpl(Bank bank) {
		this.bank = bank;
	}

	@Override
	public String createAccount(String owner) throws IOException {
		System.out.println("Create account " + owner);
		return this.bank.createAccount(owner);
	}

	@Override
	public boolean closeAccount(String number) throws IOException {
		System.out.println("Close account " + number);
		return this.bank.closeAccount(number);
	}

	@Override
	public Object[] getAccountNumbers() throws IOException {
		System.out.println("Get account numbers");
		return this.bank.getAccountNumbers();
	}

	@Override
	public String getAccount(String number) throws IOException {
		System.out.println("GetAccount " + number);
		if (bank.getAccount(number) != null)
			return number;
		else {
			System.err.println("  Account " + number + " does not exist!");
			return "";
		}
	}

	@Override
	public void transfer(String from, String to, double amount)
			throws IOException, IllegalArgumentException, OverdrawException,
			InactiveException {
		System.out
				.println("Transfer " + amount + " from " + from + " to " + to);
		try {
			bank.transfer(bank.getAccount(from), bank.getAccount(to), amount);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public String getNumber() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOwner(String number) throws IOException {
		System.out.println("Get owner " + number);
		bank.Account acc = this.bank.getAccount(number);
		if (acc != null)
			return acc.getOwner();
		else
			return "";
	}

	@Override
	public boolean isActive(String number) throws IOException {
		System.out.println("Get isActive " + number);
		if (this.bank.getAccount(number) == null)
			throw new IOException();
		return this.bank.getAccount(number).isActive();
	}

	@Override
	public void deposit(String number, double amount) throws IOException,
			IllegalArgumentException, InactiveException {
		System.out.println("Deposit " + amount + " to " + number);
		try {
			this.bank.getAccount(number).deposit(amount);
		} catch (IOException e) {
			throw new IOException();
		}
	}

	@Override
	public void withdraw(String number, double amount) throws IOException,
			IllegalArgumentException, OverdrawException, InactiveException {
		System.out.println("Withdraw " + amount + " from " + number);
		try {
			this.bank.getAccount(number).withdraw(amount);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public double getBalance(String number) throws IOException {
		System.out.println("Get balance " + number);
		if (this.bank.getAccount(number) == null)
			throw new IOException();
		return this.bank.getAccount(number).getBalance();
	}

}
