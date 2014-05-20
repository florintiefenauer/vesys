package bank.rmi;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import bank.BankDriver2;
import bank.rmi.IBankRMI.IRMIUpdateHandler;

public class RMIUpdateHandler extends UnicastRemoteObject implements IRMIUpdateHandler{

	BankDriver2.UpdateHandler handler;
	public RMIUpdateHandler(BankDriver2.UpdateHandler handler) throws RemoteException{
		this.handler = handler;
	}
	
	@Override
	public void accountChanged(String id) throws IOException {
		handler.accountChanged(id);		
	}

}
