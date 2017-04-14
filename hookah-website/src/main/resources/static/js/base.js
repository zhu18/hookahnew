$(document).ready(function () {
	var pathname = window.location.pathname
	if (pathname.indexOf('/exchange') >= 0) {
		$('#exchange_menu').show();
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

	$(".shopping-cart").mouseenter(function () {
		$(this).addClass('hover')
	});
	$(".shopping-cart").mouseleave(function () {
		$(this).removeClass('hover')
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
		$(this).keydown(function (event) {
			if (event.keyCode == 40) {
				$(this).blur();
				$('.search-sug').show().addClass('keyActive');
			} else if (event.keyCode == 13) {
				$("#J_searchInput").blur();
				window.open(host.website+'/exchange/search?names=' + $(this).val());
				return;
			}
		});
	});
	$('#J_searchBtn').click(function(){
		$("#J_searchInput").blur();
		window.open(host.website+'/exchange/search?names=' + $("#J_searchInput").val());
		return;
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
});
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
