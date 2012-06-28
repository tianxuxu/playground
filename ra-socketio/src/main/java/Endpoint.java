import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.glines.socketio.common.ConnectionState;
import com.glines.socketio.common.DisconnectReason;
import com.glines.socketio.server.SocketIOInbound;
import com.glines.socketio.server.SocketIOOutbound;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
class Endpoint implements SocketIOInbound {

	private final ChatServlet servlet;

	private final String user;

	private final HttpServletRequest request;

	private SocketIOOutbound outbound;

	Endpoint(final ChatServlet servlet, final String user, final HttpServletRequest request) {
		this.request = request;
		this.servlet = servlet;
		this.user = user;
	}

	@Override
	public void onConnect(final SocketIOOutbound outbound) {
		this.outbound = outbound;
		servlet.add(this);
		servlet.broadcast(user + " connected");
	}

	@Override
	public void onDisconnect(final DisconnectReason reason, final String errorMessage) {
		outbound = null;
		request.getSession().removeAttribute("user");
		servlet.remove(this);
		servlet.broadcast(user + " disconnected");
	}

	@Override
	public void onMessage(final int messageType, final String message) {
		if ("/disconnect".equals(message)) {
			outbound.close();
		} else {
			servlet.broadcast("[" + user + "] " + message);
		}
	}

	void send(final String data) {
		try {
			if (outbound != null && outbound.getConnectionState() == ConnectionState.CONNECTED) {
				outbound.sendMessage(data);
			}
		} catch (IOException e) {
			outbound.close();
		}
	}

}
