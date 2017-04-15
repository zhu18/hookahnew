function loadPageData(data){ //渲染页面数据
	if(data.data.list){
        var list = data.data.list;
        var html = '';
        for(var i=0; i<list.length; i++){
            var shopFormat = '';
            if(list[i].shopFormat == 0 ){
                shopFormat = '次';
            }else if(list[i].shopFormat == 1 ){
                shopFormat = '月';
            }else if(list[i].shopFormat == 2 ){
                shopFormat = '年';
            }
            html += '<li>';
            html += '<a class="item-top" href="/exchange/details?id='+list[i].goodsId+'">';
            html += '<p class="goods-img"><img src="'+list[i].goodsImg+'" alt=""/></p>';
            html += '<p class="goods-name" title="'+list[i].goodsName+'">'+list[i].goodsName+'</p>';
            html += '<p class="goods-brief" title="'+list[i].goodsBrief+'">'+list[i].goodsBrief+'</p>';
            html += '</a>';
            html += '<div class="item-down">';
            html += '<span class="grid-left goods-price"><span>'+Number(list[i].shopPrice)/100+'</span>/'+(list[i].shopNumber == 1 ? '':list[i].shopNumber)+shopFormat+'</span>';
            html += '<a class="grid-right" href="javascript:void(0)">加入购物车</a>';
            html += '</div>';
            html += '</li>';
        }
        $('.order-list ul').html(html);
    }
    if(data.data2){
		var html = '';
		html += '<ul class="conditionCon">';
		if(data.data2.categoryList){
			html += '<li>';
			html += '<span>分类：</span>';
			html += '<ul>';
			data.data2.categoryList.forEach(function(item){
				html += '<li><a href="javascript:void(0)">'+item.nodeName+'</a></li>';
			});
			html += '</ul>';
			html += '</li>';
		}
		if(data.data2.goodsAttrTypeList){
			html += '<li>';
			html += '<span>属性：</span>';
			html += '<ul>';
			data.data2.goodsAttrTypeList.forEach(function(item){
				html += '<li><a href="javascript:void(0)">'+item.nodeName+'</a></li>';
			});
			html += '</ul>';
			html += '</li>';
		}
		if(data.data2.areaCountryList){
			html += '<li>';
			html += '<span>国家：</span>';
			html += '<ul>';
			data.data2.areaCountryList.forEach(function(item){
				html += '<li><a href="javascript:void(0)">'+item.nodeName+'</a></li>';
			});
			html += '</ul>';
			html += '</li>';
		}
		if(data.data2.areaProvinceList){
			html += '<li>';
			html += '<span>省份：</span>';
			html += '<ul>';
			data.data2.areaProvinceList.forEach(function(item){
				html += '<li><a href="javascript:void(0)">'+item.nodeName+'</a></li>';
			});
			html += '</ul>';
			html += '</li>';
		}
		if(data.data2.areaCityList){
			html += '<li>';
			html += '<span>城市：</span>';
			html += '<ul>';
			data.data2.areaCityList.forEach(function(item){
				html += '<li><a href="javascript:void(0)">'+item.nodeName+'</a></li>';
			});
			html += '</ul>';
			html += '</li>';
		}
		html += '</ul>';
		$('#J_searchCategory').html(html)
	}
}



html += '<li>';
html += '<span>地区：</span>';
html += '<ul>';
html += '<li><a href="javascript:void(0)">北京市</a></li>';
html += '<li><a href="javascript:void(0)">广东省</a></li>';
html += '</ul>';
html += '</li>';
html += '<li>';
html += '<span>付费方式：</span>';
html += '<ul>';
html += '<li><a href="javascript:void(0)">数据源</a></li>';
html += '<li><a href="javascript:void(0)">大数据应用场景</a></li>';
html += '<li><a href="javascript:void(0)">大数据模型</a></li>';
html += '<li><a href="javascript:void(0)">大数据开发工具</a></li>';
html += '</ul>';
html += '</li>';
html += '<li>';
html += '<span>数据格式：</span>';
html += '<ul>';
html += '<li><a href="javascript:void(0)">数据源</a></li>';
html += '<li><a href="javascript:void(0)">大数据应用场景</a></li>';
html += '<li><a href="javascript:void(0)">大数据模型</a></li>';
html += '<li><a href="javascript:void(0)">大数据开发工具</a></li>';
html += '</ul>';
html += '</li>';
html += '<li>';
html += '<span>数据大小：</span>';
html += '<ul>';
html += '<li><a href="javascript:void(0)">数据源</a></li>';
html += '<li><a href="javascript:void(0)">大数据应用场景</a></li>';
html += '<li><a href="javascript:void(0)">大数据模型</a></li>';
html += '<li><a href="javascript:void(0)">大数据开发工具</a></li>';
html += '</ul>';
html += '</li>';
html += '</ul>';








if(prId){
	var html = '';
	html += '<div class="crumbs-nav margin-top-20 padding-bottom-10" id="J_crimbsNav">';
	if(msId){
		html += '分类：<a href="/exchange/list?catId='+prId+'&prId='+prId+'&prNm='+prNm+'">'+prNm+'</a><span class="fa fa-angle-right margin-left-5 margin-right-5"></span><span class="color-blue">'+msNm+'</span></div>';
    }else{
		html += '分类：<span class="color-blue">'+prNm+'</span></div>';
    }
	$('#J_searchCategory').before(html)
}else{
	$('#J_searchCategory').addClass('margin-top-20')
}




