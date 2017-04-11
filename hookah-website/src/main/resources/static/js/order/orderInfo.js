/**
 * Created by wubingbing on 2017/4/11.
 */
$.getUrlParam = function (key) {
  var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
  var result = window.location.search.substr(1).match(reg);
  return result ? decodeURIComponent(result[2]) : null;
};

var params = $.getUrlParam('ids');
goOrder(params);
function goOrder(params) {
  $.ajax({
    url:'/order/orderInfo',
    type:'post',
    data:params,
    success: function (data) {
      if (data.code == 1) {

      }else{
        $.alert(data.message)
      }
    }
  });
}