import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

public class ChatStream extends WebSocketServlet {
	private ArrayList<MyStreamInbound> arr = new ArrayList<ChatStream.MyStreamInbound>();
	private AtomicInteger connectionIds = new AtomicInteger(0);

	public class MyStreamInbound extends StreamInbound {
		private String nickname;

		public MyStreamInbound(int id) {
			// TODO Auto-generated constructor stub
			nickname = "Guest " + id;
		}

		@Override
		protected void onOpen(WsOutbound outbound) {
			// TODO Auto-generated method stub
			arr.add(this);
			broadcastNicknames();
			broadcast(nickname + " 进来了...");
		}

		@Override
		protected void onClose(int status) {
			// TODO Auto-generated method stub
			arr.remove(this);
			broadcastNicknames();
			broadcast(nickname + " 走了...");
		}

		@Override
		protected void onBinaryData(InputStream is) throws IOException {
			// TODO Auto-generated method stub

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
			broadcast(nickname + " : " + sb.toString());
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

		private void broadcastNicknames() {
			String nicknames = "[";
			for (MyStreamInbound inbound : arr) {
				nicknames += inbound.nickname + ",";
			}
			broadcast(nicknames.replaceAll(",$", "]"));
		}

	}

	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return new MyStreamInbound(connectionIds.incrementAndGet());
	}

}
