package bank.sockets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Set;

import bank.InactiveException;
import bank.OverdrawException;

public class Driver implements bank.BankDriver {

	private Bank bank = null;
	private String server = "localhost";
	private int port = 12345;

	@Override
	public void connect(String[] args) throws UnknownHostException, IOException {
		
		if (args.length > 1) { 
			server = args[0];
			port = Integer.parseInt(args[1]);
		}
		bank = new Bank();
		bank.socket = new Socket(server, port, null, 0);
		bank.out =new DataOutputStream(bank.socket.getOutputStream());
		bank.in = new ObjectInputStream(bank.socket.getInputStream());
	}

	@Override
	public void disconnect() throws IOException {
		bank.socket.close();
		bank.socket = null;
		bank = null;
	}

	@Override
	public Bank getBank() {
		return bank;
	}

	static class Bank implements bank.IBank {

		Socket socket = null;
		DataOutputStream out = null;
		ObjectInputStream in = null;

		@Override
		public Set<String> getAccountNumbers() throws IOException {
			out.writeUTF("getAccountNumbers");
			Object inObj = null;
			try {
				inObj = in.readObject();
			} catch (ClassNotFoundException e) {
				System.out.println("Fatal Error");
				throw new RuntimeException();
			}
			if (inObj instanceof Set<?>) {
				return (Set<String>) inObj;
			}
			return null;
		}

		@Override
		public String createAccount(String owner) throws IOException {
			out.writeUTF("createAccount");
			out.writeUTF(owner);

			Object inObj = null;
			try {
				inObj = in.readObject();
			} catch (ClassNotFoundException e) {
				System.out.println("Fatal Error");
				throw new RuntimeException();
			}
			if (inObj instanceof String) {
				return (String) inObj;
			}
			return null;
		}

		@Override
		public boolean closeAccount(String number) throws IOException {
			out.writeUTF("closeAccount");
			out.writeUTF(number);
			Object inObj = null;
			try {
				inObj = in.readObject();
			} catch (ClassNotFoundException e) {
				System.out.println("Fatal Error");
				throw new RuntimeException();
			}
			if (inObj instanceof Boolean) {
				return (Boolean) inObj;
			}
			return false;
		}

		@Override
		public bank.IAccount getAccount(String number) throws IOException {
			out.writeUTF("getAccount");
			out.writeUTF(number);
			Object inObj = null;
			try {
				inObj = in.readObject();
			} catch (ClassNotFoundException e) {
				System.out.println("Fatal Error");
				throw new RuntimeException();
			}
			if (inObj instanceof String) {
				return new Account(number,(String) inObj, out, in);
			}
			return null;
		}

		@Override
		public void transfer(bank.IAccount from, bank.IAccount to, double amount)
				throws IOException, InactiveException, OverdrawException {
			out.writeUTF("transfer");
			out.writeUTF(from.getNumber());
			out.writeUTF(to.getNumber());
			out.writeDouble(amount);
			Object inObj = null;
			try {
				inObj = in.readObject();
			} catch (ClassNotFoundException e) {
				System.out.println("Fatal Error");
				throw new RuntimeException();
			}
			if (inObj instanceof InactiveException) {
				throw (InactiveException) inObj;
			} else if (inObj instanceof OverdrawException) {
				throw (OverdrawException) inObj;
			} else if (inObj instanceof IllegalArgumentException) {
				throw (IllegalArgumentException) inObj;
			}

		}

	}

	static class Account implements bank.IAccount {

		private String number;
		private String owner;
		private DataOutputStream out;
		private ObjectInputStream in;

		private Account(String number, String owner, DataOutputStream out, ObjectInputStream in) {
			this.number = number;
			this.owner = owner;
			this.out = out;
			this.in = in;
		}

		@Override
		public double getBalance() throws IOException {
			out.writeUTF("getBalance");
			out.writeUTF(number);

			Object inObj = null;
			try {
				inObj = in.readObject();
			} catch (ClassNotFoundException e) {
				System.out.println("Fatal Error");
				throw new RuntimeException();
			}
			if (inObj instanceof Double) {
				return (double) inObj;
			} else if (inObj instanceof NullPointerException){
				throw (NullPointerException) inObj;
			}
			return 0.0;
		}

		@Override
		public String getOwner() throws IOException {
			return owner;
		}

		@Override
		public String getNumber() {
			return number;
		}

		@Override
		public boolean isActive() throws IOException {
			out.writeUTF("isActive");
			out.writeUTF(number);

			Object inObj = null;
			try {
				inObj = in.readObject();
			} catch (ClassNotFoundException e) {
				System.out.println("Fatal Error");
				throw new RuntimeException();
			}
			if (inObj instanceof Boolean) {
				return (boolean) inObj;
			} else if (inObj instanceof NullPointerException){
				throw (NullPointerException) inObj;
			}
			return false;
		}

		@Override
		public void deposit(double amount) throws InactiveException,
				IOException {
			out.writeUTF("deposit");
			out.writeUTF(number);
			out.writeDouble(amount);

			Object inObj = null;
			try {
				inObj = in.readObject();
			} catch (ClassNotFoundException e) {
				System.out.println("Fatal Error");
				throw new RuntimeException();
			}
			if (inObj instanceof InactiveException) {
				throw (InactiveException) inObj;
			} else if (inObj instanceof IllegalArgumentException) {
				throw (IllegalArgumentException) inObj;
			} else if (inObj instanceof NullPointerException) {
				throw (NullPointerException) inObj;
				
			}
		}

		@Override
		public void withdraw(double amount) throws InactiveException,
				OverdrawException, IOException {
			out.writeUTF("withdraw");
			out.writeUTF(number);
			out.writeDouble(amount);

			Object inObj = null;
			try {
				inObj = in.readObject();
			} catch (ClassNotFoundException e) {
				System.out.println("Fatal Error");
				throw new RuntimeException();
			}
			if (inObj instanceof InactiveException) {
				throw (InactiveException) inObj;
			} else if (inObj instanceof OverdrawException) {
				throw (OverdrawException) inObj;
			} else if (inObj instanceof IllegalArgumentException) {
				throw (IllegalArgumentException) inObj;
			} else if (inObj instanceof NullPointerException){
				throw (NullPointerException) inObj;
			}
		}

	}

}
