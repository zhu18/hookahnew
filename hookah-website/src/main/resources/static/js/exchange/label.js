function loadPageData(data){ //渲染页面数据
	var currentPage = data.data.currentPage;
	if(data.data.list.length > 0){
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
			html += '<a target="_blank" class="item-top" href="/exchange/details?id='+list[i].goodsId+'">';
			html += '<p class="goods-img"><img src="'+list[i].goodsImg+'" alt=""/></p>';
			html += '<p class="goods-name">'+list[i].goodsName+'</p>';
			html += '<p class="goods-brief">'+(list[i].goodsBrief  ? list[i].goodsBrief : '暂无简介')+'</p>';
			html += '</a>';
			html += '<div class="item-down clearfix">';
			if(list[i].isDiscussPrice == 1){
				html += '<span class="grid-left goods-price">价格：<span>咨询服务</span></span>';
			}else{
				html += '<span class="grid-left goods-price">￥<span>'+shopPrice+'</span>/'+(list[i].shopNumber == 1 ? '':list[i].shopNumber)+shopFormat+'</span>';
			}
			html += '<a class="grid-right goods-cart btn btn-full-red padding-5" href="/exchange/details?id='+list[i].goodsId+'" target="_blank">查看详情</a>';
			html += '</div>';
			html += '</li>';
		}
		$('.order-list ul').html(html);
	}else{
		$('.order-list ul').html('<div class="noData">暂无数据</div>');
	}
	if(data.data2){ //渲染分类数据
		var html = '';
		html += '<ul class="conditionCon">';
		if(data.data2.length > 0){
			renderSelector(data.data2,'标签','labels');
		}
		function renderSelector(datas,name,fnName){
			html += '<li class="parLi '+fnName+'">';
			html += '<span>'+name+'：</span>';
			html += '<ul>';
			datas.forEach(function(item){
				html += '<li class="op_i '+fnName+'" typeid="'+item.labId+'"><a href="javascript:;" onclick="selectCategory(this,\''+item.labId+'\',\''+fnName+'\',\''+item.labName+'\')">'+item.labName+'</a></li>';
			});
			html += '</ul>';
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
	dataParm.labels = '';
	if($('#J_crimbsNav').attr('labels')){
		dataParm.labels = $('#J_crimbsNav').attr('labelsid');
	}
	goPage('1')
}

$(document).ready(function(){
		var html = '';
		html += '<div class="crumbs-nav margin-top-20 padding-bottom-10" id="J_crimbsNav">';
		html += '已选：<span class="color-blue"></span></div>';
		$('#J_searchCategory').before(html)
});