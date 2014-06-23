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

public class ClientBank implements IBank{
	
	private Queue queue;
	private JMSContext context;
	private TemporaryQueue tempQueue;
	private JMSProducer sender;
	private JMSConsumer receiver;
	
	public ClientBank(JMSContext sender, Queue queue){
		this.context = context;
		this.queue = queue;
		
		tempQueue = context.createTemporaryQueue();
		sender = (JMSContext) context.createProducer().setJMSReplyTo(tempQueue);
		receiver = context.createConsumer(tempQueue);	
	}

	@Override
	public String createAccount(String owner) throws IOException {		
		String accNr = null;
		sender.send(queue, "createAccount");
		sender.send(queue, owner);
		Message msg = receiver.receive();
		try {
			if(msg.getBooleanProperty("isException")){
				throw msg.getBody(Exception.class);
			} else{
				accNr = msg.getBody(String.class);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accNr;
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

}