/**
 * Created by lss on 2017/4/6 0006.
 */

$(function () {
    function menuList() {
        var url = window.location.pathname;
        // $(".exchange-index-menu .menu-son-list").css('display', 'none');
        // 控制进入那个页面
        if (url == '/exchange/index') {
            $(".exchange-index-menu .menu-list").css('display', 'block');
            // $(".exchange-index-menu").mouseleave(function () {
            //     $(".exchange-index-menu .menu-son-list").css('display', 'none');
            //     // $(".exchange-index-menu .menu-list .menu-item").css({
            //     //     // 'backgroundColor': '#0063b2',
            //     //     'backgroundColor': 'green',
            //     //     'color': 'white'
            //     // })
            // })
        } else {
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
            $(this).children('.menu-son-list').show().end().siblings().children('.menu-son-list').hide();
            // $(this).siblings().children('.menu-son-list').hide();
            $(this).css({'backgroundColor': '#F39800', 'color': '#FFF', 'border-left': 'none'}).siblings().css({'backgroundColor': '#031128', 'color': '#fff', 'border-left': 'none'})
        });
        $(".exchange-index-menu .menu-list").mouseleave(function(){
            $(".exchange-index-menu .menu-son-list").css('display', 'none');
            $(".exchange-index-menu .menu-list .menu-item").css({
                'backgroundColor': '#031128',
                'color': '#fff'
            })
        })
        $(".exchange-index-menu").mouseleave(function () {
            $(".exchange-index-menu .menu-son-list").css('display', 'none');
            $(".exchange-index-menu .menu-list .menu-item").css({
                'backgroundColor': '#031128',
                'color': '#fff'
            })
        });
    }
    menuList();
});
