/**
 * Created by Administrator on 2014/12/16.
 */
var url = "ws://zyl-me.xicp.net:1234/WebSocket/five";
var isReady = false;
var isOtherReady = false;
var gameStatus = "init";
var hasToken = false;
var ws = null;
var drawOtherChess = [];
var drawChess = [];//棋子标志位
var drawHover = [];//选择标志位
var drawLast = [];//最后棋子标志位
var chessColor = "white";
function initDrawOtherChess() {
    for (var i = 0; i < 15; i++) {
        for (var j = 0; j < 15; j++) {
            drawOtherChess[i][j] = 0;
        }
    }
}

function initDrawHover() {
    for (var i = 0; i < 15; i++) {
        for (var j = 0; j < 15; j++) {
            drawHover[i][j] = 0;
        }
    }
}
function initDrawChess() {
    for (var i = 0; i < 15; i++) {
        for (var j = 0; j < 15; j++) {
            drawChess[i][j] = 0;
        }
    }
}

function initDrawLast() {
    for (var i = 0; i < 15; i++) {
        for (var j = 0; j < 15; j++) {
            drawLast[i][j] = 0;
        }
    }
}

function initGame() {
    $("#other").addClass("invisible");
    gameStatus = "init"
    chessColor = "white";
    initChessboard();
    initReady();
}

function initReady() {
    isReady = false;
    isOtherReady = false;
    $("#zb1").slideUp();
    $("#zb2").slideUp();
    onReadyStateChanged();
}

function initChessboard() {
    initDrawChess();
    initDrawHover();
    initDrawLast();
    initDrawOtherChess();
}

//行转点 points --- 点转行 getRowColByXY
function initLayout() {
    ctx_bg.drawImage(images.chessboard, 0, 0);
    $('#btnStart').hover(function () {
        if (!isReady)
            $('#btnStart')[0].style.background = "url(images/startBright.png) no-repeat";
    }, function () {
        if (!isReady)
            $('#btnStart')[0].style.background = "url(images/start.png) no-repeat";
    })
    $('#btnStart').click(clickStart)
    $('#fg').mousemove(function (e) {
        if (gameStatus == "start" && hasToken) {
            initDrawHover();
            var x = e.pageX - $('#fg').offset().left;
            var y = e.pageY - $('#fg').offset().top;
            var colRow = Colrow.getColRowByXY(x, y);
            drawHover[colRow.row][colRow.col] = 1;
        }
    })
    $('#fg').click(function (e) {
            if (gameStatus == "start" && hasToken) {
                var x = e.pageX - $('#fg').offset().left;
                var y = e.pageY - $('#fg').offset().top;
                var colRow = Colrow.getColRowByXY(x, y);
                if (drawChess[colRow.row][colRow.col] != 1 && drawOtherChess[colRow.row][colRow.col] != 1) { //下棋
                    initDrawLast();
                    drawChess[colRow.row][colRow.col] = 1;
                    drawLast[colRow.row][colRow.col] = 1;
                    dectectWin(colRow.row, colRow.col);
                    drawChess_Message.drawOtherChess = drawChess;
                    drawLast_Message.drawLast = drawLast;
                    ws.send(s(drawChess_Message));
                    ws.send(s(drawLast_Message));
                }
            }
        }
    )
    $("#btnSend").click(function (e) {
        sendChatMessage();
    })
    $("#msg").keypress(function (e) {
        if (e.which == 13) {
            sendChatMessage();
        }
    })
}
function sendChatMessage() {
    chat_Message.content = $("#msg").val();
    $("#msg").val("");
    ws.send(s(chat_Message));
}
function clickStart() {
    if (gameStatus != "start") {
        changeReadyState();
    }
}

function changeReadyState() {
    isReady = !isReady;
    ready_Message.isOtherReady = isReady;
    ws.send(s(ready_Message));
    onReadyStateChanged();
}

function changeOtherReadyState(bool) {
    isOtherReady = bool;
    onReadyStateChanged();
}

