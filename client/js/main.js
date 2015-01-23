/**
 * Created by Administrator on 2014/12/16.
 */
function main() {
    //connectWS();
    var fps = 60;
    setInterval(loop, 1000 / fps);
}

function loop() {
    ctx_fg.clearRect(0, 0, scr_width, scr_height);
    //画select
    for (var i = 0; i < 15; i++) {
        for (var j = 0; j < 15; j++) {
            if (drawHover[i][j] == 1) {
                new Chess(i, j).drawHover();
            }
        }
    }
    //画自家棋
    for (var i = 0; i < 15; i++) {
        for (var j = 0; j < 15; j++) {
            if (drawChess[i][j] == 1) {
                new Chess(i, j).draw(chessColor);
            }
        }
    }
    //画别人家棋
    for (var i = 0; i < 15; i++) {
        for (var j = 0; j < 15; j++) {
            if (drawOtherChess[i][j] == 1) {
                if (chessColor == "white")
                    new Chess(i, j).draw("black");
                else if (chessColor == "black")
                    new Chess(i, j).draw("white");
            }
        }
    }
    //画Last
    for (var i = 0; i < 15; i++) {
        for (var j = 0; j < 15; j++) {
            if (drawLast[i][j] == 1) {
                new Chess(i, j).drawLast();
            }
        }
    }
    //画win
    if (gameStatus == "win") {
        drawWin();
    }
    //画lose
    else if (gameStatus == "lose") {
        drawLose();
    }
}

function onMessage(json) {
    switch (json.type) {
        case dataType.chatMessage:
            $("#chatBox").html($("#chatBox").html() + json.content + "<br>");
            break;
        case dataType.gameStatusMessage:
            gameStatus = json.gameStatus;
            if (gameStatus == "disconnect") {
                alert('对方跑了...');
                initGame();
            }
            if (gameStatus == "start") {
                initChessboard();
            }
            break;
        case dataType.readyMessage:
            changeOtherReadyState(json.isOtherReady);
            break;
        case dataType.tokenMessage:
            hasToken = json.hasToken;
            break;
        case dataType.drawChessMessage:
            drawOtherChess = json.drawOtherChess;
            break;
        case dataType.drawHoverMessage:
            drawHover = json.drawHover;
            break;
        case dataType.drawLastMessage:
            drawLast = json.drawLast;
            break;
        case dataType.initGameMessage:
            gameStatus = "init";
            initGame();
            break;
        case dataType.chessColorMessage:
            chessColor = json.color;
            break;
        case dataType.winMessage:
            gameStatus = "lose";
            initReady();
            break;
        case dataType.enterMessage:
            $("#other").removeClass("invisible");
            break;
        case dataType.alertMessage:
            alert(json.content);
            $("#chatBox").html($("#chatBox").html() + "<font color=red>" + json.content + "</font><br>");
            break;
        default :
            break;
    }
}