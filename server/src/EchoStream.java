import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

public class EchoStream extends WebSocketServlet {

	class MyStreamInbound extends StreamInbound {
		
		@Override
		protected void onBinaryData(InputStream is) throws IOException {
			// TODO Auto-generated method stub
			WsOutbound wsOutbound = getWsOutbound();
			int ch = is.read();
			while (ch != -1) {
				wsOutbound.writeBinaryData(ch);
				ch = is.read();
			}
			wsOutbound.flush();
		}

		@Override
		protected void onTextData(Reader r) throws IOException {
			// TODO Auto-generated method stub
			WsOutbound wsOutbound = getWsOutbound();
			int ch = r.read();
			while (ch != -1) {
				wsOutbound.writeTextData((char) ch);
				ch = r.read();
			}
			wsOutbound.flush();
		}

	}

	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return new MyStreamInbound();
	}

}
