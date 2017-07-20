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
    window.location.href= host.website+'/payAccount/userRecharge?'+'money='+$('#recharge-money').val();
  // $.ajax({
  //   url: host.website + '/payAccount/userRecharge',
  //   data: {
  //       money :$('#recharge-money').val()
  //   },
  //   type: 'get',
  //   success: function (data) {
  //     if (data.code == "1") {
  //
  //     }
  //   }
  // });
});
