/**
 * Created by lss on 2017/4/11 0011.
 */
$(function () {
    function switchHover() {
        var item=$(".exchange-category-down .category-left .category-item");
        $(".exchange-category-down .category-left .category-box ul li").on('click',function () {
                $(this).addClass('active').siblings().removeClass('active');
                var index =$(this).index()+1;
            console.log(index);
            item.each(function () {
                console.log($(this).index())
                    if (index == $(this).index()) {
                        $(this).addClass('item-active').siblings().removeClass('item-active');
                    }
                });
        })
    }
    function hotSwitchHover() {
        $('.hot-resource-down .hot-down-rank ul li').hover(function () {
            $(this).addClass('active').siblings().removeClass('active');
            $(this).children().removeClass('li-rank').parent().siblings().children().addClass("li-rank")
        })
    }
    // 交易中心轮播图--勿删
    function carousel() {
        var box = $(".exchange-industry-resource .industry-resource-down");
        var screen = $(".screen");
        var ul = $(".screen ul");
        var ulLis = $(".screen ul li");
        var len=ulLis.length;
        var liWidth=ulLis.width();
        var arr = $("#arr");
        var arrRight = $("#arrow-right");
        var arrLeft = $("#arrow-left");

        box.on('mouseover',function () {
            arr.css({
                'display':'block'
            })
        }).on("mouseout",function () {
            arr.css({
                'display':'none'
            })
        });
        ul.css({
            'width':len*liWidth
        });

        var pic = 1;
        var i=0;
        var len1=len-parseInt(len/5)*5;
        var lis=ulLis.slice(-len1);
        var left="";
        var flag='';
        var j=0;
        var k="";

        arrRight.on('click',function () {
            if(ul.css('left')=="0px"){
                pic=1;
                j=0;
                i=0;
                flag=0;
            }
            if (flag==1){
                if(pic>=0 && pic<=parseInt(len/5) && i==0){
                    console.log(pic);
                    pic=pic-2;
                    if(pic<0){
                        pic=0;
                        ul.css({
                            'left':screen.width()*pic
                        });
                    }else {
                        ul.css({
                            'left':-screen.width() * (parseInt(len/5)-1)- liWidth * (j)+screen.width()*pic
                        });
                    }
                }else if(lis.length<5 && i<lis.length){
                    i--;
                    ul.css({
                        'left':-screen.width() * (parseInt(len/5)-1)- liWidth * (j)+screen.width()*(pic-1) + liWidth * (i)
                    });
                }
            }else {
                if(pic<parseInt(len/5)){
                    ul.css({
                        'left':-screen.width()*pic
                    });
                    pic++;
                }else if(lis.length<5 && i<lis.length){
                    i++;
                    ul.css({
                        'left':-screen.width() * (pic-1) - liWidth * i
                    });
                }else if(i==0 || i==lis.length){
                    pic=0;
                    ul.css({
                        'left':-screen.width()*pic
                    });
                }
            }
        });

        arrLeft.on('click',function () {
            if(ul.css('left')=="0px"){
                pic=0;
                i=0;
                flag=1;
                if(len1==0){
                    j=0;
                }else {
                    j=lis.length;
                }
            }
            if(flag==0){
                if(0<pic && pic <=parseInt(len/5) && i==0){
                    pic=pic-2;
                    if(pic>0){}else {pic=0}
                    ul.css({
                        'left':-screen.width() * pic
                    });
                }else if(lis.length<5 && i<=lis.length){
                    i--;
                    ul.css({
                        'left':-screen.width() * (pic-1) - liWidth * (i)
                    });
                }else if(i==lis.length){
                    pic=0;
                    ul.css({
                        'left':-screen.width() * (pic)
                    });
                }
            }else {
                if(pic <parseInt(len/5) && i==0){
                    ul.css({
                        'left':-screen.width() * (parseInt(len/5)-1)- liWidth * (j)+screen.width()*pic
                    });
                    pic++;
                }else if(lis.length<5 && i<lis.length){
                    i++;
                    ul.css({
                        'left':-screen.width() * (parseInt(len/5)-1)- liWidth * (j)+screen.width()*(pic-1) + liWidth * (i)
                    });

                }else if(i==lis.length){
                    pic=0;
                    ul.css({
                        'left':-screen.width() * (pic)
                    });
                }
            }
        });
    }


    hotSwitchHover();
    switchHover();
    carousel();
});