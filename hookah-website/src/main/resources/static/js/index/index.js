/**
 * Created by lss on 2017/4/28 0028.
 */

$(function () {
   // $('.co-organization .grid-row ul li a').hover(function () {
   //
   //     $(this).css({
   //         "background-position":"0 -37px"
   //     }).mouseleave(function () {
   //         $(this).css({
   //             "background-position":"0 0"
   //         })
   //     })
   // })
    sliceString(".infos-info",85);
});
function sliceString(className,number){
    var goodsBrief = $(className);
    var number=parseInt(number);
    $(goodsBrief).each(function(){
        var text = $(this).html();
        if(text.length>=number){
            $(this).html(text.slice(0,number)+'...');
        }
    });
}