package bank.requests;

import java.io.IOException;

import bank.IAccount;
import bank.IBank;

public class GetBalanceRequest extends Request {
	
	private String number;
	private double balance;
	
	public GetBalanceRequest(String number){
		this.number = number;
	}
	
	public double getBalance(){
		return this.balance;
	}
	
	@Override
	public void handleRequest(IBank b) {
		try {
			IAccount a = b.getAccount(this.number);
			if (a != null){
					this.balance = a.getBalance();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
