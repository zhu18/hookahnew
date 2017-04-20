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
        var liWidth=''
        // li的个数
        var len=ulLis.length;
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
        var pic=0;
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
        var screeW='';
        var cab='';
        if( screen.width() >=1180 && screen.width()<=1440) {
            console.log($(document).width());
            console.log(1);
            screeW=screen.width();
            liWidth=screeW/5;
            $(".screen ul li").css({
                'width':liWidth
            });
        }else {
            screeW=1180;
            liWidth=1180/5;
            $(".screen ul li").css({
                'width':liWidth
            });
        }
        $(window).resize(function(){
            cab=screeW-screen.width();
            screeW=screen.width();
            if( screen.width() >=1180 && screen.width()<=1440) {
                liWidth=screen.width()/5;
                $(".screen ul li").css({
                    'width':liWidth
                });
            }else {
                liWidth=1180/5;
                $(".screen ul li").css({
                    'width':liWidth
                });
            }
            ul.css({
                'width':newLis*liWidth
            });
            if(parseInt(ul.css('left'))>=0){
            }else {
                ul.css({
                    'left':parseInt(ul.css('left'))+cab
                });
            }
        });
        ul.css({
            'width':newLis*liWidth
        });
        var left="";
        var flag='';
        var j=0;
        var k=0;
        function playLeft() {
            // if(parseInt(ul.css('transform').split(',').slice(-2, -1))>=0){
            //     pic=Math.ceil(len/5)-2;
            //     i=0;
            //     flag=1;
            //     j=1;
            //     k=1;
            // }
            //
            // if(k==1){
            //     ul.css({
            //         'transform': "translateX(0px)",
            //         'transition': "none",
            //         'left': -len  * liWidth
            //     });
            //     k=0;
            //
            //     ul.transition({transform: "translateX("+(parseInt(ul.css('left'))+screen.width())+"px)",
            //                    left:'0px'
            //     }, 500, 'linear', function(){
            //         arrLeft.on("click",function () {
            //             playLeft()
            //         });
            //     });
            //     return;
            // }
            // if(pic<=(Math.ceil(len/5)-2) && pic >0 ){
            //     pic--;
            //     ul.transition({transform: "translateX("+(parseInt(ul.css('transform').split(',').slice(-2, -1))+screen.width())+"px)"}, 500, 'linear', function(){
            //         arrLeft.on("click",function () {
            //             playLeft()
            //         });
            //     });
            //     i=0;
            //
            // }else if(lis.length<5 && i<=lis.length && i==0){
            //     i++;
            //     ul.transition({transform: "translateX("+(parseInt(ul.css('transform').split(',').slice(-2, -1))+liWidth*lis.length)+"px)"}, 500, 'linear', function(){
            //         arrLeft.on("click",function () {
            //             playLeft()
            //         });
            //     });
            // }
            // arrLeft.unbind("click");
            if(parseInt(ul.css('left'))>=0){
                pic=Math.ceil(len/5)-2;
                ul.css({'left':'0px'});
                i=0;
                flag=1;
                j=1;
                k=1;
            }
            if(k==1){
                ul.css({
                    'left': -len  * liWidth
                });
                k=0;
                animate(ul,parseInt(ul.css('left'))+screen.width());
                return;
            }
            if(pic<=(Math.ceil(len/5)-2) && pic >0 ){
                pic--;
                animate(ul,parseInt(ul.css('left'))+screen.width());
                i=0;
            }else if(lis.length<5 && i<=lis.length && i==0){
                i++;
                animate(ul,parseInt(ul.css('left'))+liWidth*lis.length);

            }

        }
        function playRight() {
            // arrRight.unbind("click");
            // if(parseInt(ul.css('transform').split(',').slice(-2,-1))==0){
            //     pic=0;
            //     j=0;
            //     i=0;
            // }
            // if(j==1){
            //     ul.transition({transform: "translateX("+(ul.css('transform').split(',').slice(-2, -1)-screen.width())+"px)"}, 500, 'linear', function(){
            //         arrRight.on("click",function () {
            //             playRight()
            //         });
            //     });
            //     j=0;
            //     k=1;
            //     pic=0;
            //     return;
            // }
            // if(k==1){
            //     ul.css({
            //         'transform': "translateX(0px)",
            //         'transition': "none",
            //         'left': 0
            //     });
            //     k=0;
            //     i=0;
            //     pic=0;
            // }
            // if(pic<Math.ceil(len/5)-2 && i==0){
            //     ul.transition({transform: "translateX("+(ul.css('transform').split(',').slice(-2, -1)-screen.width())+"px)"}, 500, 'linear', function(){
            //         arrRight.on("click",function () {
            //             playRight()
            //         });
            //     });
            //     pic++;
            // }else if(lis.length<5 && i<lis.length && i==0){
            //     i++;
            //     ul.transition({transform: "translateX("+(ul.css('transform').split(',').slice(-2, -1) - liWidth*lis.length )+"px)"}, 500, 'linear', function(){
            //         arrRight.on("click",function () {
            //             playRight()
            //         });
            //     });
            // }else if(i!=0){
            //     ul.transition({transform: "translateX("+(parseInt(ul.css('transform').split(',').slice(-2,-1))-screen.width())+"px)"}, 500, 'linear', function(){
            //         arrRight.on("click",function () {
            //             playRight()
            //         });
            //     });
            //     // 记录是不是进入最后一张
            //     k=1;
            //     i=0;
            // }
            // arrRight.unbind("click");
            console.log(screen.width());
            if(parseInt(ul.css('left'))==0){
                pic=0;
                j=0;
                i=0;
            }
            if(j==1){
                animate(ul,parseInt(ul.css('left'))-screen.width());
                j=0;
                k=1;
                pic=0;
                return;
            }
            if(k==1){
                ul.css({
                    'left': 0
                });
                k=0;
                i=0;
                pic=0;
            }
            if(pic<Math.ceil(len/5)-2 && i==0){
                animate(ul,parseInt(ul.css('left'))-screen.width());
                pic++;
            }else if(lis.length<5 && i<lis.length && i==0){
                i++;
                animate(ul,parseInt(ul.css('left'))-liWidth*lis.length);

            }else if(i!=0){
                animate(ul,parseInt(ul.css('left'))-screen.width());
                k=1;
                i=0;
            }
        }
        // 右箭头点击事件
        arrRight.on('click',function () {
            playRight()
        });
        // 左箭头点击事件
        arrLeft.on('click',function () {
            playLeft()
        });
        function animate(obj, target) {
            var obj=obj[0];
            clearInterval(obj.timer);
            obj.timer = setInterval(function () {
                var step = 25;
                var step = obj.offsetLeft < target ? step : -step;
                if (Math.abs(obj.offsetLeft - target) > Math.abs(step)) {
                    obj.style.left = obj.offsetLeft + step + "px";
                } else {
                    obj.style.left = target + "px";
                    clearInterval(obj.timer);
                }
            }, 5)

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