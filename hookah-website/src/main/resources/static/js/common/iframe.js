/**
 * Created by Dajun on 2017-7-25.
 */
var height = document.height;
var iframe2 = document.createElement('iframe');
iframe2.height = 0;
iframe2.style.display = 'none';
iframe2.src = "http://192.168.200.116:8080/?height=" + document.height + '&v' + Math.random();
document.body.appendChild(iframe2);
setInterval(function () {
  if (document.height != height) {
    iframe2.src = "http://galaxy.com.cn/height.html?height=" + document.height + '&v' + Math.random();
    height = document.height;
  }
}, 10);