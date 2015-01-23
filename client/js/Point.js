/**
 * Created by Administrator on 2014/12/17.
 */
function Point(x,y)
{
    this.x = x;
    this.y = y;
}
var points = [];
for (var i = 0; i < 15; i++) {
    points.push([]);
    for (var j = 0; j < 15; j++) {
        points[i][j] = new Point(38 * i + 9, 38 * j + 9);
//            ctx_fg.strokeRect(points[i][j].x, points[i][j].y, 1, 1);
    }
}