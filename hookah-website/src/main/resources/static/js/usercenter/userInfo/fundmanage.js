/**
 * Created by lss on 2017/7/19 0019.
 */
$(function () {
    $.ajax({
        url:host.website+'/payBankCard/userFunds',
        data:{},
        type:'get',
        success:function (data) {
            $(".account-funds-content-left .money").html("￥"+(data.data.useBalance / 100).toFixed(2))
            $(".freeze").html("￥"+(data.data.freeze / 100).toFixed(2))
            $(".useBalance").html("￥"+(data.data.useBalance / 100).toFixed(2))
        }
    });

})