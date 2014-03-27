package bank.soap;

import bank.soap.client.jaxws.Account;
import bank.soap.client.jaxws.AccountService;
import bank.soap.client.jaxws.IOException_Exception;
import bank.soap.client.jaxws.InactiveException_Exception;

public class Client {

	public static void main(String[] args) throws IOException_Exception, InactiveException_Exception {
		AccountService service = new AccountService();
		Account port = service.getAccountPort();
		
		String result = port.sayHello("Mugunta Fikmidiv");
		System.out.println(result);
		
		port.deposit(100.0);

	}

}
