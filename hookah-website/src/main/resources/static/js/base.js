$(document).ready(function () {
    var pathname = window.location.pathname
    if (pathname.indexOf('/exchange') >= 0) {
        $('#exchange_menu').show();
    } else {
        $('#exchange_menu').hide();
    }
// console.log(pathname)
    $('.header-bottom-bar ul li').each(function () {
        var elementVal = $(this).children('a').attr('href');
        if (pathname.indexOf(elementVal) >= 0) {
            $(this).addClass('active').siblings()
        }
        if(pathname == '/'){
            $('#menu_index').addClass('active');
        }else{
            $('#menu_index').removeClass('active');
        }
    })


})



var Loading = {};
Loading.start = function(){
    Loading.stop();
    var loadingHtml = '';
    loadingHtml += '<div class="loading-wrapper">';
    loadingHtml += '<div class="loading-bg">';
    loadingHtml += '</div>';
    loadingHtml += '<div class="spinner">';
    loadingHtml += '<div class="rect1"></div>';
    loadingHtml += '<div class="rect2"></div>';
    loadingHtml += '<div class="rect3"></div>';
    loadingHtml += '<div class="rect4"></div>';
    loadingHtml += '<div class="rect5"></div>';
    loadingHtml += '<div class="rect6"></div>';
    loadingHtml += '</div>';
    loadingHtml += '</div>';
    $('body').append(loadingHtml);
};
Loading.stop = function(){
    $('.loading-wrapper').remove();
};
