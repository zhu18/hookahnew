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
		if(data.data2.categoryList.length > 0){
			renderSelector(data.data2.categoryList,'分类','category');
		}
		if(data.data2.goodsAttrTypeList.length > 0){
			renderSelector2(data.data2.goodsAttrTypeList,'属性','attrType');
		}
		if(data.data2.areaCountryList.length > 0){
			renderSelector(data.data2.areaCountryList,'国家','country');
		}
		if(data.data2.areaProvinceList.length > 0){
			renderSelector(data.data2.areaProvinceList,'省份','province');
		}
		if(data.data2.areaCityList.length > 0){
			renderSelector(data.data2.areaCityList,'城市','city');
		}
		function renderSelector(datas,name,fnName){
			html += '<li class="parLi">';
			html += '<span>'+name+'：</span>';
			html += '<ul>';
			datas.forEach(function(item){
				html += '<li class="op_i '+fnName+'" typeid="'+item.nodeId+'"><a href="javascript:;" onclick="selectCategory(this,'+item.nodeId+',\''+fnName+'\',\''+item.nodeName+'\')">'+item.nodeName+'</a></li>';
			});
			html += '</ul>';
			html += '</li>';
		}
		function renderSelector2(datas,name,fnName){
			html += '<li class="parLi">';

			datas.forEach(function(item){
				html += '<li class="parLi">';
				html += '<span>'+item.nodeName+'：</span>';
				html += '<ul typeid ='+item.nodeId+'>';
				item.children.forEach(function(items){
					html += '<li class="op_i '+fnName+'" typeid="'+items.nodeId+'"><a href="javascript:;" onclick="selectCategory(this,'+items.nodeId+',\''+fnName+'\',\''+items.nodeName+'\')">'+items.nodeName+'</a></li>';
				});
				html += '</ul>';
				html += '</li>';
				html +='</li>';
			});
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

function selectCategory(that,id,fnName,name){
	if($('#J_crimbsNav').attr(fnName) == name){
		return;
	}else{
		var parName = $(that).parents('ul').siblings('span').text();
		$('.tags').each(function(){
			if($(this).attr(fnName)){
				$(this).remove();
			}
		});
		$('#J_crimbsNav').attr(fnName,name).attr(fnName+'id',id).append('<span class="tags" '+fnName+'='+name+' '+fnName+'id='+id+' type='+fnName+'>'+parName+name+'<a href="JavaScript:;" onclick="removeTag(this)" class="fa fa-close"></a></span>');
		$(that).parent('.op_i').addClass('active').siblings().removeClass('active');
	}
	getDataForin();

}
function removeTag(that){
	var ats = $(that).attr('type');
	$('#J_crimbsNav').removeAttr(ats);
	$(that).parent('.tags').remove();
	getDataForin();
}
function getDataForin(){
	dataParm.esGoods.catIds = '';
	dataParm.esGoods.attrIds = '';
	dataParm.esGoods.goodsAreas = '';
	if($('#J_crimbsNav').attr('category')){
		dataParm.esGoods.catIds = $('#J_crimbsNav').attr('categoryid');
	}
	if($('#J_crimbsNav').attr('attrType')){
	}
	if($('#J_crimbsNav').attr('country')){

	}
	if($('#J_crimbsNav').attr('province')){

	}
	if($('#J_crimbsNav').attr('city')){

	}
	goPage('1')
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




