/**
 * Created by lss on 2017/7/19 0019.
 */
$(function () {
    if($.getUrlParam('id')){
        $.ajax({
            url:host.website+'/withdrawRecord/getWithDrawStatus',
            data:{
                id:$.getUrlParam('id')
            },
            type:'get',
            success:function (data) {
                if (data.code=="1"){
                    $("#present-application").html(data.data.addTime);
                    $("#background-audit").html(data.data.addTime);
                    $("#audit-completed").html(data.data.checkTime);
                    if(data.data.checkStatus=="0"){
                        $(".withdrawalStep2-top h4").append("提现申请已提交，后台审核中。")
                    }else if(data.data.checkStatus=="1"){
                        $(".withdrawalStep2-top h4").append("提现申请已审核通过。")
                    }else {
                        $(".withdrawalStep2-top h4").append("提现申请审核未通过。");
                        $(".withdrawalStep2-top h4").append("未通过原因："+data.data.checkMsg+"")
                    }

                }
            }
        });
    }else {
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth() +1;
        var day = date.getDate();
        var hour = date.getHours();
        var minute = date.getMinutes();
        var second = date.getSeconds();
        $("#present-application").html(year + '-' + month + '-' + day  + ' ' + hour + ':' + minute + ':' + second);
        $("#background-audit").html(year + '-' + month + '-' + day  + ' ' + hour + ':' + minute + ':' + second);
        $(".withdrawalStep2-top h4").append("提现申请已提交，后台审核中。")
    }

});