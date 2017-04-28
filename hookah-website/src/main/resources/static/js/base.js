$(document).ready(function () {
	var pathname = window.location.pathname;
	if (pathname.indexOf('/exchange') >= 0) {
		$('#exchange_menu').show();
		$(".header-bottom-bar ul li").css("width","14.28%");
	} else {
		$('#exchange_menu').hide();
	}

	$('.header-bottom-bar ul li').each(function () {
		var elementVal = $(this).children('a').attr('href');
		if (pathname.indexOf(elementVal) >= 0) {
			$(this).addClass('active').siblings()
		}
		if (pathname == '/') {
			$('#menu_index').addClass('active');
		} else {
			$('#menu_index').removeClass('active');
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
			if ($('.search-sug li').length <= 0){
				getSearchSug($(this).val())
			}else{
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
			if(window.location.pathname == '/exchange/search'){
				window.location.href = host.website+'/exchange/search?names=' + $(this).val();
			}else{
				window.open(host.website+'/exchange/search?names=' + $(this).val());
			}
		}
	});
	$('#J_searchBtn').click(function(){
		if($("#J_searchInput").val().length > 0){
			$("#J_searchInput").blur();
			if(window.location.pathname == '/exchange/search'){
				window.location.href = host.website+'/exchange/search?names=' + $("#J_searchInput").val();
			}else{
				window.open(host.website+'/exchange/search?names=' + $("#J_searchInput").val());
			}
			return;
		}
	});
	var ds = -1;
	$(document).keydown(function (event) {
		if ($('.search-sug').hasClass('keyActive')) {
			if($('.search-sug li').length <= 0){
				$('.search-sug').hide();
			}
			if(!$("#J_searchInput").val()){
				$("#J_searchInput").focus();
				return;
			}
			var itemLen = $('.search-sug li').length - 1;
			if (event.keyCode == 40) {
				event.preventDefault();
				ds +=1;
				if(ds > itemLen){
					ds = itemLen;
				}
				$('.search-sug li').eq(ds).addClass('current').siblings().removeClass('current')
			} else if (event.keyCode == 38) {
				ds -=1;
				if(ds <= -1){
					ds = -1;
					$('.search-sug li').removeClass('current');
					$("#J_searchInput").focus();
					return;
				}
				$('.search-sug li').eq(ds).addClass('current').siblings().removeClass('current')
			} else if (event.keyCode == 13) {
				$('.search-sug li').each(function(){
					if($(this).hasClass('current')){
						$("#J_searchInput").blur();
						window.open($(this).children('a').attr('href'));
						return;
					}
				})
			}else{
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
	$('img').each(function(){
		var img = $(this);
		// img.on('load',function(){
		// 	console.log('111111---------'+img.attr('src'))
		// })
		img.on('error',function(){
			img.attr('src','/static/images/timg.jpeg')
		})
	})
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

    // if($(".shopping-cart .shopping-cart-down").height()>130){
    //    $(".shopping-cart .shopping-cart-down").css({
    //    	'overflow-y':'scroll'
    // 	})
    // }
}
function compatibilityPL() {
    if ((navigator.appName == "Microsoft Internet Explorer") && (document.documentMode < 10 || document.documentMode == undefined)) {
        var $placeholder = $("input[placeholder]");
        $placeholder.val($placeholder.attr("placeholder")).css({"color": "#ccc"});
        $placeholder.focus(function () {
            if ($(this).val() == $(this).attr("placeholder")) {$(this).val("").css({"color": "#333"})}
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
        html += '<li><a target="_blank" href="'+host.website+'/exchange/search?names=' + list + '">' + list + '</a></li>';
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

var Attention={};
Attention.add=function(goodsId){
    $.ajax({
        url:'/goodsFavorite/add',
        type:'get',
        data:{
            goodsId:goodsId
        },
        success:function(data){
            if(data.code=="1"){
                $.alert("您已关注该商品")
                $('.attention span').html('已关注');
            }else{
                console.log(data.message);
            }
        }
    })
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