function loadPageData(data){ //渲染页面数据
	var categoryCode = dataParm.esGoods.catIds;
	var currentPage = data.data.currentPage;
	if(data.data.list.length > 0){
        var list = data.data.list;
        var html = '';
        for(var i=0; i<list.length; i++){
            var shopFormat = '';
            var goodsTypeName = '';
            var goodsTypeAllName = '';
            var purchaseLimitName = '';
            var purchaseLimitAllName = '';
			if(list[i].shopFormat == 0 ){
				shopFormat = '次';
			}else if(list[i].shopFormat == 1 ){
				shopFormat = '天';
			}else if(list[i].shopFormat == 2 ){
				shopFormat = '年';
			}else if(list[i].shopFormat == 3 ){
				shopFormat = '套';
			}

			switch(list[i].purchaseLimit)
			{
				case 0:
					purchaseLimitName = '通用';
					purchaseLimitAllName = '所有用户可买';
					break;
				case 1:
					purchaseLimitName = '个人';
					purchaseLimitAllName = '仅限个人用户';
					break;
				case 2:
					purchaseLimitName = '企业';
					purchaseLimitAllName = '仅限企业用户';
					break;
			}
			switch(list[i].goodsType)
			{
				case 0:
					goodsTypeName = '离线';
					goodsTypeAllName = '离线数据源';
					break;
				case 1:
					goodsTypeName = 'API';
					goodsTypeAllName = 'API数据';
					break;
				case 2:
					goodsTypeName = '模型';
					goodsTypeAllName = '模型算法';
					break;
				case 4:
					goodsTypeName = '软件';
					goodsTypeAllName = '独立平台';
					break;
				case 5:
					goodsTypeName = 'SaaS';
					goodsTypeAllName = 'SaaS应用';
					break;
				case 6:
					goodsTypeName = '软件';
					goodsTypeAllName = '独立平台';
					break;
				case 7:
					goodsTypeName = 'SaaS';
					goodsTypeAllName = 'SaaS应用';
					break;
			}




			var shopPrice = null;
			if(Number(list[i].shopPrice) >= 1000000){
				shopPrice = (Number(list[i].shopPrice) / 1000000)+'万';
			}else{
				shopPrice = Number(list[i].shopPrice) / 100
			}
			html += '<li>';
			html += '<a target="_blank" class="item-top" href="'+list[i].goodsSn+'">';
			html += '<p class="goods-img"><img src="'+list[i].goodsImg+'" alt=""/></p>';
			html += '<p class="goods-name">'+list[i].goodsName+'</p>';
			html += '<p class="goods-tag"><span class="item-tag"><span class="tag-in"><span class="in-nol">'+goodsTypeAllName+'</span></span>'+goodsTypeName+'</span><span class="item-tag"><span class="tag-in"><span class="in-nol">'+purchaseLimitAllName+'</span></span>'+purchaseLimitName+'</span></p>';
			html += '</a>';
			html += '<div class="item-down clearfix">';
			if(list[i].isDiscussPrice == 1){
				// html += '<span class="grid-left goods-price">面议参考价￥<span>'+shopPrice+'</span></span>';
				html += '<span class="grid-left goods-price">价格：<span>面议</span></span>';
			}else{
				html += '<span class="grid-left goods-price">￥<span>'+shopPrice+'</span>/'+(list[i].shopNumber == 1 ? '':list[i].shopNumber)+shopFormat+'</span>';
			}
			html += '<a class="grid-right goods-cart btn-full-red" href="/exchange/details?id='+list[i].goodsId+'" target="_blank">查看详情</a>';
			html += '</div>';
			html += '</li>';
        }
        $('.order-list ul').html(html);
    }else{
		$('.order-list ul').html('<div class="noData"><p><img src="/static/images/no_data_img.png" alt=""></p><p>商品正在上架中，敬请期待！</p></div>');
	}
    if(data.data2){ //渲染分类数据
		var html = '';
		html += '<ul class="conditionCon">';
		if(data.data2.categoryList.length > 0){
			renderSelectorO(data.data2.categoryList,'分类','category');
		}

		// if(data.data2.areaCountryList.length > 0){
		// 	renderSelector(data.data2.areaCountryList,'国家','country');
		// }
		if(data.data2.areaProvinceList.length > 0){
			renderSelector(data.data2.areaProvinceList,'省份','province');

		}
		if(data.data2.areaCityList.length > 0){
			renderSelector(data.data2.areaCityList,'城市','city');
		}
		// if(data.data2.keywordsList.length > 0){
		// 	renderSelector(data.data2.keywordsList,'商品标签','keywordsList');
		// }
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
			html += '<li class="parLi category">';
			html += '<span>'+name+'：</span>';
			html += '<ol style="overflow: hidden">';
			html += '<li><ul>';
			datas.forEach(function(item){
				var categoryIds = null;
				if(categoryCode.length > 6){
					categoryIds = categoryCode.substring(0,6);
				}else{
					categoryIds = categoryCode;
				}
				if(categoryIds == item.nodeId){
					html += '<li class="op_i current '+fnName+'" typeid="'+item.nodeId+'"><a href="javascript:;">'+item.nodeName+'</a></li>';
				}else {
					html += '<li class="op_i ' + fnName + '" typeid="' + item.nodeId + '"><a href="javascript:;" onclick="selectCategory(this,' + item.nodeId + ',\'' + fnName + '\',\'' + item.nodeName + '\')">' + item.nodeName + '</a></li>';
				}
			});
			html += '</ul></li>';
			if(categoryCode.length >= 6){
				var categoryCodePreChild = null;
				datas.forEach(function(item){
					if(item.nodeId == categoryCode.substring(0,6)){
						categoryCodePreChild = item.children;
					}
				});
				if(categoryCodePreChild && categoryCodePreChild.length > 0){
					html += '<li><ul>';
					categoryCodePreChild.forEach(function(item){
						if(item.nodeId == categoryCode){
							html += '<li class="op_i current '+fnName+'" typeid="'+item.nodeId+'"><a href="javascript:;">'+item.nodeName+'</a></li>';
						}else {
							html += '<li class="op_i ' + fnName + '" typeid="' + item.nodeId + '"><a href="javascript:;" onclick="selectCategory(this,' + item.nodeId + ',\'' + fnName + '\',\'' + item.nodeName + '\')">' + item.nodeName + '</a></li>';
						}
					});
					html += '</ul></li>';
				}

			}
			html += '</ol>';
			html += '</li>';
		}
		function renderSelector2(datas,name,fnName){
			datas.forEach(function(item){
				html += '<li class="parLi">';
				html += '<span>'+item.nodeName+'：</span>';
				html += '<ul typeid ='+item.nodeId+'>';
				item.children.forEach(function(items){
					html += '<li class="op_i '+fnName+'" typeid="'+items.nodeId+'"><a href="javascript:;" onclick="selectAttr(this,'+items.nodeId+',\''+fnName+'\',\''+items.nodeName+'\')">'+items.nodeName+'</a></li>';
				});
				html += '</ul>';
				html += '</li>';
			});
		}

		html += '</ul>';
		$('#J_searchCategory').html(html);
		var country = $('#J_crimbsNav').attr('country');
		var province = $('#J_crimbsNav').attr('province');
		var city = $('#J_crimbsNav').attr('city');
		// if(country && province && !city){
		// 	$('.country').remove();
		// 	$('.province').remove();
		// }else if(country && !province){
		// 	$('.country').remove();
		// 	$('.city').remove();
		// }else if(!country && province && !city){
		// 	$('.country').remove();
		// 	$('.province').remove();
		// }else if(city){
		// 	$('.country').remove();
		// 	$('.province').remove();
		// 	$('.city').remove();
		// }else{
		// 	$('.province').remove();
		// 	$('.city').remove();
		// }

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

	$('img').each(function(){  //图片加载失败替换默认图片
		$(this).on('error',function(){
			$(this).attr('src','/static/images/timg.jpeg')
		})
	});
	if(!$('#J_searchCategory ul').html()){ //渲染数据为空隐藏边框
		$('#J_searchCategory ul').css('border','none')
	}
	$(".tags[endType='attrType']").each(function(){
		var endTypeId = $(this).attr('endTypeId');
		$(".parLi ul[typeid='"+endTypeId+"']").parent('.parLi').remove();
	});
}
function selectCategory(that,id,fnName,name){
	categoryCode = id;
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
function selectAttr(that,id,fnName,name){
	var ulId = $(that).parent().parent().attr('typeid');
	if($('#J_crimbsNav').attr(fnName+'name'+ulId) == name){
		return;
	}else{
		var parName = $(that).parents('ul').siblings('span').text();
		$('.tags').each(function(){
			if($(this).attr(fnName+'name'+ulId)){
				$(this).remove();
			}
		});
		$('#J_crimbsNav').attr(fnName+'name'+ulId,name).attr(fnName+'id'+ulId,id).append('<span class="tags" '+fnName+'name'+ulId+'='+name+' '+fnName+'id'+ulId+'='+id+' type='+fnName+'name'+ulId+' endType="attrType" endTypeId='+ulId+'>'+parName+name+'<a href="JavaScript:;" onclick="removeTag(this)" class="fa fa-close"></a></span>');
		// $(that).parent('.op_i').addClass('active').siblings().removeClass('active');
	}
	$(that).parents('.parLi').hide();
	getDataForin();
}
function removeTag(that){
	var ats = $(that).parent('.tags').attr('type');
	console.log(ats);
	$('#J_crimbsNav').removeAttr(ats);
	$(that).parent('.tags').remove();
	getDataForin();
}
function getDataForin(){
    dataParm.esGoods.catIds = '';
    dataParm.esGoods.attrIds = '';
    dataParm.esGoods.goodsAreas = '';
	dataParm.esGoods.keywordsArrays = '';
	dataParm.esGoods.payFormats = '';
	if($('#J_crimbsNav').attr('category')){
		dataParm.esGoods.catIds = $('#J_crimbsNav').attr('categoryid');
	}else{
		dataParm.esGoods.catIds = catId;
	}
	for(var i=1;i<=5;i++){
		if($('#J_crimbsNav').attr('attrtypename'+i)){
			dataParm.esGoods.attrIds += $('#J_crimbsNav').attr('attrtypeid'+i)+' ';
		}
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

$(document).ready(function(){
	if(prId){//渲染分类
		var html = '';
		html += '<div class="crumbs-nav margin-top-20 padding-bottom-10" id="J_crimbsNav">';
		html += '分类：<span class="color-blue">'+prNm+'</span></div>';
		$('#J_searchCategory').before(html)
	}else{
		$('#J_searchCategory').addClass('margin-top-20')
	}
});