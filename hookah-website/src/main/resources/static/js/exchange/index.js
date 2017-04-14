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

        var pic =0;
        var i=0;
        var len1=len-5-parseInt((len-5)/5)*5;
        console.log(len1);
        var lis=ulLis.slice(-len1-5);
        var left="";
        var flag='';
        var j=0;
        var k=0;
        // 右箭头点击事件
        arrRight.on('click',function () {
            $(this).attr("disabled", "disabled");
            if(parseInt(ul.css('transform').split(',').slice(-2,-1))==0){
                pic=0;
                j=0;
                i=0;
            }
            if(j==1){
                ul.css({
                    'transform': "translateX("+(ul.css('transform').split(',').slice(-2, -1)-screen.width())+"px)",
                    'transition': 'all 0.5s linear'
                });
                j=0;
                k=1;
                pic=0;
                return
            }
            if(k==1){
                ul.css({
                    'transform': "translateX(0px)",
                    'transition': "none",
                    'left': 0
                });

                k=0;
                i=0;
                pic=0;
            }
                if(pic<Math.ceil((len-5)/5)-2 && i==0){
                    ul.css({
                        'transform': "translateX("+(ul.css('transform').split(',').slice(-2, -1)-screen.width())+"px)",
                        'transition': 'all 0.5s linear'
                    });
                    pic++;
                }else if(lis.length-5<5 && i<lis.length-5){
                    i++;
                    ul.css({
                        'transform': "translateX("+(ul.css('transform').split(',').slice(-2, -1) - liWidth )+"px)"
                        // 'left':-screen.width() * (pic-1) - liWidth * i
                    });
                }else if((i==0 || i==lis.length-5)|| pic==Math.ceil((len-5)/5)-2){

                    ul.css({
                        'transform': "translateX("+(parseInt(ul.css('transform').split(',').slice(-2,-1))-screen.width())+"px)"
                    });
                    // 记录是不是进入最后一张
                    k=1;
                    i=0;
                }
            $(this).removeAttr("disabled");

        });
        // 左箭头点击事件
        arrLeft.on('click',function () {

            if(parseInt(ul.css('transform').split(',').slice(-2, -1))==0){
                // ul.css({
                //     'transform': "translateX(0px)"
                // });
                pic=Math.ceil((len-5)/5)-2;
                i=0;
                flag=1;
                j=1;
                k=1;
            }

            if(k==1){
                ul.css({
                    'transform': "translateX(0px)",
                    'transition': "none",
                    'left': -(len - 5) * liWidth
                });
                k=0;
                ul.css({
                    'transform': "translateX("+(parseInt(ul.css('left'))+screen.width())+"px)",
                    'transition': 'all 0.3s linear',
                    'left':'0px'
                });
                return
            }
                if(pic<=(Math.ceil((len-5)/5)-2) && pic >0 && i==0){
                    pic--;
                    ul.css({
                        'transform': "translateX("+(parseInt(ul.css('transform').split(',').slice(-2, -1))+screen.width())+"px)",
                        'transition': 'all 0.3s linear',
                        'left':'0px'
                    });

                }else if(lis.length-5<5 && i<=lis.length-5){
                    i--;
                    ul.css({
                        'transform': "translateX("+(parseInt(ul.css('transform').split(',').slice(-2, -1))+liWidth)+"px)",
                        'transition': 'all 0.3s linear'
                    });
                }else if(i==0){
                }
        });




    }
    hotSwitchHover();
    switchHover();
    carousel();
});