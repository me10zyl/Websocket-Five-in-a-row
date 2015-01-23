package fiveInARow.message;

import net.sf.json.JSONObject;

public class Msg {

	public static JSONObject chat_Message = JSONObject.fromObject("{type: " + DataType.chatMessage + ",content: null}");

	// 游戏状态消息
	public static JSONObject gameStatus_Message = JSONObject.fromObject("{type: " + DataType.gameStatusMessage + ",gameStatus: 'init'}");

	public static JSONObject ready_Message = JSONObject.fromObject("{type: " + DataType.readyMessage + ",isOtherReady : false}");

	// 令牌消息
	public static JSONObject token_Message = JSONObject.fromObject("{type: " + DataType.tokenMessage + ",hasToken: false}");

	// 下棋消息
	public static JSONObject drawChess_Message = JSONObject.fromObject("{type: " + DataType.drawChessMessage + ",drawOtherChess : []}");

	// 选择消息
	public static JSONObject drawHover_Message = JSONObject.fromObject("{type: " + DataType.drawHoverMessage + ",drawHover : []}");

	// 最后棋子消息
	public static JSONObject drawLast_Message = JSONObject.fromObject("{type: " + DataType.drawLastMessage + ",drawLast : []}");

	// 初始化游戏消息
	public static JSONObject initGame_Message = JSONObject.fromObject("{type: " + DataType.initGameMessage + "}");

	// 初始化游戏消息
	public static JSONObject chessColor_Message = JSONObject.fromObject("{type: " + DataType.chessColorMessage + ",color : 'white'}");

	// 胜利消息
	public static JSONObject win_Message = JSONObject.fromObject("{type: " + DataType.winMessage + "}");

	// 进入房间消息
	public static JSONObject enter_Message = JSONObject.fromObject("{type: " + DataType.enterMessage + "}");

	public static JSONObject alert_Message = JSONObject.fromObject("{type: " + DataType.alertMessage + ",content : null}");

	public static void main(String[] args) {
		System.out.println(Msg.chat_Message.toString());
	}
}
