package bank.jms.request;

import java.io.IOException;

import bank.IAccount;
import bank.IBank;

public class GetBalanceRequest extends Request {
	
	private String number;
	private double balance;
	
	public GetBalanceRequest(String number, double balance){
		this.number = number;
		this.balance = balance;
	}
	
	public double getBalance(){
		return this.balance;
	}
	
	@Override
	public void handleRequest(IBank b) {
		try {
			IAccount a = b.getAccount(this.number);
			if (a != null){
				try {
					this.balance = a.getBalance();
				} catch (Exception e) {
					this.setException(e);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
