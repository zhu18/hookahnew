/**
 * Created by lss on 2017/7/19 0019.
 */
$(function () {
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
                $("#audit-completed").html(data.data.checkTime)
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
});