package bank.jms.request;

import java.io.IOException;
import java.util.Set;

import bank.IBank;

public class getAccountNumbersRequest extends Request{

	private Set<String> accountNumbers;
	
	public Set<String> getAccNumbers(){
		return accountNumbers;
	}
	
	
	@Override
	public void handleRequest(IBank b) {
		try {
			accountNumbers = b.getAccountNumbers();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}