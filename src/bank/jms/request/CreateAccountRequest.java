package bank.jms.request;

import java.io.IOException;

import bank.IBank;

public class CreateAccountRequest extends Request {

	private String owner;
	private String AccNr = null;
	
	public CreateAccountRequest(String owner){
		this.owner = owner;
	}
	public String getAccNr(){
		return AccNr;
	}
	
	@Override
	public void handleRequest(IBank b) {
		try {
			AccNr = b.createAccount(owner);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
