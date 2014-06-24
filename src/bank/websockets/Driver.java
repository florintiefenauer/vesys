package bank.websockets;

import java.io.IOException;

import javax.websocket.ClientEndpoint;

import bank.BankDriver2;
import bank.IBank;

@ClientEndpoint
public class Driver implements BankDriver2 {

	@Override
	public void connect(String[] args) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnect() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public IBank getBank() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerUpdateHandler(UpdateHandler handler) throws IOException {
		// TODO Auto-generated method stub

	}

}
