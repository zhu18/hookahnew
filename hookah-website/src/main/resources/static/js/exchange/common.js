/**
 * Created by lss on 2017/4/6 0006.
 */

$(function () {
    var url=window.location.pathname;
    $(".exchange-index-menu div").css('display', 'none');
    if(url=='/exchange/list' || url=='/exchange/details'){
    $("#exchange_menu").hover(function () {
    $(".exchange-index-menu").css('display','block')
    });

    $(".exchange-index-menu").mouseleave(function () {
    $(".exchange-index-menu").css('display','none')
    })

    $(".exchange-index-menu").css('display','none');

    }else {
        $(".exchange-index-menu").css('display', 'block');
    }

    $(".exchange-index-menu li").hover(function () {
        var index=$(this).index();
        var height=$(this).height()+1;
        var _this=$(this);

        $(this).parent().siblings().css({
            "top":index*height,
            'display':"block"
        }).mouseleave(function () {
            $(this).css({
                'display':"none"
            })
            _this.css({
                'backgroundColor':'#3E557B',
                 'color':'white'
            })
        });
        $(this).css({
            'backgroundColor':'white',
            'color':'#000000'
        }).siblings().css({
            'backgroundColor':'#3E557B',
            'color':'white'
        })
        // $(".exchange-index-menu div").mouseleave(function () {
        //     $(this).css({
        //         'backgroundColor':'#3E557B',
        //         'color':'white'
        //     })
        // })
    })

})