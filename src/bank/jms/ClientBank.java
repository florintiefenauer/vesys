package bank.jms;

import java.io.IOException;
import java.util.Set;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TemporaryQueue;

import bank.IAccount;
import bank.IBank;
import bank.InactiveException;
import bank.OverdrawException;
import bank.jms.request.CreateAccountRequest;
import bank.jms.request.IsActiveRequest;
import bank.jms.request.Request;

public class ClientBank implements IBank{
	
	private Queue queue;
	private JMSContext context;
	private TemporaryQueue tempQueue;
	private JMSProducer sender;
	private JMSConsumer receiver;
	
	public ClientBank(JMSContext context, Queue queue){
		this.context = context;
		this.queue = queue;
		
		tempQueue = context.createTemporaryQueue();
		sender = context.createProducer().setJMSReplyTo(tempQueue);
		receiver = context.createConsumer(tempQueue);	
	}

	@Override
	public String createAccount(String owner) throws IOException {		
		sender.send(queue, new CreateAccountRequest(owner));
		CreateAccountRequest r = receiver.receiveBody(CreateAccountRequest.class);
		return r.getAccNr();
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
	
	public class ClientAccount implements IAccount{
		
		private String number;
		private String owner;
		private JMSProducer sender;
		private JMSConsumer receiver;
		private Queue queue;
		
		public ClientAccount(String number, String owner, JMSProducer sender, JMSConsumer receiver, Queue queue){
			this.number =number;
			this.owner = owner;
			this.sender = sender;
			this.receiver = receiver;
			this.queue = queue;
			
		}

		@Override
		public String getNumber() throws IOException {
			return number;
		}

		@Override
		public String getOwner() throws IOException {
			return owner;
		}

		@Override
		public boolean isActive() throws IOException {
			sender.send(queue, new IsActiveRequest(number));
			IsActiveRequest r = receiver.receiveBody(IsActiveRequest.class);
			Exception exc = r.getException();
			if(exc != null) exc.printStackTrace();
			return r.isActive();
		}

		@Override
		public void deposit(double amount) throws IOException,
				IllegalArgumentException, InactiveException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void withdraw(double amount) throws IOException,
				IllegalArgumentException, OverdrawException, InactiveException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public double getBalance() throws IOException {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}

}
