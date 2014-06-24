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
import bank.requests.CloseAccountRequest;
import bank.requests.CreateAccountRequest;
import bank.requests.DepositRequest;
import bank.requests.GetAccountNumbersRequest;
import bank.requests.GetAccountRequest;
import bank.requests.GetBalanceRequest;
import bank.requests.IsActiveRequest;
import bank.requests.Request;
import bank.requests.TransferRequest;
import bank.requests.WithdrawRequest;

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
		sender.send(queue, new CloseAccountRequest(number));
		CloseAccountRequest r = receiver.receiveBody(CloseAccountRequest.class);
		return r.isClosed();
	}

	@Override
	public Set<String> getAccountNumbers() throws IOException {
		sender.send(queue, new GetAccountNumbersRequest());
		GetAccountNumbersRequest r = receiver.receiveBody(GetAccountNumbersRequest.class);
		return r.getAccNumbers();
	}

	@Override
	public IAccount getAccount(String number) throws IOException {
		sender.send(queue, new GetAccountRequest(number));
		GetAccountRequest r = receiver.receiveBody(GetAccountRequest.class);
		String owner = r.getOwner();
		if (owner == null){
			return null;
		} else{
			return new ClientAccount(number,r.getOwner(),sender,receiver, queue);
		}	
	}

	@Override
	public void transfer(IAccount a, IAccount b, double amount)
			throws IOException, IllegalArgumentException, OverdrawException,
			InactiveException {
		sender.send(queue, new TransferRequest(a.getNumber(), b.getNumber(),amount));
		TransferRequest r = receiver.receiveBody(TransferRequest.class);
		Exception exc = r.getException();
		
		if(exc != null){
			if(exc instanceof IllegalArgumentException ) {
				throw (IllegalArgumentException)exc;
			} else if(exc instanceof InactiveException){
				throw (InactiveException) exc;
			} else if(exc instanceof OverdrawException){
				throw (OverdrawException) exc;
			}
		}
		
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
			
			sender.send(queue, new DepositRequest(number, amount));
			DepositRequest r = receiver.receiveBody(DepositRequest.class);
			Exception exc = r.getException();
			
			if(exc != null){
				if(exc instanceof IllegalArgumentException ) {
					throw (IllegalArgumentException)exc;
				} else if(exc instanceof InactiveException){
					throw (InactiveException) exc;
				} 
			}			
		}

		@Override
		public void withdraw(double amount) throws IOException,
				IllegalArgumentException, OverdrawException, InactiveException {
			
			sender.send(queue, new WithdrawRequest(number, amount));
			WithdrawRequest r = receiver.receiveBody(WithdrawRequest.class);
			Exception exc = r.getException();
			
			if(exc != null){
				if(exc instanceof IllegalArgumentException ) {
					throw (IllegalArgumentException)exc;
				} else if(exc instanceof InactiveException){
					throw (InactiveException) exc;
				} else if(exc instanceof OverdrawException){
					throw (OverdrawException) exc;
				}
			}
		}

		@Override
		public double getBalance() throws IOException {
			sender.send(queue, new GetBalanceRequest(number));
			GetBalanceRequest r = receiver.receiveBody(GetBalanceRequest.class);
			return r.getBalance();
		}
		
	}

}
