/**
 * Created by lss on 2017/4/7 0007.
 */
// 列表切换功能
function switchHover() {
    $(".goods-table .table-content .table-item").each(function () {
        if ($(this).hasClass('active')) {
            $(this).css({
                'display': 'block'
            }).siblings().css({
                'display': 'none'
            })
        }
    })
    $(".goods-table .table-title li").on('click', function () {
        $(this).addClass('active').siblings().removeClass('active');
        var index = $(this).index();
        $(".goods-table .table-content .table-item").each(function () {
            if (index == $(this).index()) {
                $(this).css({
                    'display': 'block'
                }).siblings().css({
                    'display': 'none'
                })
            }
        })
        if ($(document).scrollTop() >= 570) {
            $("body").animate({scrollTop: 570}, 300);
        }
    })
}
// 购买数量功能
function purchaseQuantity() {
    var conut = '';
    // 对输入框中的数据进行格式化
    $('.purchase-quantity input').blur(function () {
        var val = $(this).val();
        if (isNaN(val)) {
            $(this).val(1);
        } else if (val < 1 || val > 999) {
            $.alert('数量只能在1-999之间')
            $(this).val(1);
        }

    })
    $(".purchase-quantity .btn-sub").on('click', function () {
        var val = $('.purchase-quantity input').val();
        if (val == 1) {
            return;
        }
        val = val - 1;
        $('.purchase-quantity input').val(val);
    });
    $(".purchase-quantity .btn-plus").on('click', function () {
        var val = $('.purchase-quantity input').val();
        if (val == 999) {
            return;
        }
        val = parseInt(val) + 1;
        $('.purchase-quantity input').val(val);
    });
}

// 悬浮框
function suspensionBox() {
    var nav = $(".goods-table .table-title").children('ul'); //得到导航对象
    $(window).scroll(function () {
        if ($(document).scrollTop() >= 570) {
            nav.addClass('fiexd');
            nav.fadeIn();
        } else {
            nav.removeClass('fiexd');
        }
    })
}
suspensionBox();
switchHover();
purchaseQuantity();

$.getUrlParam = function (key) {
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
    var result = window.location.search.substr(1).match(reg);
    return result ? decodeURIComponent(result[2]) : null;
};
var id = $.getUrlParam('id');
renderDetails();
function renderDetails() {
    $.ajax({
        url: '/goods/back/findById',
        type: 'get',
        data: {
            id: id
        },
        success: function (data) {
            // return JSON.stringify(data);
            if (data.code == "1") {
                $('#J_goodsImg').attr('src', data.data.goodsImg);
                $('#J_goodsTitle').html(data.data.goodsName);
                $('#J_goodsBrief').html(data.data.goodsBrief);
                $('#J_goodsPrice').html(Number(data.data.formatList[0].price) / 100);

                function formatType(type) {
                    if (type == 0) {
                        return '次';
                    } else if (type == 1) {
                        return '月';
                    } else if (type == 2) {
                        return '年';
                    }
                }

                // $('#J_goodsNumber').html((data.data.shopNumber <= 1 ? '' : data.data.shopNumber)+formatType(data.data.shopFormat));
                $('#J_goodsDesc').html(data.data.goodsDesc);
                $('#J_addCart').attr('href', 'javascript:addCart("' + data.data.goodsId + '");')
                if (data.data.formatList[0].formatId == 0) {
                    var html = '';
                    var listData = data.data.formatList;
                    html += '';
                    html += '<div class="money-standard">';
                    html += '<span>规格：</span>';
                    $.each(listData, function (index, item) {
                        var active = '';
                        index == 0 ? active = 'active' : active = '';
                        html += '<a href="javascript:;" onclick="editPrice(this, ' + item.price + ')" class="margin-right-5 ' + active + '" formatid="' + item.formatId + '" price="' + item.price + '">' + item.number + formatType(item.format) + '</a>';
                    })
                    html += '</div>';
                    $("#J_detail-money").append(html)
                }

                function add(m) {
                    return m < 10 ? '0' + m : m
                };
                function format(time) {
                    var date = new Date(time);
                    var year = date.getFullYear();
                    var month = date.getMonth() + 1;
                    var date1 = date.getDate();
                    var hours = date.getHours();
                    var minutes = date.getMinutes();
                    var seconds = date.getSeconds();
                    return year + '-' + add(month) + '-' + add(date1) + ' ' + add(hours) + ':' + add(minutes) + ':' + add(seconds);
                };
            } else {
                console.log(data.message);
            }

        }
    });
}
// 加入购物车
function addCart(goodsId) {
    $.ajax({
        url: '/cart/add',
        type: 'post',
        data: {
            goodsId: goodsId,
            formatId: formatId,
            goodsNumber: $('#J_buyNumber').val()
        },
        success: function (data) {
            // return JSON.stringify(data);
            if (data.code == "1") {
                $.alert('加入购物车成功')
            } else {
                $.alert(data.message);
            }
        }
    });
}
function editPrice(that,price){
    $(that).addClass('active').siblings('a').removeClass('active');
    $('#J_goodsPrice').html(Number(price) / 100);
}