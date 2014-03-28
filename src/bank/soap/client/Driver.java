package bank.soap.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bank.InactiveException;
import bank.OverdrawException;
import bank.soap.client.jaxws.BankServiceImpl;
import bank.soap.client.jaxws.BankServiceImplService;
import bank.soap.client.jaxws.IOException_Exception;
import bank.soap.client.jaxws.InactiveException_Exception;
import bank.soap.client.jaxws.OverdrawException_Exception;

public class Driver implements bank.BankDriver {

	private Bank bank = null;
	private BankServiceImplService service;
	private BankServiceImpl port;

	@Override
	public void connect(String[] args) throws UnknownHostException, IOException {
		
		 service = new BankServiceImplService();
		 port = service.getBankServiceImplPort();
		
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
		public Set<String> getAccountNumbers() throws IOException{			
			List<Object> strList = null;
			try {
				strList = port.getAccountNumbers();
			} catch (IOException_Exception e) {
			    throw new IOException();
			}
			Set <String> strSet = new HashSet<String>();
			for(Object s : strList){
				strSet.add((String) s);
			}
			return strSet;		
		}

		@Override
		public String createAccount(String owner) throws IOException {
			try {
				return port.createAccount(owner);
			} catch (IOException_Exception e) {
				throw new IOException();
			} 
		}

		@Override
		public boolean closeAccount(String number) throws IOException {
			try {
				return port.closeAccount(number);
			} catch (IOException_Exception e) {
				throw new IOException();
			}
		}

		@Override
		public bank.IAccount getAccount(String number) throws IOException {
			String owner = null;
			try {
				owner = port.getAccount(number);				
			} catch (IOException_Exception e) {
				throw new IOException();
			}
			if(owner!=null){
				return new Account (number,owner , port);
			}
			return null;
		}

		@Override
		public void transfer(bank.IAccount from, bank.IAccount to, double amount)
				throws IOException, InactiveException, OverdrawException {
			try {
				port.transfer(from.getNumber(), to.getNumber(), amount);
			} catch (IOException_Exception e) {
				throw new IOException();
			} catch (InactiveException_Exception e) {
				throw new InactiveException();
			} catch (OverdrawException_Exception e) {
				throw new OverdrawException();
			}
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
			try {
				return port.getBalance(number);
			} catch (IOException_Exception e) {
				throw new IOException();
			}
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
			try {
				return port.isActive(number);
			} catch (IOException_Exception e) {
				throw new IOException();
			}
		}

		@Override
		public void deposit(double amount) throws InactiveException,
				IOException {
			try {
				port.deposit(number, amount);
			} catch (IOException_Exception e) {
				throw new IOException();
			} catch (InactiveException_Exception e) {
				throw new InactiveException();
			}
		}

		@Override
		public void withdraw(double amount) throws InactiveException,
				OverdrawException, IOException {
			try {
				port.withdraw(number, amount);
			} catch (IOException_Exception e) {
				throw new IOException();
			} catch (InactiveException_Exception e) {
				throw new InactiveException();
			} catch (OverdrawException_Exception e) {
				throw new OverdrawException();
			}
		}

	}

}
