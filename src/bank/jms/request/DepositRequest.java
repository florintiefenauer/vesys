package bank.jms.request;

import java.io.IOException;

import bank.IAccount;
import bank.IBank;

public class DepositRequest extends Request {
	private String number;
	private double amount;
	
	
	public DepositRequest (String number, double amount){
		this.number = number;
		this.amount = amount;
	}
	
	@Override
	public void handleRequest(IBank b) {
		try {
			IAccount a = b.getAccount(this.number);
			if (a != null){
				try {
					a.deposit(this.amount);
				} catch (Exception e) {
					this.setException(e);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
