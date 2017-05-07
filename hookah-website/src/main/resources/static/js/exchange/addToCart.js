$.getUrlParam = function (key) {
  var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
  var result = window.location.search.substr(1).match(reg);
  return result ? decodeURIComponent(result[2]) : null;
};
var goodsId = $.getUrlParam('goodsId');
var number = $.getUrlParam('number');
$('#goodsNum span').html(number);
$('.goGoodsDetails').attr('href','/exchange/details?id='+goodsId);