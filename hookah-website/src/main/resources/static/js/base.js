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
            console.log('有')
        } else {
            console.log('木有')
        }
        if(pathname == '/'){
            $('#menu_index').addClass('active');
        }else{
            $('#menu_index').removeClass('active');
        }
    })


})
