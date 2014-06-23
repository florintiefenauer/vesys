package bank.jms;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import bank.BankDriver2;
import bank.IBank;

public class Driver implements BankDriver2 {

	private List<UpdateHandler> listeners = new LinkedList<UpdateHandler>();

	@Override
	public void connect(String[] args) throws IOException {
		try {
			Context jndiContext = new InitialContext();

			ConnectionFactory factory = (ConnectionFactory) jndiContext
					.lookup("ConnectionFactory");
			Queue queue = (Queue) jndiContext.lookup("BANK");

			
			Topic topic = (Topic) jndiContext.lookup("BANK.LISTENER");

			JMSContext context = factory.createContext();

			JMSConsumer consumer = context.createConsumer(topic);
			consumer.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message msg) {
					try {
						String id = msg.getBody(String.class);
						for (UpdateHandler h : listeners) {
							try {
								h.accountChanged(id);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					} catch (JMSException e1) {
						e1.printStackTrace();
					}

				}

			});

		} catch (NamingException e) {
			e.printStackTrace();
		}
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
		listeners.add(handler);
	}

}
