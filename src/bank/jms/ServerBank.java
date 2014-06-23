package bank.jms;

import java.io.IOException;
import java.util.Set;

import bank.Bank;
import bank.BankDriver2;
import bank.IAccount;
import bank.IBank;
import bank.InactiveException;
import bank.OverdrawException;

public class ServerBank implements IBank{

	IBank bank;
	BankDriver2.UpdateHandler handler;
	
	public ServerBank(IBank bank, BankDriver2.UpdateHandler handler){
		this.bank = bank;
		this.handler = handler;
		
	}
	
	@Override
	public String createAccount(String owner) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean closeAccount(String number) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<String> getAccountNumbers() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAccount getAccount(String number) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void transfer(IAccount a, IAccount b, double amount)
			throws IOException, IllegalArgumentException, OverdrawException,
			InactiveException {
		// TODO Auto-generated method stub
		
	}

}
