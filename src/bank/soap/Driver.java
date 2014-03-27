package bank.soap;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

import bank.InactiveException;
import bank.OverdrawException;
import bank.soap.server.BankService;
import bank.soap.server.BankServiceImpl;

public class Driver implements bank.BankDriver {

	private Bank bank = null;
	private BankService service;
	private BankServiceImpl port;

	@Override
	public void connect(String[] args) throws UnknownHostException, IOException {
		
		 service = new BankService();
		 port = service.getBankServiceImpl();
		
		bank = new Bank(port);
	}

	@Override
	public void disconnect() throws IOException {
		bank = null;
	}

	@Override
	public Bank getBank() {
		return bank;
	}
	

	static class Bank implements bank.IBank {

		BankServiceImpl port;
		private Bank(BankServiceImpl port){
			this.port = port;
			
		}

		@Override
		public Set<String> getAccountNumbers() throws IOException {			
			String[] strArr = (String[])port.getAccountNumbers();
			Set <String> strSet = new HashSet<String>();
			for(String s : strArr){
				strSet.add(s);
			}
			return strSet;		
		}

		@Override
		public String createAccount(String owner) throws IOException {
			return port.createAccount(owner);
		}

		@Override
		public boolean closeAccount(String number) throws IOException {
			return port.closeAccount(number);
		}

		@Override
		public bank.IAccount getAccount(String number) throws IOException {
			return new Account (number, port.getAccount(number), port);
		}

		@Override
		public void transfer(bank.IAccount from, bank.IAccount to, double amount)
				throws IOException, InactiveException, OverdrawException {
			port.transfer(from.getNumber(), to.getNumber(), amount);
		}

	}

	static class Account implements bank.IAccount {

		private String number;
		private String owner;
		private BankServiceImpl port;

		private Account(String number, String owner, BankServiceImpl port) {
			this.number = number;
			this.owner = owner;
			this.port = port;
		}

		@Override
		public double getBalance() throws IOException {
			return port.getBalance(number);
		}

		@Override
		public String getOwner() {
			return owner;
		}

		@Override
		public String getNumber() {
			return number;
		}

		@Override
		public boolean isActive() throws IOException {
			return port.isActive(number);
		}

		@Override
		public void deposit(double amount) throws InactiveException,
				IOException {
			port.deposit(number, amount);
		}

		@Override
		public void withdraw(double amount) throws InactiveException,
				OverdrawException, IOException {
			port.withdraw(number, amount);
		}

	}

}