function onReadyStateChanged() {
    if (isReady) {
        $('#btnStart')[0].style.background = "url(images/startGray.png) no-repeat";
        $("#zb1").slideDown();
    }
    else {
        $('#btnStart')[0].style.background = "url(images/start.png) no-repeat";
        $("#zb1").slideUp();
    }
    if (isOtherReady) {
        $("#zb2").slideDown();
    } else {
        $("#zb2").slideUp();
    }
}
function init() {
    $("#other").html("<div><img src='images/06.gif'></div><div><span>分数:555</span><img src='images/ready.png' style='float: right' id='zb2'><div style='margin-top: 15px;font-size: 12px;font-family: '幼圆';font-weight: bold;'>幼儿园</div></div>");
    $("#myready").html("<img src='images/ready.png' style='float: right' id='zb1'>")
    for (var i = 0; i < 15; i++) {
        drawHover.push([]);
        for (var j = 0; j < 15; j++) {
            drawHover[i][j] = 0;
        }
    }
    for (var i = 0; i < 15; i++) {
        drawChess.push([]);
        for (var j = 0; j < 15; j++) {
            drawChess[i][j] = 0;
        }
    }
    for (var i = 0; i < 15; i++) {
        drawLast.push([]);
        for (var j = 0; j < 15; j++) {
            drawLast[i][j] = 0;
        }
    }
    for (var i = 0; i < 15; i++) {
        drawOtherChess.push([]);
        for (var j = 0; j < 15; j++) {
            drawOtherChess[i][j] = 0;
        }
    }
    initGame();
    initLayout();
    connectWS();
    main();
}

function connectWS() {
    if (window.WebSocket) {
        //连接WebSocket服务器
        ws = new WebSocket(url);
        ws.onopen = function () {
        };
        ws.onclose = function () {
            alert('连接关闭...');
        };
        ws.onmessage = function (e) {
            var message = e.data;
            console.log(message);
            var json = JSON.parse(message);
            onMessage(json);
            //drawLine(json.oldX, json.oldY, json.x, json.y);//解析错误是因为...服务器代码写错..无语
        };
        ws.onerror = function () {
        }
    }
}

function drawWin() {
    ctx_fg.drawImage(images.youwin, (scr_width - images.youwin.width) / 2, (scr_height - images.youwin.height) / 2);
}

function drawLose() {
    ctx_fg.drawImage(images.youlose, (scr_width - images.youlose.width) / 2, (scr_height - images.youlose.height) / 2);
}

function isWin(count, i, j) {
    if (i < 15 && j < 15) {
        if (drawChess[i][j] == 1) {
            count++;
            count = isWin(count, i + 1, j);
        }
        return count;
    }
    return count;
}

function isWin2(count, i, j) {
    if (i >= 0 && i < 15 && j < 15) {
        if (drawChess[i][j] == 1) {
            count++;
            count = isWin2(count, i - 1, j);
        }
        return count;
    }
    return count;
}

function isWin3(count, i, j) {
    if (i < 15 && j < 15 && j >= 0) {
        if (drawChess[i][j] == 1) {
            count++;
            count = isWin3(count, i, j - 1);
        }
        return count;
    }
    return count;
}


function isWin4(count, i, j) {
    if (i < 15 && j < 15) {
        if (drawChess[i][j] == 1) {
            count++;
            count = isWin4(count, i, j + 1);
        }
        return count;
    }
    return count;
}


function isWin5(count, i, j) {
    if (i >=0 && j >= 0 &&i < 15 && j < 15) {
        if (drawChess[i][j] == 1) {
            count++;
            count = isWin5(count, i - 1, j - 1);
        }
        return count;
    }
    return count;
}

function isWin6(count, i, j) {
    if (i < 15 && j < 15) {
        if (drawChess[i][j] == 1) {
            count++;
            count = isWin6(count, i + 1, j + 1);
        }
        return count;
    }
    return count;
}

function isWin7(count, i, j) {
    if (i < 15 && j < 15 && j >= 0) {
        if (drawChess[i][j] == 1) {
            count++;
            count = isWin7(count, i + 1, j - 1);
        }
        return count;
    }
    return count;
}

function isWin8(count, i, j) {
    if (i >= 0 && i < 15 && j < 15) {
        if (drawChess[i][j] == 1) {
            count++;
            count = isWin8(count, i - 1, j + 1);
        }
        return count;
    }
    return count;
}


function dectectWin(i, j) {
    var count = 0;
    var count2 = 0;
    var count3 = 0;
    var count4 = 0;
    var count5 = 0;
    var count6 = 0;
    var count7 = 0;
    var count8 = 0;
    count = isWin(count, i, j);
    count2 = isWin2(count2, i, j);
    count3 = isWin3(count3, i, j);
    count4 = isWin4(count4, i, j);
    count5 = isWin5(count5, i, j);
    count6 = isWin6(count6, i, j);
    count7 = isWin7(count7, i, j);
    count7 = isWin8(count7, i, j);
    if ((count + count2 - 1) >= 5) {
        gameStatus = "win";
    } else if ((count3 + count4 - 1) >= 5) {
        gameStatus = "win";
    } else if ((count5 + count6 - 1) >= 5) {
        gameStatus = "win";
    } else if ((count7 + count8 - 1) >= 5) {
        gameStatus = "win";
    }
    if (gameStatus == "win") {
        ws.send(s(win_Message));
        initReady();
    }
}

function s(json) {
    return JSON.stringify(json);
}