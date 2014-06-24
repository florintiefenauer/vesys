package bank.requests;

import java.io.IOException;
import java.util.Set;

import bank.IBank;

public class GetAccountNumbersRequest extends Request {

	private Set<String> accountNumbers;

	public Set<String> getAccNumbers() {
		return accountNumbers;
	}

	@Override
	public void handleRequest(IBank b) {

		try {

			accountNumbers = b.getAccountNumbers();

		} catch (IOException e) {

			throw new RuntimeException(e);

		}

	}

}
