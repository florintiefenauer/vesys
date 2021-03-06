package bank.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Set;

import bank.InactiveException;
import bank.OverdrawException;

public class Driver implements bank.BankDriver {

	private Bank bank = null;
	private String server = "localhost";
	private int port = 8080;

	@Override
	public void connect(String[] args) throws UnknownHostException, IOException {
		
		if (args.length > 1) { 
			server = args[0];
			port = Integer.parseInt(args[1]);
		}
		bank = new Bank(this);
	}

	@Override
	public void disconnect() throws IOException {
		bank = null;
	}

	@Override
	public Bank getBank() {
		return bank;
	}
	
	public ObjectInputStream sendGet(String params) throws IOException{
		
		System.out.println(params);
		String sUrl = "http://"+server+":"+port+"/bankHTTP/bank?"+params;
		String sUrlReplaced = sUrl.replace(" ", "%20");
		System.out.println(sUrlReplaced);
		URL url = new URL(sUrlReplaced);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		InputStream is = connection.getInputStream();
		
		int size = connection.getContentLength();
		byte[] byteArray = new byte[size];
		int read = 0;
		
		while (read < size){
			int n = is.read(byteArray, read, size - read);
			if (n == -1) break; //EOF
			read += n;
		}
		
		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteArray);
		
		return new ObjectInputStream(byteIn);
	}

	static class Bank implements bank.IBank {

		Driver driver;
		private Bank(Driver driver){
			this.driver = driver;
			
		}

		@Override
		public Set<String> getAccountNumbers() throws IOException {
			Object inObj = null;
			try {
				inObj = driver.sendGet("action=getAccountNumbers").readObject();
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
			Object inObj = null;
			try {
				inObj = driver.sendGet("action=createAccount"+"&owner="+owner).readObject();
			} catch (ClassNotFoundException e) {
				System.out.println("Fatal Error");
				throw new RuntimeException();
			}
			if (inObj instanceof String) {
				return (String) inObj;
			}else throw new IllegalArgumentException();
		}

		@Override
		public boolean closeAccount(String number) throws IOException {
			Object inObj = null;
			try {
				inObj = driver.sendGet("action=closeAccount"+"&number="+number).readObject();
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
			Object inObj = null;
			try {
				inObj = driver.sendGet("action=getAccount"+"&number="+number).readObject();
			} catch (ClassNotFoundException e) {
				System.out.println("Fatal Error");
				throw new RuntimeException();
			}
			if (inObj instanceof String) {
				return new Account(number,(String) inObj, driver);
			}
			return null;
		}

		@Override
		public void transfer(bank.IAccount from, bank.IAccount to, double amount)
				throws IOException, InactiveException, OverdrawException {
			Object inObj = null;
			try {
				inObj = driver.sendGet("action=transfer"+"&from="+from.getNumber()+"&to="+to.getNumber()+"&amount="+amount).readObject();
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
		private Driver driver;

		private Account(String number, String owner, Driver driver) {
			this.number = number;
			this.owner = owner;
			this.driver = driver;
		}

		@Override
		public double getBalance() throws IOException {

			Object inObj = null;
			try {
				inObj = driver.sendGet("action=getBalance"+"&number="+number).readObject();
			} catch (ClassNotFoundException e) {
				System.out.println("Fatal Error");
				throw new RuntimeException();
			}
			if (inObj instanceof Double) {
				return (double) inObj;
			} else if (inObj instanceof IllegalArgumentException){
				throw (IllegalArgumentException) inObj;
			}
			return 0.0;
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

			Object inObj = null;
			try {
				inObj = driver.sendGet("action=isActive"+"&number="+number).readObject();
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

			Object inObj = null;
			try {
				inObj = driver.sendGet("action=deposit"+"&number="+number+"&amount="+amount).readObject();
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

			Object inObj = null;
			try {
				inObj = driver.sendGet("action=withdraw"+"&number="+number+"&amount="+amount).readObject();
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
