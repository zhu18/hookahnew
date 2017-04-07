/**
 * Created by lss on 2017/4/6 0006.
 */

$(function () {
    var url = window.location.pathname;
    $(".exchange-index-menu .menu-son-list").css('display', 'none');
    // 控制进入那个页面
    if (url == '/exchange/list' || url == '/exchange/details') {

        $(".exchange-index-menu .menu-title").hover(function () {

            $(".exchange-index-menu .menu-list").css('display', 'block')
        });

        $(".exchange-index-menu").mouseleave(function () {

            $(".exchange-index-menu .menu-list").css('display', 'none')
        })

        $(".exchange-index-menu .menu-list").css('display', 'none');

    } else {
        $(".exchange-index-menu .menu-list").css('display', 'block');
    }

    $(".exchange-index-menu .menu-list li").hover(function () {
        var index = $(this).index();
        var height = $(this).height()+1;
        var _this = $(this);
        $(this).parent().siblings('.menu-son-list').css({
            "top": 46 + index * height,
            'display': "block"
        }).mouseleave(function () {
            console.log($(this));
            $(this).css({
                'display': "none"
            });
            _this.css({
                'backgroundColor': '#3E557B',
                'color': 'white'
            })
        });

        $(this).css({
            'backgroundColor': 'white',
            'color': '#000000'
        }).siblings().css({
            'backgroundColor': '#3E557B',
            'color': 'white'
        });
    })
    $(".exchange-index-menu .menu-son-list li ").hover(function () {
        $(this).css({
            'backgroundColor': '#3E557B'
        }).find('a').css({
            'color': 'white'
        }).parent().siblings().css({
            'backgroundColor': 'white'
        }).find('a').css({
            'color': '#000000'
        })
    })

});
