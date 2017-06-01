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
        });
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
// renderDetails();
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
                // $('#J_goodsImg').attr('src', data.data.goodsImg);
                // $('#J_goodsTitle').html(data.data.goodsName);
                // $('#J_goodsBrief').html(data.data.goodsBrief);
                // $('#J_goodsPrice').html(Number(data.data.formatList[0].price) / 100).attr('formatid',data.data.formatList[0].formatId);

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
                        html += '<a href="javascript:;" onclick="editPrice(this, ' + item.price + ', ' + item.formatId + ')" class="margin-right-5 ' + active + '" formatid="' + item.formatId + '" price="' + item.price + '">' + item.number + formatType(item.format) + '</a>';
                    })
                    html += '</div>';
                    // $("#J_detail-money").append(html)
                }
            } else {
                console.log(data.message);
            }

        }
    });
}
// 加入购物车
function addCart(goodsId) {
    var formatname = null;
    $('.money-standard a').each(function(){
        if($(this).hasClass('active')){
			formatname = $(this).html();
        }
    });
    if(formatname){
		$.ajax({
			url: '/cart/add',
			type: 'post',
			data: {
				goodsId: goodsId,
				formatId: $('#J_goodsPrice').attr('formatid'),
				goodsNumber: $('#J_buyNumber').val()
			},
			success: function (data) {
				// return JSON.stringify(data);
				if (data.code == "1") {
					window.location.href = "/exchange/addToCart?goodsId=" + goodsId + "&number=" + $('#J_buyNumber').val() + '&fmt=' +formatname+'&gm='+$('#J_goodsPrice').html();
				} else {
					console.log(data);
					$.alert(data.message);
				}
			},
			error:function(e){
				if(e.status == 401){
					window.location.href = host.loginUrl+window.location.href
				}
			}
		});
    }else{
        $.alert('数据有误');
    }
}
function editPrice(that,price,formatId){
    $(that).addClass('active').siblings('a').removeClass('active');
    $('#J_goodsPrice').html((Number(price) / 100).toFixed(1)).attr('formatid',formatId);
	$('#J_formatId').val(formatId);
}

function check() {
	$.ajax({
		url: '/islogin',
		type: 'post',
		success: function (data) {
			if(data){
				var goodsId = $('#J_goodsId').val();
				var goodsNumber = $('#J_buyNumber').val();
				var formatId = $('#J_formatId').val();
				if (goodsId && goodsNumber && formatId) {
					return true;
				} else {
					$.alert('');
					return false;
				}
			}else{
				window.location.href = host.loginUrl + window.location.href
			}
		}
	});
	// return false;
}
function getDataPackage(goodsId){
    $.ajax({
        url: host.website+'/help/exportWords',
        type:'get',
		data:{
			goodsId:goodsId
        },
        success:function(data){
            if(data.code == 1){
                // window.location.href = data.data;
                window.location.href = data.data;
            }else{
                $.alert(data.message)
                // $.alert('下载失败')
            }
        }
    });
}
