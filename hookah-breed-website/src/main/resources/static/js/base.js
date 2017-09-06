$(document).ready(function () {
	var pathname = window.location.pathname;
	if (pathname.indexOf('/exchange') >= 0) {
		$('#exchange_menu').show();
		$(".header-bottom-bar ul li").css("width", "14.28%");
	} else {
		$('#exchange_menu').hide();
	}

	$('.header-bottom-bar ul li').each(function () {
		var elementVal = $(this).children('a').attr('href');
		if (pathname.indexOf(elementVal) >= 0) {
			$(this).addClass('active');
		}
		if (pathname == '/' || pathname == '') {
			$('#menu_index').addClass('active');
		} else {
			$('#menu_index').removeClass('active');
		}
		if(pathname.indexOf('/exchange/list') >= 0 || pathname.indexOf('/exchange/details') >= 0){
			$('.header-bottom-bar ul li:nth-child(3)').addClass('active');
		}
		if(pathname.indexOf('/information/details') >= 0){
			$('.header-bottom-bar ul li:nth-child(6)').addClass('active');
		}
	});

	$("#J_searchInput").on("input propertychange", function () {
		if ($(this).val() == "") {
			$('.search-sug').hide();
		} else {
			getSearchSug($(this).val())
		}
	});
	$("#J_searchInput").on("input focus", function () {
		if ($(this).val()) {
			if ($('.search-sug li').length <= 0) {
				getSearchSug($(this).val())
			} else {
				$('.search-sug').show();
			}
		}
	});
	$("#J_searchInput").keydown(function (event) {
		if (event.keyCode == 40) {
			$(this).blur();
			$('.search-sug').show().addClass('keyActive');
		}
		if (event.keyCode == 13) {
			// $("#J_searchInput").blur();
			if (window.location.pathname == '/exchange/search') {
				window.location.href = host.website + '/exchange/search?names=' + $(this).val();
			} else {
				window.open(host.website + '/exchange/search?names=' + $(this).val());
			}
		}
	});
	$('#J_searchBtn').click(function () {
		if ($("#J_searchInput").val().length > 0) {
			$("#J_searchInput").blur();
			if (window.location.pathname == '/exchange/search') {
				window.location.href = host.website + '/exchange/search?names=' + $("#J_searchInput").val();
			} else {
				window.open(host.website + '/exchange/search?names=' + $("#J_searchInput").val());
			}
			return;
		}
	});
	var ds = -1;
	$(document).keydown(function (event) {
		if ($('.search-sug').hasClass('keyActive')) {
			if ($('.search-sug li').length <= 0) {
				$('.search-sug').hide();
			}
			if (!$("#J_searchInput").val()) {
				$("#J_searchInput").focus();
				return;
			}
			var itemLen = $('.search-sug li').length - 1;
			if (event.keyCode == 40) {
				event.preventDefault();
				ds += 1;
				if (ds > itemLen) {
					ds = itemLen;
				}
				$('.search-sug li').eq(ds).addClass('current').siblings().removeClass('current')
			} else if (event.keyCode == 38) {
				ds -= 1;
				if (ds <= -1) {
					ds = -1;
					$('.search-sug li').removeClass('current');
					$("#J_searchInput").focus();
					return;
				}
				$('.search-sug li').eq(ds).addClass('current').siblings().removeClass('current')
			} else if (event.keyCode == 13) {
				$('.search-sug li').each(function () {
					if ($(this).hasClass('current')) {
						$("#J_searchInput").blur();
						window.open($(this).children('a').attr('href'));
						return;
					}
				})
			} else {
				event.initEvent("keydown", true, true);
			}
		}
	});
	$("#J_searchInput").on("input blur", function () {
		if (!$('.search-sug').hasClass('active')) {
			$('.search-sug').hide();
		}
	});
	$('.search-sug').on('click', function () {
		$("#J_searchInput").focus();
	});
	$('.search-sug').hover(function () {
		$(this).toggleClass('active');
	});
	// $('img').each(function () { //替换加载异常图片
	// 	var img = $(this);
	// 	// img.on('load',function(){
	// 	// 	console.log('111111---------'+img.attr('src'))
	// 	// })
	// 	img.on('error', function () {
	// 		img.attr('src', '/static/images/timg.jpeg')
	// 	})
	// });
	// 兼容性placeholder
	compatibilityPL();
	shoppingCart();

});
function shoppingCart() {
	$(".shopping-cart").mouseenter(function () {
		$(this).addClass('hover')
	});
	$(".shopping-cart").mouseleave(function () {
		$(this).removeClass('hover')
	});
	if ($(".shopping-cart .shopping-cart-down").height() > 430) {
		$(".shopping-cart .shopping-cart-down").css({
			'overflow-y': 'scroll',
			'height': '430px'
		})
	}
}
function compatibilityPL() {
	if ((navigator.appName == "Microsoft Internet Explorer") && (document.documentMode < 10 || document.documentMode == undefined)) {
		var $placeholder = $("input[placeholder]");
		$placeholder.val($placeholder.attr("placeholder")).css({"color": "#ccc"});
		$placeholder.focus(function () {
			if ($(this).val() == $(this).attr("placeholder")) {
				$(this).val("").css({"color": "#333"})
			}
		}).blur(function () {
			if ($(this).val() == "") {
				$(this).val($(this).attr("placeholder")).css({"color": "#ccc"})
			}
		});
	}
}
function getSearchSug(sugText) {
	$.ajax({
		url: host.website + '/search/v1/goods/suggest',
		type: 'post',
		data: {
			prefix: sugText,
			size: 10
		},
		success: function (data) {
			if (data.code == 1) {
				showSugBox(data.data);
			} else {
				console.log(data.message);
			}
		}
	})
}
function showSugBox(data) {
	var html = '';
	data.forEach(function (list) {
		html += '<li><a target="_blank" href="' + host.website + '/exchange/search?names=' + list + '">' + list + '</a></li>';
	});
	$('.search-sug').show().children('ul').html(html);
}

