/**
 * Created by lss on 2017/4/28 0028.
 */

$(function () {
   $('.co-organization .grid-row ul li a').hover(function () {

       $(this).css({
           "background-position":"0 -37px"
       }).mouseleave(function () {
           $(this).css({
               "background-position":"0 0"
           })
       })
   })

});