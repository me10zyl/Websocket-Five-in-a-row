package drawNGuess;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

public class DrawNGuessServlet extends WebSocketServlet {
	private ArrayList<MyStreamInbound> arr = new ArrayList<DrawNGuessServlet.MyStreamInbound>();
	private AtomicInteger connectionIds = new AtomicInteger(0);
	private Game game = new Game();

	public class MyStreamInbound extends StreamInbound {
		public User user;

		public MyStreamInbound(User user) {
			// TODO Auto-generated constructor stub
			this.user = user;
		}

		@Override
		protected void onOpen(WsOutbound outbound) {
			// TODO Auto-generated method stub
			arr.add(this);
			game.getUsers().add(user);
			sendUserInfo();
			broadcastGame();
		}

		@Override
		protected void onClose(int status) {
			// TODO Auto-generated method stub
			arr.remove(this);
			game.getUsers().remove(user);
			if (game.getUsers().size() < 2) {
				initGame();
			}
			broadcastGame();
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
			JSONObject jsonObj = JSONObject.fromObject(sb.toString());
			if (jsonObj.containsKey("ready")) {
				String ready = jsonObj.getString("ready");
				if (ready.equals("true")) {
					user.setReady(true);
					if (isAllUserReady()) {
						Random ran = new Random();
						game.setGameStatus("start");
						game.randomGameTitle();
						game.getUsers().get(ran.nextInt(game.getUsers().size())).setTurn(true);
						broadcastGame();
					}
				} else {
					user.setReady(false);
				}
			} else {
				if (jsonObj.containsKey("msg") && game.getGameStatus().equals("start")) {
					String msg = jsonObj.getString("msg");
					String id = null;
					if (jsonObj.containsKey("id"))
						id = jsonObj.getString("id");
					if (msg.equals(game.getGameTitle())) {
						boolean isSelf = false;
						if (user.getId().equals(id)) {
							if (user.isTurn()) {
								isSelf = true;
							}
						}
						if (!isSelf) {
							user.setWin(true);
							game.setGameStatus("end");
							broadcastGame();
							initGame();
						} else {
							return;
						}
					}
				}
				broadcast(sb.toString());
			}
		}

		private void initGame() {
			game.setGameStatus("end");
			for (User u : game.getUsers()) {
				u.setReady(false);
				u.setTurn(false);
				u.setWin(false);
			}
		}

		private boolean isAllUserUnReady() {
			boolean isAllUnReady = true;
			for (int i = 0; i < arr.size(); i++) {
				if (arr.get(i).user.isReady()) {
					isAllUnReady = false;
				}
			}
			return isAllUnReady;
		}

		private boolean isAllUserReady() {
			boolean isAllReady = true;
			for (int i = 0; i < arr.size(); i++) {
				if (!arr.get(i).user.isReady()) {
					isAllReady = false;
				}
			}
			return isAllReady;
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
			String nicknames = "{\"nicknames\":[";
			for (MyStreamInbound inbound : arr) {
				nicknames += "\"" + inbound.user.getNickname() + "\"" + ",";
			}
			broadcast(nicknames.replaceAll(",$", "]}"));
		}

		private void broadcastGame() {
			JSONObject gameJson = JSONObject.fromObject(game);
			broadcast(gameJson.toString());
		}

		private void sendUserInfo() {
			// TODO Auto-generated method stub
			JSONObject json_me = JSONObject.fromObject(user);
			try {
				getWsOutbound().writeTextMessage(CharBuffer.wrap(json_me.toString()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public class TrashInbound extends StreamInbound {

		@Override
		protected void onBinaryData(InputStream is) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		protected void onTextData(Reader r) throws IOException {
			// TODO Auto-generated method stub
			JSONObject errorJSON = new JSONObject();
			errorJSON.put("error", "请不要直接访问本页面,输入你的用户名再来...");
			getWsOutbound().writeTextMessage(CharBuffer.wrap(errorJSON.toString()));
			getWsOutbound().flush();
		}

	}

	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("name");
		if (name != null) {
			// for(MyStreamInbound my : arr)
			// {
			// if(my.user.getId().equals(session.getId()))
			// {
			// return new TrashInbound();
			// }
			// }
			return new MyStreamInbound(new User(connectionIds.getAndIncrement() + "", name, 0, false, false, false));
		}
		return new TrashInbound();
	}

}