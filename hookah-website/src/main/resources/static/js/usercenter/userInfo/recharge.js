/**
 * Created by zsj on 2017/7/19 0014.
 */
/*
 $(function () {
 $.ajax({
 url: host.website + '/payBankCard/userFunds',
 data: {},
 type: 'get',
 success: function (data) {
 console.log(data)
 $("#J_useBalance").html("￥" + (data.data.useBalance / 100).toFixed(2));

 }
 });
 })
 */
$("#J_rechargeBtn").on("click", function () {
  var data = {};
  data.money = $('#money').val();
  data.userId = $('#userId').val();
  console.log(';')
  $.ajax({
    url: '/payAccount/userRecharge',
    data: data,
    type: 'get',
    success: function (data) {
      if (data.code == "1") {

      }
    }
  });
});
