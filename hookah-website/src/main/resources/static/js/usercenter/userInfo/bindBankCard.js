/**
 * Created by lss on 2017/7/19 0019.
 */
$(function () {
    $(".btn").on("click",function () {
        $.ajax({
            url:host.website+'/payBankCard/addBankInfo',
            data:{
                cardCode:$('#cardCode').val(),
                openBank:$('#openBank').val(),
                phoneNumber:$('#phoneNumber').val(),
                payBankId:$('#bindName').val()
            },
            type:'get',
            success:function (data) {
                if (data.code=="1"){
                    window.location.href= host.website+'/usercenter/fundmanage';
                }
            }
        });
    });
     $.ajax({
         url:host.website+'/payBankCard/searchBankInfo',
         data:{},
         type:'get',
         success:function (data) {
             var list=data.data.bank;
             var html = '<option value="">全部</option>';
             for(var i=0;i<list.length;i++){
                 html += '<option value="'+list[i].id+'">'+list[i].bankName+'</option>';
                 $('#bindName').html(html);
             }
             $("#account-name").html(data.data.cardOwner)
         }
     });

})
