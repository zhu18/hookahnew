var number = $.getUrlParam('number');
var fmt = $.getUrlParam('fmt');
var goodsPrice = $.getUrlParam('gm');
$('#goodsNum span').html(number);
$('.formats').html(fmt);
$('.goodsPrice').html(goodsPrice);
