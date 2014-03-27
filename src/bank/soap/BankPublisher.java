package bank.soap;

import javax.xml.ws.Endpoint;

public class BankPublisher {
	public static void main(String[] args) {
		Endpoint.publish("http://127.0.0.1:9875/hs", // publication URI
				new Bank()); // SIB instance
		System.out.println("service published");
	}
}