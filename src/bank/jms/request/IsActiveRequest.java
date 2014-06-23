package bank.jms.request;

import java.io.IOException;

import bank.IAccount;
import bank.IBank;

public class IsActiveRequest extends Request {

	private String number;
	private boolean active;
	
	public IsActiveRequest (String number) {
		this.number = number;
	}
	
	public boolean isActive() {
		return this.active;
	}
	
	@Override
	public void handleRequest(IBank b) {
		try {
			IAccount a = b.getAccount(this.number);
			if(a != null){
				this.active = a.isActive();
			} else {
				this.setException(new IllegalArgumentException("account number is invalid"));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
