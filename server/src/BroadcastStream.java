import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

public class BroadcastStream extends WebSocketServlet {
	private ArrayList<MyStreamInbound> arr = new ArrayList<MyStreamInbound>();

	public class MyStreamInbound extends StreamInbound {
		@Override
		protected void onOpen(WsOutbound outbound) {
			// TODO Auto-generated method stub
			super.onOpen(outbound);
			arr.add(this);
		}

		@Override
		protected void onClose(int status) {
			// TODO Auto-generated method stub
			super.onClose(status);
			arr.remove(this);
		}

		@Override
		protected void onBinaryData(InputStream is) throws IOException {
			// TODO Auto-generated method stub
			byte[] buffer = new byte[1024];
			int len = is.read(buffer);
			StringBuffer sb = new StringBuffer();
			while (len != -1) {
				sb.append(new String(buffer, 0, len, Charset.defaultCharset()));
				len = is.read(buffer);
			}
			broadcast(sb.toString());
		}

		@Override
		protected void onTextData(Reader r) throws IOException {
			// TODO Auto-generated method stub
			char[] buffer = new char[1024];
			int len = r.read(buffer);
			StringBuffer sb = new StringBuffer();
			while (len != -1) {
				sb.append(new String(buffer, 0, len));
				len = r.read(buffer);
			}
			broadcast(sb.toString());
			System.out.println(sb.toString());
			// broadcast("{\"oldX\": \"216\", \"oldY\": \"168\", \"x\": \"219\", \"y\": \"172\"}");
		}

		private void broadcast(String message) {
			try {
				for (MyStreamInbound inbound : arr) {
					inbound.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return new MyStreamInbound();
	}

}
