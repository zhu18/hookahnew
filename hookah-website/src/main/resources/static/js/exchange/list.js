function loadPageData(data){ //渲染页面数据
	var currentPage = data.data.currentPage;
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
			renderSelector(data.data2.categoryList,'分类','category');
		}
		if(data.data2.goodsAttrTypeList){
			renderSelector2(data.data2.goodsAttrTypeList,'属性','attrType');
		}
		if(data.data2.areaCountryList){
			renderSelector(data.data2.areaCountryList,'国家','country');
		}
		if(data.data2.areaProvinceList){
			renderSelector(data.data2.areaProvinceList,'省份','province');
		}
		if(data.data2.areaCityList){
			renderSelector(data.data2.areaCityList,'城市','city');
		}
		function renderSelector(datas,name,fnName){
			html += '<li class="parLi">';
			html += '<span>'+name+'：</span>';
			html += '<ul>';
			datas.forEach(function(item){
				html += '<li><a href="javascript:selectCategory('+item.nodeId+',\''+fnName+'\',\''+item.nodeName+'\')">'+item.nodeName+'</a></li>';
			});
			html += '</ul>';
			html += '</li>';
		}
		function renderSelector2(datas,name,fnName){
			html += '<li class="parLi">';
			html += '<span>'+name+'：</span>';
			html += '<ul>';
			datas.forEach(function(item){
				html += '<li class="parLi">';
				html += '<span>'+item.nodeName+'：</span>';
				html += '<ul>';
				item.children.forEach(function(items){
					html += '<li><a href="javascript:selectCategory('+items.nodeId+',\''+fnName+'\','+items.nodeName+')">'+items.nodeName+'</a></li>';
				});
				html += '</ul>';
				html += '</li>';
				html +='</li>';
			});
			html += '</ul>';
			html += '</li>';
		}

		html += '</ul>';
		$('#J_searchCategory').html(html)
	}

	$('img').each(function(){
		$(this).on('error',function(){
			$(this).attr('src','/static/images/timg.jpeg')
		})
	})

}

function selectCategory(id,fnName,name){
	if(fnName == 'category'){
		$('#J_crimbsNav').attr('category',true).append('<span class="tags">'+name+'</span>');

	}else if(fnName == 'attrType'){
		console.log(123123123);
	}else if(fnName == 'country'){

	}else if(fnName == 'province'){

	}else if(fnName == 'city'){

	}
}


if(prId){//渲染分类
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




