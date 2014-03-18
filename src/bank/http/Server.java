package bank.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bank.InactiveException;
import bank.OverdrawException;

@WebServlet("/bank")
public class Server extends HttpServlet {

	ServerHelper helper;

	private static final long serialVersionUID = -9132774871593752541L;	

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		if(helper == null) helper = new ServerHelper();
	
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		
		Enumeration<?> e = request.getParameterNames();

		String method = (String) e.nextElement();
		String[] params = new String[3];
		int i = 0;
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			params[i] = request.getParameter(name);
			i++;
		}

		out.writeObject(helper.getResponse(method, params));
		
		byte[] buf = byteOut.toByteArray();
		response.setContentType("application/octet-stream");
		response.setContentLength(buf.length);
		
		ServletOutputStream sOut = response.getOutputStream();
		sOut.write(buf);
		sOut.close();
		
	}
	
	static class ServerHelper{
		
		private Bank bank = new Bank();

		private Object getResponse(String method, String[] params) throws IOException {
			
			switch (method) {
			case "getAccountNumbers":
				System.out.println("getAccountNumbers");
				return bank.getAccountNumbers();
			case "createAccount":
				System.out.println("createAccount");
				return bank.createAccount(params[0]);
			case "closeAccount":
				System.out.println("closeAccount");
				return bank.closeAccount(params[0]);
			case "getAccount":
				System.out.println("getAccount");
				Account acc = (Account) bank.getAccount(params[0]);
				if (acc != null) {
					return acc.getOwner();
				} else {
					return null;
				}
			case "transfer":
				System.out.println("transfer");
				try {
					bank.transfer(bank.getAccount(params[0]),
							bank.getAccount(params[1]),
							Double.parseDouble(params[2]));
					return null;
				} catch (Exception e) {
					return e;
				}
			case "getBalance":
				System.out.println("getBalance");
				try {
					double balance = bank.getAccount(params[0]).getBalance();
					return balance;
				} catch (NullPointerException e) {
					return e;
				}
			case "isActive":
				System.out.println("isActive");
				try {
					boolean isActive = bank.getAccount(params[0]).isActive();
					return isActive;
				} catch (NullPointerException e) {
					return e;
				}
			case "deposit":
				System.out.println("deposit");
				try {
					bank.getAccount(params[0]).deposit(
							Double.parseDouble(params[1]));
					return null;
				} catch (Exception e) {
					return e;
				}
			case "withdraw":
				System.out.println("withdraw");
				try {
					bank.getAccount(params[0]).withdraw(
							Double.parseDouble(params[1]));
					return null;
				} catch (Exception e) {
					return e;
				}
			default:
				System.out.println("False input");
				throw new RuntimeException();

			}
		}
	}
	
	static class Bank implements bank.Bank {

		private final Map<String, Account> accounts = new HashMap<>();

		@Override
		public Set<String> getAccountNumbers() {
			Set<String> accNumbers = new HashSet<String>();
			for (Account acc : accounts.values()) {
				if (acc.isActive())
					accNumbers.add(acc.getNumber());
			}
			return accNumbers;
		}

		@Override
		public String createAccount(String owner) {
			if (owner != null) {

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
			if (acc != null && acc.balance == 0 && acc.isActive()) {
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
				throws IOException, InactiveException, OverdrawException,
				IllegalArgumentException {
			from.withdraw(amount);
			to.deposit(amount);
		}

	}

	static class Account implements bank.Account {

		private final String number;
		private final String owner;
		private double balance;
		private boolean active = true;
		private static int count = 0;

		Account(String owner) {
			this.owner = owner;
			count++;
			this.number = owner.substring(0, 1) + "-" + count;
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
			if (!isActive()) {
				throw new InactiveException();
			}
			if (amount < 0)
				throw new IllegalArgumentException();
			balance += amount;
		}

		@Override
		public void withdraw(double amount) throws InactiveException,
				OverdrawException {
			if (!isActive())
				throw new InactiveException();
			if (amount < 0)
				throw new IllegalArgumentException();
			if ((balance - amount) < 0)
				throw new OverdrawException();
			balance -= amount;
		}

	}

}
