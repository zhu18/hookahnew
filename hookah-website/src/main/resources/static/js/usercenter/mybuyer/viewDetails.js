/**
 * Created by ki on 2017/4/26.
 */

$(function(){
    var urlSearch = window.location.search;
    console.log("urlSearch:"+urlSearch);
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

    }
})
