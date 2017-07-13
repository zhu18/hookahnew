/**
 * Created by lss on 2017/7/13 0013.
 */
$(function () {

    $(".query-condition .btn-box a").on("click",function () {
        $(this).addClass("active").siblings().removeClass("active")
    })
    $(".query-condition .select-month a").on("click",function () {
        $(this).addClass("active").siblings().removeClass("active")
    })

})