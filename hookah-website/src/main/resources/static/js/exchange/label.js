function loadPageData(data) { //渲染页面数据
	var currentPage = data.data.currentPage;
	if (data.data.list.length > 0) {
		$('#J_searchCategory').before('');
		var list = data.data.list;
		var html = '';
		for (var i = 0; i < list.length; i++) {
			var shopFormat = '';
			if (list[i].shopFormat == 0) {
				shopFormat = '次';
			} else if (list[i].shopFormat == 1) {
				shopFormat = '天';
			} else if (list[i].shopFormat == 2) {
				shopFormat = '年';
			} else if (list[i].shopFormat == 3) {
				shopFormat = '套';
			}
			var shopPrice = null;
			if (Number(list[i].shopPrice) >= 1000000) {
				shopPrice = (Number(list[i].shopPrice) / 1000000) + '万';
			} else {
				shopPrice = Number(list[i].shopPrice) / 100
			}
			html += '<li>';
			html += '<a target="_blank" class="item-top" href="/exchange/details?id=' + list[i].goodsId + '">';
			html += '<p class="goods-img"><img src="' + list[i].goodsImg + '" alt=""/></p>';
			html += '<p class="goods-name">' + list[i].goodsName + '</p>';
			html += '<p class="goods-brief">' + (list[i].goodsBrief ? list[i].goodsBrief : '暂无简介') + '</p>';
			html += '</a>';
			html += '<div class="item-down clearfix">';
			if (list[i].isDiscussPrice == 1) {
				// html += '<span class="grid-left goods-price">面议参考价￥<span>'+shopPrice+'</span></span>';
				html += '<span class="grid-left goods-price">价格：<span>面议</span></span>';
			} else {
				html += '<span class="grid-left goods-price">￥<span>' + shopPrice + '</span>/' + (list[i].shopNumber == 1 ? '' : list[i].shopNumber) + shopFormat + '</span>';
			}
			html += '<a class="grid-right goods-cart btn btn-full-red padding-5" href="/exchange/details?id=' + list[i].goodsId + '" target="_blank">查看详情</a>';
			html += '</div>';
			html += '</li>';
		}
		$('.order-list ul').html(html);
	} else {
		$('.order-list ul').html('<div class="noData">暂无数据</div>');
	}
	if (data.data2) { //渲染分类数据
		var html = '';
		html += '<div class="crumbs-nav margin-top-20 padding-bottom-10" id="J_crimbsNav">分类：';
		data.data2.forEach(function (item) {
			html += '<span class="labelSpan">' + item.labName + '</span>';
		});
		html += '<span></span>';
		html += '</div>';
		$('#J_searchCategory').before(html)
	} else {
		$('#J_searchCategory').addClass('margin-top-20')
	}

	if (!$('#J_searchCategory ul').html()) { //渲染数据为空隐藏边框
		$('#J_searchCategory ul').css('border', 'none')
	}
}
