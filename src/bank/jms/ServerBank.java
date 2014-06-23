package bank.jms;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import bank.Bank.Account;
import bank.BankDriver2;
import bank.IAccount;
import bank.IBank;
import bank.InactiveException;
import bank.OverdrawException;

public class ServerBank implements IBank {
	
	private final bank.Bank bank;
	private final BankDriver2.UpdateHandler handler;

	public ServerBank(bank.Bank bank, BankDriver2.UpdateHandler handler){
		this.bank = bank;
		this.handler = handler;
	}
	
	@Override
	public String createAccount(String owner) throws IOException {
		String id = bank.createAccount(owner);
		if (id != null) handler.accountChanged(id);
		return id;
	}

	@Override
	public boolean closeAccount(String number) throws IOException {
		boolean res = bank.closeAccount(number);
		if(res) handler.accountChanged(number);
		return res;
	}

	@Override
	public Set<String> getAccountNumbers() throws IOException {
		return new HashSet<String>(bank.getAccountNumbers());
	}

	@Override
	public IAccount getAccount(String number) throws IOException {
		IAccount acc = bank.getAccount(number);
		return acc == null ? null : new ServerAccount(acc, handler);
	}

	@Override
	public void transfer(IAccount a, IAccount b, double amount)
			throws IOException, IllegalArgumentException, OverdrawException,
			InactiveException {
		bank.transfer(a, b, amount);
		handler.accountChanged(a.getNumber());
		handler.accountChanged(b.getNumber());
	}
	
	public static class ServerAccount implements IAccount {

		private final bank.IAccount acc;
		private final bank.BankDriver2.UpdateHandler handler;
		
		public ServerAccount(bank.IAccount acc, bank.BankDriver2.UpdateHandler handler){
			this.acc = acc;
			this.handler = handler;
		}
		
		@Override
		public String getNumber() throws IOException {
			return acc.getNumber();
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

}
