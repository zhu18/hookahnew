/**
 * Created by ki on 2017/4/26.
 */

$(function(){
    var urlSearch = window.location.search;
    if(urlSearch.indexOf('num=1')>=0){
        $('.leftContent .state-txt').text('完成');
    };
    if(urlSearch.indexOf('num=2')>=0){
        $('.leftContent .state-txt').text('待付款').css("color","#e34f4f");
        $('.state-btn').show();
        $('.rightContent .iconfont').css('color','#ccc');
        $('.rightContent .icon-dingdan').css('color','#e34f4f');
        $('.rightContent .line span').css('background-position','0 -74px');
        $('.rightContent .line .gray').css('background-position','0 -17px');
        $('.viewDetails .goods-list .goods-table thead tr th:nth-child(5)').css('display','none');
        $('.viewDetails .goods-list .goods-table tbody tr td:nth-child(5)').css('display','none');
        $('.viewDetails tbody tr td').not('.goods').addClass('public');
    }
})

function addCart(goodsId,formatId,goodsNumber) {
    $.ajax({
        url: '/cart/add',
        type: 'post',
        data: {
            goodsId: goodsId,
            formatId: formatId,
            goodsNumber:goodsNumber
        },
        success: function (data) {
            if (data.code == "1") {
                window.location.href = "/exchange/addToCart?goodsId=" + goodsId + "&number=" + goodsNumber;
            } else {
                $.alert(data.message);
            }
        }
    });
}