package bank.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import bank.InactiveException;
import bank.OverdrawException;

@WebServlet("/*")
public class Server extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9132774871593752541L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println(">> " + getClass().getName() + " " + new Date());
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.println("<html><body><pre>");
		Enumeration<?> e;

		out.println("\nParameters:");
		e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			out.println(name + " = " + request.getParameter(name));
		}

		out.println("</pre></body></html>");
		System.out.println("<< " + getClass().getName());
	}

	static class ServerHandler implements Runnable {
		private Socket s;
		private Bank bank;

		private ServerHandler(Socket s, Bank bank) {
			this.s = s;
			this.bank = bank;
		}

		public void run() {
			System.out.println("connection from " + s);
			try {
				DataInputStream in = new DataInputStream(s.getInputStream());
				ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
				while (!s.isClosed()) {
					String method = in.readUTF();

					switch (method) {
					case "getAccountNumbers":
						System.out.println("getAccountNumbers");
						out.writeObject(bank.getAccountNumbers());
						break;
					case "createAccount":
						System.out.println("createAccount");
						out.writeObject(bank.createAccount(in.readUTF()));
						break;
					case "closeAccount":
						System.out.println("closeAccount");
						out.writeObject(bank.closeAccount(in.readUTF()));
						break;
					case "getAccount":
						System.out.println("getAccount");
						Account acc = (Account) bank.getAccount(in.readUTF());
						if (acc != null) { 
							out.writeObject(acc.getOwner());
						} else {
							out.writeObject(null);
						}
						break;
					case "transfer":
						System.out.println("transfer");
						try {
							bank.transfer(bank.getAccount(in.readUTF()),
									bank.getAccount(in.readUTF()),
									in.readDouble());
							out.writeObject(null);
						} catch (Exception e) {
							out.writeObject(e);
						}
						break;
					case "getBalance":
						System.out.println("getBalance");
						try{
							double balance = bank.getAccount(in.readUTF()).getBalance();
							out.writeObject(balance);
						}catch (NullPointerException e){
							out.writeObject(e);
						}
						
						break;
					case "isActive":
						System.out.println("isActive");
						try{
							boolean isActive = bank.getAccount(in.readUTF()).isActive();
							out.writeObject(isActive);
						}catch (NullPointerException e){
							out.writeObject(e);
						}

						break;
					case "deposit":
						System.out.println("deposit");
						try {
							bank.getAccount(in.readUTF()).deposit(
									in.readDouble());
							out.writeObject(null);
						} catch (Exception e) {
							out.writeObject(e);
						}
						break;
					case "withdraw":
						System.out.println("withdraw");
						try {
							bank.getAccount(in.readUTF()).withdraw(
									in.readDouble());
							out.writeObject(null);
						} catch (Exception e) {
							out.writeObject(e);
						}
						break;
					default:
						System.out.println("False input");
						throw new Exception();

					}

					out.flush();
				}
			} catch (Exception e) {
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
		public bank.Account getAccount(String number){
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
