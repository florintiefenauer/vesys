package bank.jms;

import java.io.IOException;

import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;

import bank.Bank;
import bank.BankDriver2;
import bank.IBank;
import bank.jms.request.Request;

public class Server {

	static final int deliveryMode = DeliveryMode.NON_PERSISTENT;

	public static void main(String[] args) throws Exception {
		final Context jndiContext = new InitialContext();
		final ConnectionFactory factory = (ConnectionFactory) jndiContext
				.lookup("ConnectionFactory");
		final Queue queue = (Queue) jndiContext.lookup("BANK");
		final Topic topic = (Topic) jndiContext.lookup("BANK.LISTENER");

		try (JMSContext context = factory.createContext()) {
			JMSConsumer consumer = context.createConsumer(queue);
			final JMSProducer producer = context.createProducer()
					.setDeliveryMode(deliveryMode);
			
			final JMSProducer topicProducer = context.createProducer()
					.setDeliveryMode(deliveryMode).setTimeToLive(10_000);

			BankDriver2.UpdateHandler handler = new BankDriver2.UpdateHandler() {
				@Override
				public void accountChanged(String id) throws IOException {
					topicProducer.send(topic, id);
				}
			};
				
			IBank bank = new ServerBank(new Bank(), handler);
			
			System.out.println("JMS server is running...");
			while (true) {
			ObjectMessage msg = (ObjectMessage) consumer.receive();			
			Request r = msg.getBody(Request.class);
			r.handleRequest(bank);
			producer.send(msg.getJMSReplyTo(), r);
			}
		}
	
	}
}
