/**
 * Created by lss on 2017/7/19 0014.
 */

 $(function () {
       $.ajax({
             url: host.website + '/payBankCard/userFunds',
             data: {},
             type: 'get',
             success: function (data) {
                  console.log(data);
                  $("#J_useBalance").html("￥" + (data.data.useBalance / 100).toFixed(2));
                  $("#J_cardCode").html(data.data.useBalance);
             }
       });
 });

$("#J_rechargeBtn").on("click", function () {
    var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
    var money=$('#recharge-money').val();
    if(reg.test(money)){
        window.location.href= host.website+'/payAccount/userRecharge?'+'money='+$('#recharge-money').val();
    }else if(!money){
        $.alert('充值金额不能为空!')
    }else {
        $.alert('请填写正确格式的金额!')
    }
});
