package bank.websockets;

import java.nio.ByteBuffer;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import bank.requests.Request;

public class RequestEncoder implements Encoder.Binary<Request> {

	@Override
	public void destroy() {}

	@Override
	public void init(EndpointConfig arg0) {}

	@Override
	public ByteBuffer encode(Request obj) throws EncodeException {
		// TODO Auto-generated method stub
		return null;
	}

}
