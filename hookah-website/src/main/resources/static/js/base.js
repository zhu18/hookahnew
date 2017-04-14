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
	$("#J_searchInput").on("input propertychange",function(){
		if($(this).val()==""){
			$('.search-sug').hide();
		}else{
			getSearchSug($(this).val())
		}
	});
	$("#J_searchInput").on("input focus",function(){
		if($(this).val()){
			$('.search-sug').show();
		}
	});
	// $("#J_searchInput").on("input change",function(){
	// 	$('.search-sug').hide();
	// })
});
function getSearchSug(sugText){
	$.ajax({
		url:host.website+'/search/v1/goods/suggest',
		type:'post',
		data:{
			prefix:sugText,
			size:10
		},
		success:function(data){
			if(data.code == 1){
				showSugBox(data.data);
			}else{
				console.log(data.message);
			}
		}
	})
}
function showSugBox(data){
	var html = '';
	data.forEach(function(list){
		html += '<li><a target="_blank" href="/exchange/search?names='+list+'">'+list+'</a></li>';
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
