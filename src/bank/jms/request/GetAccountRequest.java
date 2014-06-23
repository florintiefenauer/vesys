package bank.jms.request;

import java.io.IOException;

import bank.IAccount;
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
			IAccount a = b.getAccount(this.number);
			if(a != null){
				this.owner = a.getOwner();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

}
