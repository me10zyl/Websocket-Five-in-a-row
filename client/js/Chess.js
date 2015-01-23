/**
 * Created by Administrator on 2014/12/16.
 */
function Chess(row, col) {
    this.row = row;
    this.col = col;
}

Chess.prototype.draw = function (color) {
    //ctx_fg.drawImage(images.whitechess, this.col * 38 - 9, this.row * 38 - 7);
    if (color== "white")
        drawCore(images.whitechess, this.row, this.col, 18, 18);
    else if (color == "black")
        drawCore(images.blackchess, this.row, this.col, 17, 17);
}
Chess.prototype.drawHover = function () {
//    var __ret = getColRowByXY(x, y);
//    var row = __ret.row;
//    var col = __ret.col;
    drawCore(images.select, this.row, this.col, 17, 18);
}

Chess.prototype.drawLast = function () {
    //ctx_fg.drawImage(images.whitechess, this.col * 38 - 9, this.row * 38 - 7);
    drawCore(images.underaapawn, this.row, this.col, 18, 18);
}


function drawCore(img, row, col, errorX, errorY) {
    ctx_fg.drawImage(img, points[row][col].x - errorX, points[row][col].y - errorY);
}

