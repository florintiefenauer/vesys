package bank.soap.server;

import java.io.IOException;

import javax.jws.WebService;

import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;

@WebService
public class BankServiceImpl implements BankService {

	private Bank bank;
	
	public BankServiceImpl(){}

	public BankServiceImpl(Bank bank) {
		this.bank = bank;
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
	public Object[] getAccountNumbers() throws IOException {
		return bank.getAccountNumbers().toArray();
	}

	@Override
	public String getAccount(String number) throws IOException {
		if(number == null) throw new IllegalArgumentException();
		return bank.getAccount(number).getOwner();
	}

	@Override
	public void transfer(String from, String to, double amount)
			throws IOException, IllegalArgumentException, OverdrawException,
			InactiveException {
		bank.transfer(bank.getAccount(from), bank.getAccount(to), amount);
	}

	@Override
	public boolean isActive(String number) throws IOException {
		return bank.getAccount(number).isActive();
	}

	@Override
	public void deposit(String number, double amount) throws IOException,
			IllegalArgumentException, InactiveException {
		bank.getAccount(number).deposit(amount);
	}

	@Override
	public void withdraw(String number, double amount) throws IOException,
			IllegalArgumentException, OverdrawException, InactiveException {
		bank.getAccount(number).withdraw(amount);
	}

	@Override
	public double getBalance(String number) throws IOException {
		return bank.getAccount(number).getBalance();
	}
}
