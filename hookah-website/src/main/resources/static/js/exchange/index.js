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
        //要做实现找人
        var box = $(".exchange-industry-resource .industry-resource-down");
        var screen = $(".screen");
        var ul = $(".screen ul");
        var ulLis = $(".screen ul li");
        var len=ulLis.length;
        var liWidth=ulLis.width();
        var liheight=ulLis.height();
        var arr = $("#arr");
        var arrRight = $("#arrow-right");
        var arrLeft = $("#arrow-left");
        //图片宽度
        var imgWidth = screen.offsetWidth;
        var timer = null;
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
        //3.2点击右侧按钮 将ul移动到对应位置
        var pic = 1;
        var pi=0
        var n=0;
        var lis='';
        var lis1='';
        var i=0;
        var j=0;
        var len1=len-parseInt(len/5)*5;
        var left="";
           lis=ulLis.slice(-len1);

        arrRight.on('click',function () {
            console.log(pic);
          if(pic<parseInt(len/5)){
              ul.css({
                  'left':-screen.width()*pic
              });
              pic++;
          }else if(lis.length<5 && i<lis.length){
              // console.log(9);
              // console.log(pic);
              console.log(-screen.width() * (pic-1) - liWidth * (i + 1));
              ul.css({
                  'left':-screen.width() * (pic-1) - liWidth * (i + 1)
              });
              i++;
          }
          if(i==lis.length){
              pic=0;
              i=0;
          }

        });

        arrLeft.on('click',function () {
            console.log(pic);
            left=-screen.width() * (parseInt(len/5)-1) - liWidth * (lis.length);
                if(pic<parseInt(len/5)+1){
                    ul.css({
                        'left':left+screen.width()*(pic-1)
                    });
                    pic++;
                }else if(lis.length<5 && i<lis.length){
                    // console.log(9);
                    // console.log(pic);
                    ul.css({
                        'left':left+screen.width()*(pic-2) + liWidth * (i + 1)
                    });
                    i++;
                }else {
                    console.log(10);
                    pic=1;
                    i=0;
                    ul.css({
                        'left':screen.width()*(pic-1)
                    });
                }
        });
    hotSwitchHover();
    switchHover();
});