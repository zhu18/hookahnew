/**
 * Created by lss on 2017/7/19 0019.
 */
$(function () {
    var land=$("input[name='safetyLandScore']").val()?parseInt($("input[name='safetyLandScore']").val()):0,
    pay=$("input[name='safetyPayScore']").val()?parseInt($("input[name='safetyPayScore']").val()):0,
    phone=$("input[name='safetyPhoneScore']").val()?parseInt($("input[name='safetyPhoneScore']").val()):0,
    mail=$("input[name='safetyMailScore']").val()?parseInt($("input[name='safetyMailScore']").val()):0;
    var totle=land+pay+phone+mail;
     $(".progress-bar").css({
         "width":totle+"%"
     })
    if(totle<60){
        $(".progress-bar").css({
            "background":"red"
        })
        $(".degree").html("差")
    }else if(totle>=60 && totle<=80){
        $(".progress-bar").css({
            "background":"#efefef"
        })
        $(".degree").html("中")
    }else {
        $(".progress-bar").css({
            "background":"red"
        })
        $(".degree").html("高")
    }
});