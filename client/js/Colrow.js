/**
 * Created by Administrator on 2014/12/17.
 */
function Colrow(row,col)
{
    this.row = row;
    this.col = col;
}
Colrow.getColRowByXY = function(x, y) {
    var row = 0;
    var col = 0;
    for (var i = 0; i < 15; i++) {
        for (var j = 0; j < 15; j++) {
            if (Math.abs(x - points[i][j].x) <= 38 / 2) {
                if (Math.abs(y - points[i][j].y) <= 38 / 2) {
                    col = j;
                    row = i;
                    break;
                }
            }
        }
    }
    return new Colrow(row,col);
}