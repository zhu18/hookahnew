/**
 * Created by wcq on 2017/4/8.
 */
function renderChange(that){
    $(that).addClass('active').siblings().removeClass('active');
    var textT = $(that).children().children('.page-title').html();
    $('#J_pageTitle').html(textT);
    var index = $(that).index();
    $('.list-innovation .data-item').eq(index).addClass('active').siblings().removeClass('active');
}

