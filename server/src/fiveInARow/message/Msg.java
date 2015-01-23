package fiveInARow.message;

import net.sf.json.JSONObject;

public class Msg {

	public static JSONObject chat_Message = JSONObject.fromObject("{type: " + DataType.chatMessage + ",content: null}");

	// ��Ϸ״̬��Ϣ
	public static JSONObject gameStatus_Message = JSONObject.fromObject("{type: " + DataType.gameStatusMessage + ",gameStatus: 'init'}");

	public static JSONObject ready_Message = JSONObject.fromObject("{type: " + DataType.readyMessage + ",isOtherReady : false}");

	// ������Ϣ
	public static JSONObject token_Message = JSONObject.fromObject("{type: " + DataType.tokenMessage + ",hasToken: false}");

	// ������Ϣ
	public static JSONObject drawChess_Message = JSONObject.fromObject("{type: " + DataType.drawChessMessage + ",drawOtherChess : []}");

	// ѡ����Ϣ
	public static JSONObject drawHover_Message = JSONObject.fromObject("{type: " + DataType.drawHoverMessage + ",drawHover : []}");

	// ���������Ϣ
	public static JSONObject drawLast_Message = JSONObject.fromObject("{type: " + DataType.drawLastMessage + ",drawLast : []}");

	// ��ʼ����Ϸ��Ϣ
	public static JSONObject initGame_Message = JSONObject.fromObject("{type: " + DataType.initGameMessage + "}");

	// ��ʼ����Ϸ��Ϣ
	public static JSONObject chessColor_Message = JSONObject.fromObject("{type: " + DataType.chessColorMessage + ",color : 'white'}");

	// ʤ����Ϣ
	public static JSONObject win_Message = JSONObject.fromObject("{type: " + DataType.winMessage + "}");

	// ���뷿����Ϣ
	public static JSONObject enter_Message = JSONObject.fromObject("{type: " + DataType.enterMessage + "}");

	public static JSONObject alert_Message = JSONObject.fromObject("{type: " + DataType.alertMessage + ",content : null}");

	public static void main(String[] args) {
		System.out.println(Msg.chat_Message.toString());
	}
}
