var number = $.getUrlParam('number');
var fmt = $.getUrlParam('fmt');
var goodsPrice = $.getUrlParam('gm');
$('#goodsNum span').html(number);
  if(fmt=="null"){
      $(".standard").hide()
  }else {
      $('.formats').html(fmt);
  }

$('.goodsPrice').html(goodsPrice);
