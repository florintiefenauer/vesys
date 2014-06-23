package bank.rmi;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

import bank.BankDriver2;
import bank.IBank;

public class Driver implements BankDriver2 {

	private String server = "localhost";
	private IBankRMI bank;

	@Override
	public void connect(String[] args) throws IOException {
		if (args.length > 0) { 
			server = args[0];
		}
		try {
			bank = (IBankRMI)Naming.lookup("rmi://" + server + "/BankService");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void disconnect() throws IOException {
		bank = null;
		
	}

	@Override
	public IBank getBank() {
		return bank;
	}

	@Override
	public void registerUpdateHandler(UpdateHandler handler) throws IOException {
		bank.registerUpdateHandler(new RMIUpdateHandler(handler));
	}

}
