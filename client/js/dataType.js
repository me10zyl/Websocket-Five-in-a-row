/**
 * Created by Administrator on 2014/11/20.
 */
//定义消息枚举
var dataType = {
    chatMessage: 0,//聊天消息
    gameStatusMessage: 1,//游戏状态消息
    tokenMessage: 2,//令牌消息
    readyMessage: 3,//游戏准备好消息
    drawChessMessage: 4,//下棋消息
    drawHoverMessage: 5,//选择消息
    drawLastMessage: 6,//最后棋子消息
    initGameMessage: 7,//初始化游戏消息
    chessColorMessage: 8,//颜色消息
    winMessage: 9,//胜利消息
    enterMessage: 10,//进入房间消息
    alertMessage: 11//警告消息
};

//聊天消息的格式
var chat_Message = {
    type: dataType.chatMessage,  //数据类型
    content: null              //聊天内容
}
//游戏状态消息
var gameStatus_Message = {
    type: dataType.gameStatusMessage,
    gameStatus: "init"
}

//游戏准备消息
var ready_Message = {
    type: dataType.readyMessage,
    isOtherReady: false
}

//令牌消息
var token_Message = {
    type: dataType.tokenMessage,//消息类型
    hasToken: false //是否拥有令牌
}

//下棋消息
var drawChess_Message = {
    type: dataType.drawChessMessage,
    drawOtherChess: []
}

//选择消息
var drawHover_Message = {
    type: dataType.drawHoverMessage,
    drawHover: []
}

//最后棋子消息
var drawLast_Message = {
    type: dataType.drawLastMessage,
    drawLast: []
}

//初始化游戏消息
var initGame_Message = {
    type: dataType.initGameMessage
}

//颜色消息
var chessColor_Message = {
    type: dataType.chessColorMessage,
    color: "white"
}

//胜利消息
var win_Message = {
    type: dataType.winMessage
}

//进入房间消息
var enter_Message = {
    type: dataType.enterMessage
}

//警告消息
var alert_Message = {
    type: dataType.alertMessage,
    content : null
}