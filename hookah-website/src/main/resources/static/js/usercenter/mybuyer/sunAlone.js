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

function check(orderId,goodsId,commentContent,commentLevel){
    $.ajax({
        type: "post",
        url: '/comment/add',
        data:{
            orderId:orderId,
            goodsId:goodsId,
            commentContent:commentContent,
            commentLevel:commentLevel
        },
        success: function(msg) {
            if (msg.code == 1) {
                $.alert('提交成功');
               window.location.href="/usercenter/buyer/orderManagement";
            } else {
                $.alert(msg.message);
            }
        }
    })
}