package bank.jms.request;

import java.io.IOException;

import bank.IAccount;
import bank.IBank;

public class TransferRequest extends Request {
	private String numberAcc1;
	private String numberAcc2;
	private double amount;
	
	public TransferRequest (String numberAcc1, String numberAcc2, double amount) {
		this.numberAcc1 = numberAcc1;
		this.numberAcc2 = numberAcc2;
		this.amount = amount;
	}
	
	@Override
	public void handleRequest(IBank b) {
		try {
			IAccount acc1 = b.getAccount(this.numberAcc1);
			IAccount acc2 = b.getAccount(this.numberAcc2);
			if(acc1 != null && acc2 != null){
				try {
					b.transfer(acc1, acc2, this.amount);
				} catch (Exception e) {
					this.setException(e);
				}
			} else {
				this.setException(new IllegalArgumentException("account number is invalid"));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
