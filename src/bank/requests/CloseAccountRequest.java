package bank.requests;

import java.io.IOException;

import bank.IBank;

public class CloseAccountRequest extends Request {

	private boolean isClosed;
	private String number;
	
	public CloseAccountRequest(String number){
		this.number = number;
	}
	
	public boolean isClosed(){
		return isClosed;
	}
	
	
	@Override
	public void handleRequest(IBank b) {
		try {
			isClosed = b.closeAccount(number);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
