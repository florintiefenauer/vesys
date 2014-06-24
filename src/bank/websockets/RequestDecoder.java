package bank.websockets;

import java.nio.ByteBuffer;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import bank.requests.Request;

public class RequestDecoder implements Decoder.Binary<Request> {

	@Override
	public void destroy() {}

	@Override
	public void init(EndpointConfig bytes) {}

	@Override
	public Request decode(ByteBuffer bytes) throws DecodeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean willDecode(ByteBuffer bytes) {
		// TODO Auto-generated method stub
		return false;
	}

}
