package bank.soap.server;

import javax.xml.ws.Endpoint;

import bank.Bank;


public class BankPublisher {
	public static void main(String[] args) {
		Endpoint.publish("http://127.0.0.1:9875/hs", // publication URI
				new BankServiceImpl(new Bank())); // SIB instance
		System.out.println("service published");
	}
}