package fiveInARow;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

import fiveInARow.message.DataType;
import fiveInARow.message.Msg;

public class FliveInARowServlet extends WebSocketServlet {
	private ArrayList<MyStreamInbound> arr = new ArrayList<FliveInARowServlet.MyStreamInbound>();
	private AtomicInteger connectionIds = new AtomicInteger(0);
	private String gameStatus = "init";
	private String readyMsg = "";
	private int readyCount = 0;
	private String chessColor = "white";
	private int gameCount = 0;
	private int hasTokenIndex = 0;

	public class MyStreamInbound extends StreamInbound {
		private int id;

		public MyStreamInbound() {
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onOpen(WsOutbound outbound) {
			// TODO Auto-generated method stub
			try {
				if (arr.size() < 2) {
					arr.add(this);
				} else {
					Msg.alert_Message.put("content", "对不起,房间已经爆满了...请稍后再试");
					outbound.writeTextMessage(CharBuffer.wrap(Msg.alert_Message.toString()));
					outbound.close(0, null);
				}
				// 缓存
				outbound.writeTextMessage(CharBuffer.wrap(readyMsg));
				if (arr.size() == 2)
					broadcast(Msg.enter_Message.toString());
				else
					broadcastExceptMe(Msg.enter_Message.toString());
				// outbound.writeTextMessage(CharBuffer.wrap(Msg.gameStatus_Message.toString()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected void onClose(int status) {
			// TODO Auto-generated method stub
			if (arr.contains(this)) {
				arr.remove(this);
				Msg.ready_Message.put("isOtherReady", false);
				readyMsg = Msg.ready_Message.toString();
				broadcast(Msg.initGame_Message.toString());
				readyCount = 0;
			}
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
			System.out.println(sb.toString());
			JSONObject json = JSONObject.fromObject(sb.toString());
			switch (json.getInt("type")) {
			case DataType.readyMessage:
				readyMsg = sb.toString();
				broadcastExceptMe(readyMsg);
				if (json.getString("isOtherReady").equals("true")) {
					readyCount++;
				} else {
					readyCount--;
				}
				if (readyCount == 2) {
					gameStart();
				}
				return;
			case DataType.drawChessMessage:
				Msg.drawChess_Message.put("drawOtherChess", json.getString("drawOtherChess"));
				broadcastExceptMe(Msg.drawChess_Message.toString());
				sendHasToken();
				return;
			case DataType.winMessage:
				sendChatMessage("<font color='orange'>胜负已分...</font>");
				broadcastExceptMe(Msg.win_Message.toString());
				readyCount = 0;
				return;
			default:
				break;
			}
			broadcast(sb.toString());
		}

		private void sendHasToken() {
			boolean hasToken = (hasTokenIndex & 1) == 0 ? true : false;
			Msg.token_Message.put("hasToken", hasToken);
			sendMessage(Msg.token_Message.toString(), 0);
			Msg.token_Message.put("hasToken", !hasToken);
			sendMessage(Msg.token_Message.toString(), 1);
			hasTokenIndex = ++hasTokenIndex % 2;
		}

		private void gameStart() {
			Msg.chessColor_Message.put("color", (gameCount & 1) == 1 ? chessColor = "white" : (chessColor = "black"));
			sendMessage(Msg.chessColor_Message.toString(), 0);
			if (chessColor.equals("white"))
				chessColor = "black";
			else
				chessColor = "white";
			Msg.chessColor_Message.put("color", chessColor);
			sendMessage(Msg.chessColor_Message.toString(), 1);
			Msg.gameStatus_Message.put("gameStatus", "start");
			broadcast(Msg.gameStatus_Message.toString());
			sendHasToken();
			sendChatMessage("<font color='orange'>新的一局开始了...</font>");
			gameCount++;
		}

		private void sendChatMessage(String str) {
			Msg.chat_Message.put("content", str);
			broadcast(Msg.chat_Message.toString());
		}

		private void sendMessage(String message, int index) {
			try {
				MyStreamInbound inbound = arr.get(index);
				inbound.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private void broadcastExceptMe(String message) {
			try {
				for (int i = 0; i < arr.size(); i++) {
					MyStreamInbound inbound = arr.get(i);
					if (i != arr.indexOf(this))
						inbound.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
