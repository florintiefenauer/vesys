package bank.jms.request;

import java.io.IOException;

import bank.IBank;

public class GetAccountRequest extends Request{

	private String owner;
	private String number;
	
	public GetAccountRequest(String number){
		this.number = number;
	}
	
	public String getOwner(){
		return owner;
	}
	
	@Override
	public void handleRequest(IBank b) {
		try {
			owner = b.getAccount(number).getOwner();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

}
