package bank.rmi;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Set;

import bank.Bank;
import bank.IAccount;
import bank.InactiveException;
import bank.OverdrawException;

public class BankRMI extends java.rmi.server.UnicastRemoteObject implements IBankRMI{

	private Bank bank;
	private LinkedList<IRMIUpdateHandler> handlerList= new LinkedList<IRMIUpdateHandler>();
	
	public BankRMI() throws java.rmi.RemoteException {
		super();
		bank = new Bank();
	}
	
	@Override
	public String createAccount(String owner) throws IOException {		
		String number = bank.createAccount(owner);
		notifyUpdateHandler(number);
		return number;
	}

	@Override
	public boolean closeAccount(String number) throws IOException {
		boolean result = bank.closeAccount(number);
		if(result) notifyUpdateHandler(number);
		return result;
	}

	@Override
	public Set<String> getAccountNumbers() throws IOException {
		return bank.getAccountNumbers();
	}

	@Override
	public IAccountRMI getAccount(String number) throws IOException {
		IAccount account = bank.getAccount(number);
		if (account != null) {
			return new AccountRMI(account, this);
		}
		return null;
	}

	@Override
	public void transfer(IAccount a, IAccount b, double amount)
			throws IOException, IllegalArgumentException, OverdrawException,
			InactiveException {
		bank.transfer(a, b, amount);
		notifyUpdateHandler(a.getNumber());
		notifyUpdateHandler(b.getNumber());
	
	}
	
	public void registerUpdateHandler(IRMIUpdateHandler handler){
		handlerList.add(handler);		
	}
	
	public void notifyUpdateHandler(String id) throws IOException{
		for(IRMIUpdateHandler handler : handlerList){
			handler.accountChanged(id);
		}
	}

}
