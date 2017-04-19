/**
 * Created by lss on 2017/4/11 0011.
 */
$(function () {
    function switchHover() {
        var item=$(".exchange-category-down .category-left .category-item");
        $(".exchange-category-down .category-left .category-box ul li").hover(function () {
            $(this).addClass('active').siblings().removeClass('active');
            var index =$(this).index()+1;
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
            $(this).addClass('active').siblings().removeClass('active');})
    }
    // 交易中心轮播图--勿删
    function carousel() {
        var box = $(".exchange-industry-resource .industry-resource-down");
        var screen = $(".screen");
        var ul = $(".screen ul");
        // 获取到每个li
        var ulLis = $(".screen ul li");
        // li的个数
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

        var pic =0;
        var i=0;
        // 计算出多出来的li的个数
        var len1=len-parseInt((len)/5)*5;
        // 把多出来的li从总的li中截取出来
        var lis=ulLis.slice(-len1);
        // 截取首页的li
        var top5 = $(".screen ul li").slice(0,5);
        // 把首页的li追加到li后面
        top5.each(function () {
            var item=$(this).clone();
            ul.append(item)
        });
        var newLis = $(".screen ul li").length;
        // 给ul赋予新的宽度
        ul.css({
            'width':newLis*liWidth
        });
        var left="";
        var flag='';
        var j=0;
        var k=0;
        // 右箭头点击事件
        arrRight.on('click',function () {
            playRight()
        });

        // 左箭头点击事件
        arrLeft.on('click',function () {
            playLeft()
        });
        function playLeft() {
            arrLeft.unbind("click");
            if(parseInt(ul.css('transform').split(',').slice(-2, -1))>=0){
                pic=Math.ceil(len/5)-2;
                i=0;
                flag=1;
                j=1;
                k=1;
            }

            if(k==1){
                ul.css({
                    'transform': "translateX(0px)",
                    'transition': "none",
                    'left': -len  * liWidth
                });
                k=0;

                ul.transition({transform: "translateX("+(parseInt(ul.css('left'))+screen.width())+"px)",
                               left:'0px'
                }, 500, 'linear', function(){
                    arrLeft.on("click",function () {
                        playLeft()
                    });
                });
                return;
            }
            if(pic<=(Math.ceil(len/5)-2) && pic >0 ){
                pic--;
                ul.transition({transform: "translateX("+(parseInt(ul.css('transform').split(',').slice(-2, -1))+screen.width())+"px)"}, 500, 'linear', function(){
                    arrLeft.on("click",function () {
                        playLeft()
                    });
                });
                i=0;

            }else if(lis.length<5 && i<=lis.length && i==0){
                i++;
                ul.transition({transform: "translateX("+(parseInt(ul.css('transform').split(',').slice(-2, -1))+liWidth*lis.length)+"px)"}, 500, 'linear', function(){
                    arrLeft.on("click",function () {
                        playLeft()
                    });
                });
            }
        }
        function playRight() {
            arrRight.unbind("click");

            if(parseInt(ul.css('transform').split(',').slice(-2,-1))==0){
                pic=0;
                j=0;
                i=0;
            }
            if(j==1){
                ul.transition({transform: "translateX("+(ul.css('transform').split(',').slice(-2, -1)-screen.width())+"px)"}, 500, 'linear', function(){
                    arrRight.on("click",function () {
                        playRight()
                    });
                });
                j=0;
                k=1;
                pic=0;
                return;
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
            if(pic<Math.ceil(len/5)-2 && i==0){
                ul.transition({transform: "translateX("+(ul.css('transform').split(',').slice(-2, -1)-screen.width())+"px)"}, 500, 'linear', function(){
                    arrRight.on("click",function () {
                        playRight()
                    });
                });
                pic++;
            }else if(lis.length<5 && i<lis.length && i==0){
                i++;
                ul.transition({transform: "translateX("+(ul.css('transform').split(',').slice(-2, -1) - liWidth*lis.length )+"px)"}, 500, 'linear', function(){
                    arrRight.on("click",function () {
                        playRight()
                    });
                });
            }else if(i!=0){
                ul.transition({transform: "translateX("+(parseInt(ul.css('transform').split(',').slice(-2,-1))-screen.width())+"px)"}, 500, 'linear', function(){
                    arrRight.on("click",function () {
                        playRight()
                    });
                });
                // 记录是不是进入最后一张
                k=1;
                i=0;
            }

        }
    }
    function imgHover() {
        var flag=1;
        var timer1=''

        $('.exchange-category-top ul li').mouseenter(function () {
            var _this=$(this);
            timer1=setInterval(function () {
                    var y=parseInt(_this.children('.category-top-img').css('background-position-y'));
                    if(y<-10300){
                        clearInterval(timer1);
                        return;
                    }else {
                        _this.children('.category-top-img').css({
                            'background-position-y':(y-175)+'px'
                        }) ;
                    }
                console.log(1);
            },10);

        }).mouseleave(function () {
            clearInterval(timer1);
            var _this=$(this);
                var timer2=setInterval(function () {
                    var y=parseInt(_this.children('.category-top-img').css('background-position-y'));
                    if(y==0){
                        clearInterval(timer2);
                        return;
                    }else {
                        _this.children('.category-top-img').css({
                            'background-position-y':(y+175)+'px'
                        }) ;
                    }
                },10);
        })
    }
    // imgHover();
    hotSwitchHover();
    switchHover();
    carousel();
});