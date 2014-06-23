package bank.jms.request;

import java.io.Serializable;

import bank.IBank;

public abstract class Request implements Serializable {
	private Exception e;
	public void setException(Exception e) { this.e = e; }
	public Exception getException() { return e; }
	public abstract void handleRequest(IBank b);
}
