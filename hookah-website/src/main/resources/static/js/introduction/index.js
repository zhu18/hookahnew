/**
 * Created by ki on 2017/4/7.
 */

$(function(){
    var url=window.location.pathname;
    console.log(url);
    $('.topList ul li').each(function () {
        var elementVal = $(this).children('a').attr('href');
        if (url.indexOf(elementVal) >= 0) {
            $(this).addClass('active').siblings()
        }
    })
})