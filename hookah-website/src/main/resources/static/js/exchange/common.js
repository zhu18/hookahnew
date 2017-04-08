/**
 * Created by lss on 2017/4/6 0006.
 */

$(function () {
    function menuList() {
        var url = window.location.pathname;
        $(".exchange-index-menu .menu-son-list").css('display', 'none');
        // 控制进入那个页面
        if (url == '/exchange/index') {
            console.log(1);
            $(".exchange-index-menu .menu-list").css('display', 'block');
            $(".exchange-index-menu").mouseleave(function () {
                $(".exchange-index-menu .menu-son-list").css('display', 'none');
                $(".exchange-index-menu .menu-list .menu-item").css({
                    'backgroundColor': '#3E557B',
                    'color': 'white'
                })
            })
        } else {
            console.log(2);
            $(".exchange-index-menu .menu-list").css('display', 'none');

            $(".exchange-index-menu .menu-title").hover(function () {

                $(".exchange-index-menu .menu-list").css('display', 'block')
            });

            $(".exchange-index-menu").mouseleave(function () {

                $(".exchange-index-menu .menu-list").css('display', 'none');
                $(".exchange-index-menu .menu-son-list").css('display', 'none');

            })
        }
        $(".exchange-index-menu .menu-list .menu-item").hover(function () {
            $(this).children('.menu-son-list').show();
            $(this).siblings().children('.menu-son-list').hide();
            $(this).css({'backgroundColor': '#fff', 'color': '#3E557B', 'border-left': '1px solid #3E557B'}).siblings().css({'backgroundColor': '#3E557B', 'color': '#fff', 'border-left': 'none'})
        });
        $(".exchange-index-menu").mouseleave(function () {
            $(".exchange-index-menu .menu-son-list").css('display', 'none');
            $(".exchange-index-menu .menu-list .menu-item").css({
                'backgroundColor': '#3E557B',
                'color': 'white'
            })
        });
    }
    menuList();
});
