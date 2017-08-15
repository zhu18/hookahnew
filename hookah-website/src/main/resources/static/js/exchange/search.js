var categoryCode = null;
function loadPageData(data){ //渲染页面数据
	if(data.data.list.length>0){
		var list = data.data.list;
		var html = '';
		for(var i=0; i<list.length; i++){
			var shopFormat = '';
			if(list[i].shopFormat == 0 ){
				shopFormat = '次';
			}else if(list[i].shopFormat == 1 ){
				shopFormat = '天';
			}else if(list[i].shopFormat == 2 ){
				shopFormat = '年';
			}else if(list[i].shopFormat == 3 ){
				shopFormat = '套';
			}
			var shopPrice = null;
			if(Number(list[i].shopPrice) >= 1000000){
				shopPrice = (Number(list[i].shopPrice) / 1000000)+'万';
			}else{
				shopPrice = Number(list[i].shopPrice) / 100
			}
			html += '<li>';
			html += '<a class="item-top" target="_blank" href="/exchange/details?id='+list[i].goodsId+'">';
			html += '<p class="goods-img"><img src="'+host.static+'/'+list[i].goodsImg+'" alt=""/></p>';
			html += '<p class="goods-name">'+list[i].goodsName+'</p>';
			html += '<p class="goods-brief">'+(list[i].goodsBrief  ? list[i].goodsBrief : '暂无简介')+'</p>';
			html += '</a>';
			html += '<div class="item-down clearfix">';
			// html += '<span class="grid-left goods-price">￥<span>'+shopPrice+'</span>/'+(list[i].shopNumber == 1 ? '':list[i].shopNumber)+shopFormat+'</span>';
			if(list[i].isDiscussPrice == 1){
				html += '<span class="grid-left goods-price">面议参考价￥<span>'+shopPrice+'</span></span>';
			}else{
				html += '<span class="grid-left goods-price">￥<span>'+shopPrice+'</span>/'+(list[i].shopNumber == 1 ? '':list[i].shopNumber)+shopFormat+'</span>';
			}
			html += '<a class="grid-right goods-cart btn btn-full-red padding-5 font-size-12 margin-top-10" target="_blank" href="/exchange/details?id='+list[i].goodsId+'">查看详情</a>';
			html += '</div>';
			html += '</li>';
		}
		$('.order-list ul').html(html);
	}else {
		$('.order-list ul').html('<div class="noData">暂无数据</div>');
	}
	if(data.data2){ //渲染分类数据
		var html = '';
		html += '<ul class="conditionCon">';
		if(data.data2.categoryList.length > 0){
			renderSelectorO(data.data2.categoryList,'分类','category');
		}
		if(data.data2.areaProvinceList.length > 0){
			renderSelector(data.data2.areaProvinceList,'省份','province');
		}
		if(data.data2.areaCityList.length > 0){
			renderSelector(data.data2.areaCityList,'城市','city');
		}
		if(data.data2.payFormatList.length > 0){
			renderSelector(data.data2.payFormatList,'付费方式','payFormatList');
		}
		function renderSelector(datas,name,fnName){
			html += '<li class="parLi '+fnName+'">';
			html += '<span>'+name+'：</span>';
			html += '<ul>';
			datas.forEach(function(item){
				html += '<li class="op_i '+fnName+'" typeid="'+item.nodeId+'"><a href="javascript:;" onclick="selectCategory(this,\''+item.nodeId+'\',\''+fnName+'\',\''+item.nodeName+'\')">'+item.nodeName+'</a></li>';
			});
			html += '</ul>';
			html += '</li>';
		}
		function renderSelectorO(datas,name,fnName){
			var child2 = null;
			var child3 = null;

			html += '<li class="parLi category">';
			html += '<span>'+name+'：</span>';
			html += '<ol style="overflow: hidden">';

			if(datas){
				html += '<li><ul>';
				var categoryIds = null;
				if(categoryCode && categoryCode.length > 3){
					categoryIds = categoryCode.substring(0,3);
				}else{
					categoryIds = categoryCode;
				}
				datas.forEach(function(item){
					if(categoryIds == item.nodeId){
						child2 = item.children;
						html += '<li class="op_i current '+fnName+'" typeid="'+item.nodeId+'"><a href="javascript:;">'+item.nodeName+'</a></li>';
					}else {
						html += '<li class="op_i ' + fnName + '" typeid="' + item.nodeId + '"><a href="javascript:;" onclick="selectCategory(this,\'' + item.nodeId + '\',\'' + fnName + '\',\'' + item.nodeName + '\')">' + item.nodeName + '</a></li>';
					}
				});
				html += '</ul></li>';
			}

			if(child2){
				html += '<li><ul>';
				child2.forEach(function(item){
					var categoryIds = null;
					if(categoryCode && categoryCode.length >= 6){
						categoryIds = categoryCode.substring(0,6);
					}else{
						categoryIds = categoryCode;
					}
					if(categoryIds == item.nodeId){
						child3 = item.children;
						html += '<li class="op_i current '+fnName+'" typeid="'+item.nodeId+'"><a href="javascript:;">'+item.nodeName+'</a></li>';
					}else {
						html += '<li class="op_i ' + fnName + '" typeid="' + item.nodeId + '"><a href="javascript:;" onclick="selectCategory(this,\'' + item.nodeId + '\',\'' + fnName + '\',\'' + item.nodeName + '\')">' + item.nodeName + '</a></li>';
					}
				});
				html += '</ul></li>';
			}
			if(child3){
				html += '<li><ul>';
				child3.forEach(function(item){
					if(categoryCode == item.nodeId){
						html += '<li class="op_i current '+fnName+'" typeid="'+item.nodeId+'"><a href="javascript:;">'+item.nodeName+'</a></li>';
					}else {
						html += '<li class="op_i ' + fnName + '" typeid="' + item.nodeId + '"><a href="javascript:;" onclick="selectCategory(this,\'' + item.nodeId + '\',\'' + fnName + '\',\'' + item.nodeName + '\')">' + item.nodeName + '</a></li>';
					}
				});
				html += '</ul></li>';
			}
			html += '</ol>';
			html += '</li>';
		}


		html += '</ul>';
		$('#J_searchCategory').html(html);
		var country = $('#J_crimbsNav').attr('country');
		var province = $('#J_crimbsNav').attr('province');
		var city = $('#J_crimbsNav').attr('city');

		if(province && !city){
			$('.province').remove();
		}else if(!province){
			$('.city').remove();
		}else if(city){
			$('.province').remove();
			$('.city').remove();
		}else{
			$('.city').remove();
		}
	}
}
function selectCategory(that,id,fnName,name){
	categoryCode = id;
	dataParm.esGoods.catIds = id;
	if($('#J_crimbsNav').attr(fnName) == name){
		return;
	}else{
		var parName = $(that).parents('ul').siblings('span').text();
		$('.tags').each(function(){
			if($(this).attr(fnName)){
				$(this).remove();
			}
		});
		$('#J_crimbsNav').attr(fnName,name).attr(fnName+'id',id).append('<span class="tags" '+fnName+'='+name+' '+fnName+'='+id+' type='+fnName+' endType="attrType">'+name+'<a href="JavaScript:;" onclick="removeTag(this)" class="fa fa-close"></a></span>');
		// $(that).parent('.op_i').addClass('active').siblings().removeClass('active');
	}
	getDataForin();
}
function getDataForin(){
	dataParm.esGoods.catIds = '';
	dataParm.esGoods.goodsAreas = '';
	dataParm.esGoods.keywordsArrays = '';
	dataParm.esGoods.payFormats = '';
	if($('#J_crimbsNav').attr('category')){
		categoryCode = $('#J_crimbsNav').attr('categoryid')
		dataParm.esGoods.catIds = $('#J_crimbsNav').attr('categoryid');
	}
	if($('#J_crimbsNav').attr('country')){
		dataParm.esGoods.goodsAreas = $('#J_crimbsNav').attr('countryid');
	}
	if($('#J_crimbsNav').attr('province')){
		dataParm.esGoods.goodsAreas = $('#J_crimbsNav').attr('provinceid');
	}
	if($('#J_crimbsNav').attr('city')){
		dataParm.esGoods.goodsAreas = $('#J_crimbsNav').attr('cityid');
	}
	if($('#J_crimbsNav').attr('keywordsList')){
		dataParm.esGoods.keywordsArrays = $('#J_crimbsNav').attr('keywordsListid');
	}
	if($('#J_crimbsNav').attr('payFormatList')){
		dataParm.esGoods.payFormats = $('#J_crimbsNav').attr('payFormatListid');
	}
	goPage('1')
}
function removeTag(that){
	var ats = $(that).parent('.tags').attr('type');
	// console.log(ats);
	$('#J_crimbsNav').removeAttr(ats);
	$(that).parent('.tags').remove();
	getDataForin();
}
$('#J_searchInput').val(names);

