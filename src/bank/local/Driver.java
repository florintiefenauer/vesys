/*
 * Copyright (c) 2000-2014 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package bank.local;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import bank.InactiveException;
import bank.OverdrawException;

public class Driver implements bank.BankDriver {
	private Bank bank = null;

	@Override
	public void connect(String[] args) {
		bank = new Bank();
		System.out.println("connected...");
	}

	@Override
	public void disconnect() {
		bank = null;
		System.out.println("disconnected...");
	}

	@Override
	public Bank getBank() {
		return bank;
	}

	static class Bank implements bank.Bank {

		private final Map<String, Account> accounts = new HashMap<>();

		@Override
		public Set<String> getAccountNumbers() {
			Set<String> accNumbers = new HashSet<String>();
			for(Account acc : accounts.values()){
				if(acc.isActive()) accNumbers.add(acc.getNumber()); 
			}
			return accNumbers;
		}

		@Override
		public String createAccount(String owner) {
			if(owner != null){
				Account acc = new Account(owner);
				String number = acc.getNumber();
				accounts.put(number, acc);
				return number;
			}
			return null;
		}

		@Override
		public boolean closeAccount(String number) {
			Account acc = accounts.get(number);
			if(acc.balance == 0 && acc.isActive()){
				acc.active = false;
				return true;
			}
			return false;
		}

		@Override
		public bank.Account getAccount(String number) {
			return accounts.get(number);
		}

		@Override
		public void transfer(bank.Account from, bank.Account to, double amount)
				throws IOException, InactiveException, OverdrawException {
			from.withdraw(amount);
			to.deposit(amount);
		}

	}

	static class Account implements bank.Account {
		private String number;
		private String owner;
		private double balance;
		private boolean active = true;
		private static int count = 0;

		Account(String owner) {
			this.owner = owner;
			count++;
			this.number = owner.substring(0,1) + "-" +count;
			active = true;
		}

		@Override
		public double getBalance() {
			return balance;
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
		public boolean isActive() {
			return active;
		}

		@Override
		public void deposit(double amount) throws InactiveException {
			if(!isActive()) throw new InactiveException();
			if(amount < 0) throw new IllegalArgumentException();
			balance += amount;
		}

		@Override
		public void withdraw(double amount) throws InactiveException, OverdrawException {
			if(!isActive()) throw new InactiveException();
			if(amount < 0) throw new IllegalArgumentException();
			if((balance - amount) < 0) throw new OverdrawException();
			balance -= amount;
		}

	}

}