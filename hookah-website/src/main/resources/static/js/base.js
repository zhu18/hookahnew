$(document).ready(function () {
    var pathname = window.location.pathname
    if (pathname.indexOf('/exchange') >= 0) {
        $('#exchange_menu').show();
    } else {
        $('#exchange_menu').hide();
    }

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

    function shopCart() {
        var height=$('.header-centerbar .shopping-cart-down').height();
        if(height>=600){
            $('.header-centerbar .shopping-cart-down .shopping-cart-down-content').css({
                'height':'600px',
                'overflow-y':'scroll'
            })
        }
        $('.header-centerbar .shopping-cart').hover(function () {
            $('.shopping-cart .shopping-cart-down').show();
            $('.shopping-cart .shopping-cart-down').css({
                'width':'310px'
            });
            $('.shopping-cart .shopping-cart-top').css({
                'backgroundColor':'white',
                'boxShadow':'0px -1px 1px #DFDFDF',
                'border':'none'
            });
            $(this).mouseleave(function () {
                $('.shopping-cart .shopping-cart-down').hide();
                $('.shopping-cart .shopping-cart-top').css({
                    'backgroundColor':'#F9F9F9',
                    'border':'1px solid #DFDFDF',
                    'boxShadow':'none'
                });
            })

        })

    }
    // shopCart();
  $(".shopping-cart").mouseenter(function(){
    $(this).addClass('hover')
  });
  $(".shopping-cart").mouseleave(function(){
    $(this).removeClass('hover')
  });
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
