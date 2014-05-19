package bank.rmi;

import java.io.IOException;
import java.rmi.Remote;

import bank.BankDriver2;
import bank.IBank;

public interface IBankRMI extends java.rmi.Remote, IBank{
	
	void registerUpdateHandler(IRMIUpdateHandler handler) throws IOException;
	void notifyUpdateHandler(String id) throws IOException;
	interface IRMIUpdateHandler extends BankDriver2.UpdateHandler, Remote {}

}
