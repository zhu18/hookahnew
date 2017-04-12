/**
 * Created by ki on 2017/4/12.
 */

$(function(){
    $(".all").on("click","li",function(){
        var index = $(this).siblings().removeClass("active").end().addClass("active").index();
        $(".list").hide().eq(index).show();
    })
})