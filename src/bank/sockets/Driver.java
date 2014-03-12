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
		bank.out =new DataOutputStream(new BufferedOutputStream(bank.socket.getOutputStream()));
		bank.in = new ObjectInputStream(new BufferedInputStream(bank.socket.getInputStream()));
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

	static class Bank implements bank.Bank {

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
			}
			if (inObj instanceof Boolean) {
				return (Boolean) inObj;
			}
			return false;
		}

		@Override
		public bank.Account getAccount(String number) throws IOException {
			out.writeUTF("getAccount");
			out.writeUTF(number);
			Object inObj = null;
			try {
				inObj = in.readObject();
			} catch (ClassNotFoundException e) {
				System.out.println("Fatal Error");
			}
			if (inObj instanceof String) {
				return new Account((String) inObj, out, in);
			}
			return null;
		}

		@Override
		public void transfer(bank.Account from, bank.Account to, double amount)
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

	static class Account implements bank.Account {
		/**
		 * 
		 */
		private static final long serialVersionUID = 7766880046540449827L;
		private String number;
		private DataOutputStream out;
		private ObjectInputStream in;

		private Account(String number, DataOutputStream out, ObjectInputStream in) {
			this.number = number;
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
			}
			if (inObj instanceof Double) {
				return (double) inObj;
			}
			return 0.0;
		}

		@Override
		public String getOwner() throws IOException {
			out.writeUTF("getOwner");
			out.writeUTF(number);

			Object inObj = null;
			try {
				inObj = in.readObject();
			} catch (ClassNotFoundException e) {
				System.out.println("Fatal Error");
			}
			if (inObj instanceof String) {
				return (String) inObj;
			}
			return null;
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
			}
			if (inObj instanceof Boolean) {
				return (boolean) inObj;
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
			}
			if (inObj instanceof InactiveException) {
				throw (InactiveException) inObj;
			} else if (inObj instanceof IllegalArgumentException) {
				throw (IllegalArgumentException) inObj;
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

}