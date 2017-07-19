/**
 * Created by lss on 2017/7/19 0019.
 */
$(function () {
    $.ajax({
        url:host.website+'/payBankCard/addBankInfo',
        data:{
            cardCode:$('#cardCode').val(),
            openBank:$('#openBank').val(),
            phoneNumber:$('#phoneNumber').val(),
            payBankId:$('#payBankId').val()
        },
        type:'get',
        cache:false,
        success:function (data) {
            if (data.code=="1"){
                // window.location.href= host.website+'/usercenter/fundmanage';

            }
        }
    });
})
