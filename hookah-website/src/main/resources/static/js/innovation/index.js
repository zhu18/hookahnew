/**
 * Created by wcq on 2017/4/8.
 */
function renderChange(that,num){
    $(that).addClass('active').siblings().removeClass('active');
    var textT = $(that).children().children('.page-title').html();
    $('#J_pageTitle').html(textT);
}

