package bank.sockets;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


import bank.Bank;
import bank.Bank.Account;

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

}
