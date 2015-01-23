/**
 * Created by Administrator on 2014/12/1.
 */
/*star
 *loading模块
 *实现图片的预加载，并显示进度条
 *参数：图片数组对象，加载完成的回调函数
 */

var bg = $('#bg')[0];
var fg = $('#fg')[0];
var ctx_bg = bg.getContext('2d');
var ctx_fg = fg.getContext('2d');
var images = {};
var scr_width = bg.width;
var scr_height = bg.height;
var bar_len = 400;

function stokeProgress(clearWidth, clearHeight, step, totalStep) {
    ctx_bg.clearRect(0, 0, clearWidth, clearHeight);
    ctx_bg.fillText('Loading:' + step + '/' + totalStep, (scr_width - bar_len) / 2, (scr_height - ctx_bg.lineWidth) / 2 - 20);// 200 280
    ctx_bg.save();
    ctx_bg.strokeStyle = '#555';
    ctx_bg.beginPath();
    ctx_bg.moveTo((scr_width - bar_len) / 2, (scr_height - ctx_bg.lineWidth) / 2); //200 300
    ctx_bg.lineTo((scr_width - bar_len) / 2 + bar_len, (scr_height - ctx_bg.lineWidth) / 2);// 600 300
    ctx_bg.stroke();
    ctx_bg.beginPath();
    ctx_bg.restore();
    ctx_bg.moveTo((scr_width - bar_len) / 2, (scr_height - ctx_bg.lineWidth) / 2);//200 300
    ctx_bg.lineTo(step / totalStep * 400 + (scr_width - bar_len) / 2, (scr_height - ctx_bg.lineWidth) / 2);//
    ctx_bg.stroke();
}

function loadImages(sources, callback) {
    var loadedImages = 0;
    var numImages = 0;
    ctx_bg.font = '14px bold';
    ctx_bg.lineWidth = 5;
    var clearWidth = bg.width;
    var clearHeight = bg.height;
    // get num of sources
    for (var src in sources) {
        numImages++;
    }
    for (var src in sources) {
        images[src] = new Image();
        //当一张图片加载完成时执行
        images[src].onload = function () {
            $('#pretxt').remove();
            //重绘一个进度条
            stokeProgress(clearWidth, clearHeight, loadedImages, numImages);
            //当所有图片加载完成时，执行回调函数callback
            if (++loadedImages >= numImages) {
                callback();
            }
        };
        //把sources中的图片信息导入images数组
        images[src].src = sources[src];
    }
}

//定义预加载图片数组对象，执行loading模块
window.onload = function () {
    var sources = {
        i1: "images/01.gif",
        i2: "images/02.gif",
        i3: "images/03.gif",
        i4: "images/04.gif",
        i5: "images/05.gif",
        i6: "images/06.gif",
        i7: "images/07.gif",
        i8: "images/08.gif",
        i9: "images/09.gif",
        i10: "images/10.gif",
        i11: "images/11.gif",
        i12: "images/12.gif",
        Five: "images/Five.png",
        youlose: "images/youlose.png",
        youwin: "images/youwin.png",
        underaapawn: "images/underapawn.png",
        cursor: "images/cursor.png",
        start: "images/start.png",
        startBright: "images/startBright.png",
        startGray: "images/startGray.png",
        whitechess: "images/whitechess.png",
        blackchess: "images/blackchess.png",
        select: "images/select.png",
        ready: "images/ready.png",
        chessboard: "images/chessboard.jpg"
    };
    //执行图片预加载，加载完成后执行main
    loadImages(sources, init);
};
/*end*/