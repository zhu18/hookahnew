/**
 * Created by ki on 2017/4/24.
 */
$(function(){
    var wjx_s = "★";
    var wjx_k = "☆";
    $(".comment>li").mouseenter(function () {
        $(this).text(wjx_s).prevAll().text(wjx_s);
        $(this).nextAll().text(wjx_k);
    });
    $(".comment").mouseleave(function () {
        $(".comment").children().text(wjx_k);
        $("li.current").text(wjx_s).prevAll().text(wjx_s);
        var num = $("li.current").text(wjx_s).index()+1;
        $(".fen").text(num+"分");
    });
    $("li").click(function () {
        $(this).addClass("current").siblings().removeClass("current");
    });

})