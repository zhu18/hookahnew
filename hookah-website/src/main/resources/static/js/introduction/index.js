/**
 * Created by ki on 2017/4/7.
 */

$(function(){
    var url=window.location.pathname;
    console.log("url:"+url);
    $('.topList ul li').each(function () {
        var href = $(this).children('a').attr('href');
        if (url.indexOf(href) >= 0 ) {
            $(this).addClass('active').siblings().removeClass("active");
            if(url.indexOf("introduction")){
                $(".header-bottom-bar ul li ").eq(6).addClass("active");
            }
        }
    })
})