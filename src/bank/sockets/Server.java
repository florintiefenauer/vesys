package bank.sockets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import bank.InactiveException;
import bank.OverdrawException;

public class Server {

	private static Bank bank = new Bank();

	public static void main(String args[]) throws IOException {
		int port = 12345;
		try (ServerSocket server = new ServerSocket(port)) {
			System.out.println("Startet Bank Server on port " + port);
			while (true) {
				Socket s = server.accept();
				Thread t = new Thread(new ServerHandler(s, bank));
				t.start();
			}
		}
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
				DataInputStream in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
				ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
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
							out.writeObject(acc.getNumber());
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
						out.writeObject(bank.getAccount(in.readUTF())
								.getBalance());
						break;
					case "getOwner":
						System.out.println("getOwner");
						out.writeObject(bank.getAccount(in.readUTF())
								.getOwner());
						break;
					case "isActive":
						System.out.println("isActive");
						out.writeObject(bank.getAccount(in.readUTF())
								.isActive());
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
			if (acc.balance == 0 && acc.isActive()) {
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
