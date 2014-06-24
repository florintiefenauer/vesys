package bank.websockets;

import java.util.LinkedList;
import java.util.List;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.glassfish.tyrus.server.Server;

import bank.Bank;
import bank.BankDriver2;
import bank.jms.ServerBank;
import bank.requests.Request;


@ServerEndpoint(value = "/bank",
		decoders = RequestDecoder.class,
		encoders = RequestEncoder.class)
public class WebsocketsServer {

	private static ServerBank bank;
	private static List<Session> sessions = new LinkedList<>();
	
	public static void main(String[] args) throws Exception {
		
		
		Server server = new Server("localhost", 2222, "/websockets", WebsocketsServer.class);
		server.start();
		System.out.println("Server started...");
		
		
		BankDriver2.UpdateHandler handler = new BankDriver2.UpdateHandler() {
			@Override
			public void accountChanged(String id) {
				for(Session s: sessions){
					try {s.getBasicRemote().sendText(id);}
					catch(Exception e){
						System.out.println(e);
						sessions.remove(s);
					}
				}
			}
		};
			
		bank = new ServerBank(new Bank(), handler);
		
		System.out.println("Server started, key to stop the server"); 
		System.in.read();
	}
	
	@OnOpen
	public void onOpen( Session session ) {
		sessions.add(session);
	}

	@OnClose
	public void onClose( Session session, CloseReason closeReason ) {
		sessions.remove(session);
	}

	@OnMessage
	public Request onMessage(Request r, Session session) {
		r.handleRequest(bank);
		return r;	
	}

	@OnError
	public void onError( Throwable exception, Session session) {
		System.out.println( "an error occured on connection " + session.getId() + ":" + exception );
	}

}
