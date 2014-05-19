package bank.rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

	public static void main(String args[]) throws Exception {
		
		try {
		LocateRegistry.createRegistry(1099);
		}
		catch (RemoteException e) {
		System.out.println(">> registry could not be exported");
		System.out.println(">> probably another registry already runs on 1099");
		}
		IBankRMI b = new BankRMI();
		Naming.rebind("rmi://localhost:1099/BankService", b);
		System.out.println("Bank server started...");
		}
	
}
