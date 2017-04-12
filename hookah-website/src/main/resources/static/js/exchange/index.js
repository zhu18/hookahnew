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
        var len=ulLis.length
        var liWidth=ulLis.width();
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
    //3.2点击右侧按钮 将ul移动到对应位置
    var pic = 0;//用于表示应该向左移动的图片的张数 也表示当前图片的索引
    arrRight.on('click',function () {
        console.log(ul.width());
        console.log(len);
        console.log(liWidth);
        // playNext();
    });
    arrLeft.on('click',function () {

        playNext();
    });

    function playNext() {
        //如果pic等于最后一张图片的索引 再点击就该跳转了
        if (pic == ulLis.length - 1) {
            ul.style.left = 0;
            pic = 0;
        }
        pic++;
        //目标 和 pic有关 和 图片宽度有关 而且是 负数
        var target = -pic * liWidth;
        animate(ul, target);

        //按钮也跟着走
        //square 表示当前应该亮起的按钮
        //如果小于最后一个按钮的索引 就加否则就该等于0 了
        if (square < olLis.length - 1) {
            square++;
        } else {
            square = 0;
        }

    }
        arrLeft.onclick = function () {
            //如果pic等于第一张图片的索引 再点击就该跳转到最后了
            if (pic == 0) {
                //往左移动了ulLis.length - 1张
                //也就是往左移动了(ulLis.length - 1) * imgWidth
                ul.style.left = -(ulLis.length - 1) * imgWidth + "px";
                pic = ulLis.length - 1;
            }
            pic--;
            //目标 和 pic有关 和 图片宽度有关 而且是 负数
            var target = -pic * imgWidth;
            animate(ul, target);

            //按钮也跟着走
            //square 表示当前应该亮起的按钮
            //如果大于第一个按钮的索引就减 否则就该等于最后一个按钮的索引
            if (square > 0) {
                square--;
            } else {
                square = olLis.length - 1;
            }

            //按钮排他
            //干掉所有人
            for (var i = 0; i < olLis.length; i++) {
                olLis[i].className = "";
            }
            //留下当前的
            olLis[square].className = "current";

        }

        function animate(obj, target) {
            clearInterval(obj.timer);
            obj.timer = setInterval(function () {
                var leader = obj.offsetLeft;
                var step = 35;
                step = leader < target ? step : -step;
                if (Math.abs(target - leader) > Math.abs(step)) {
                    leader = leader + step;
                    obj.style.left = leader + "px";
                } else {
                    obj.style.left = target + "px";
                    clearInterval(obj.timer);
                }
            }, 15)
        }

    hotSwitchHover();
    switchHover();
});