var Loading = {};
Loading.start = function () {
	Loading.stop();
	var loadingHtml = '';
	loadingHtml += '<div class="loading-wrapper">';
	loadingHtml += '<div class="loading-bg">';
	loadingHtml += '</div>';
	loadingHtml += '<div class="spinner">';
	loadingHtml += '<div class="rect1"></div>';
	loadingHtml += '<div class="rect2"></div>';
	loadingHtml += '<div class="rect3"></div>';
	loadingHtml += '<div class="rect4"></div>';
	loadingHtml += '<div class="rect5"></div>';
	loadingHtml += '<div class="rect6"></div>';
	loadingHtml += '</div>';
	loadingHtml += '</div>';
	$('body').append(loadingHtml);
};
Loading.stop = function () {
	$('.loading-wrapper').remove();
};

//商品关注
var Attention = {};
Attention.add = function (goodsId) {
	$.ajax({
		url: '/islogin',
		type: 'post',
		success: function (data) {
			if (data) {
				$.ajax({
					url: '/goodsFavorite/add',
					type: 'get',
                    cache:false,
					data: {
						goodsId: goodsId
					},
					success: function (data) {
						if (data.code == "1") {
							$('.attention .iconfont').css('color', '#e34f4f');
							$('.attention .eval').html('已关注:');
							var nums = parseInt($('#J_atonNum').html());
							$('#J_atonNum').html(nums+1);
							$('#J_attenBtn').removeAttr('onclick').css('cursor','default');
							$.alert("关注成功");
						} else {

						}
					}
				})
			} else {
				window.location.href = host.loginUrl + window.location.href
			}
		}
	});

}

function add(m) {
	return m < 10 ? '0' + m : m
};
function format(time) {
    // var a = parseInt(time);
    // var date = new Date(a);
    var date = new Date(time);
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var date1 = date.getDate();
	var hours = date.getHours();
	var minutes = date.getMinutes();
	var seconds = date.getSeconds();
	return year + '-' + add(month) + '-' + add(date1) + ' ' + add(hours) + ':' + add(minutes) + ':' + add(seconds);
};
$.getUrlParam = function (key) { //获取url参数值  使用方法var id = $.getUrlParam('id');
	var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
	var result = window.location.search.substr(1).match(reg);
	return result ? decodeURIComponent(result[2]) : null;
};
// 转换金额形式
function splitK(num) {
    var decimal = String(num).split('.')[1] || '';//小数部分
    var tempArr = [];
    var revNumArr = String(num).split('.')[0].split("").reverse();//倒序
    for (i in revNumArr){
        tempArr.push(revNumArr[i]);
        if((i+1)%3 === 0 && i != revNumArr.length-1){
            tempArr.push(',');
        }
    }
    var zs = tempArr.reverse().join('');//整数部分
    return decimal?zs+'.'+decimal:zs;
}

function Transformation(price,quotaPrice) {
    if( (parseInt(price) /100) >parseInt(quotaPrice)){
        var price=price.toString().slice(0,-6);
        return parseInt(price)+'万';
    }else {
        return (price / 100 ).toFixed(2);
    }
}

function judgeTime(obj) {//判断时间范围

    var strb = obj.beginTime.split (":");
    if (strb.length != 2) {
        return false;
    }

    var stre = obj.endTime.split (":");
    if (stre.length != 2) {
        return false;
    }

    var strn = obj.nowTime.split (":");
    if (stre.length != 2) {
        return false;
    }
    var b = new Date ();
    var e = new Date ();
    var n = new Date ();

    b.setHours (strb[0]);
    b.setMinutes (strb[1]);
    e.setHours (stre[0]);
    e.setMinutes (stre[1]);
    n.setHours (strn[0]);
    n.setMinutes (strn[1]);

    if (n.getTime () - b.getTime () > 0 && n.getTime () - e.getTime () < 0) {
        obj.callback()
    } else {
        $.alert({
            content:obj.info
        })
    }
}
