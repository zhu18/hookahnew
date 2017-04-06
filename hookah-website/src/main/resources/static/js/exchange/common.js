/**
 * Created by lss on 2017/4/6 0006.
 */

$(function () {

    console.log(window.location.pathname);
    var url=window.location.pathname;
    if(url=='/exchange/list' || url=='/exchange/details'){
    $("#exchange_menu").hover(function () {
    console.log(2);
    $(".exchange-index-menu").css('display','block')
    });
    $(".exchange-index-menu").mouseleave(function () {
    console.log(3);
    $(".exchange-index-menu").css('display','none')
    })
    $(".exchange-index-menu").css('display','none');
    }else {
        $(".exchange-index-menu").css('display', 'block');
    }
})