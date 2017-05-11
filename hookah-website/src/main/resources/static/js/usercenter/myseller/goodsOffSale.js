function loadPageData(data) {
	if (data.data.list.length > 0) {
		var list = data.data.list;
		var html = '';
		for (var i = 0; i < list.length; i++) {
			html += '<tr>';
			html += '<td class="text-center">';
			html += '<a href="javascript:void(0)">';
			html += '<img src="' + list[i].goodsImg + '" alt="">';
			html += '<p>' + list[i].goodsName + '</p>';
			html += '</a>';
			html += '</td>';
			html += '<td>' + list[i].catName + '</td>';
			html += '<td class="text-right">' + (list[i].shopPrice / 100).toFixed(2) + '</td>';
			html += '<td class="text-center">' + list[i].addTime + '</td>';
			if (list[i].checkStatus == 0) {
				html += '<td class="text-center">审核中</td>';
			} else if (list[i].checkStatus == 1) {
				html += '<td class="text-center">通过</td>';
			} else if (list[i].checkStatus == 2) {
				html += '<td class="text-center">不通过</td>';
			}
			html += '<td>';
			html += '<a href="javascript:selectTimes(\'' + list[i].goodsId + '\');">上架</a>';
			html += '<a href="'+host.website+'/usercenter/goodsModify?id='+list[i].goodsId+'">修改</a>';
			html += '<a href="javascript:deleteGoods(\'' + list[i].goodsId + '\');">删除</a>';
			html += '</td>';
			html += '</tr>';
		}
		$('.trade-box tbody').html(html);
	} else {
		$('.trade-box tbody').html('<tr><td colspan="10"><div class="noData">暂无数据</div></td></tr>');
	}
}
function deleteGoods(id) {
	$.confirm('你确定要删除这条消息吗? ',null,function(type){
		if(type == 'yes'){
			this.hide();
			$.ajax({
				url: host.website + '/goods/back/status/del',
				type: 'post',
				data: {
					goodsId: id
				},
				success: function (data) {
					if (data.code == 1) {
						$.alert('删除成功',true,function(){
							location.reload();
						});
					} else {
						$.alert(data.message)
					}
				}
			})
		}else{
			this.hide();
		}
	});

}
var timesVal = 0;
function selectTimes(id) {
	var dateTime = null;
	var html = '';
	html+='<h3 class="font-size-18">请选择上架时间</h3>';
	html+='<div>';
	html+='<form id="selectTime">';
	html+='<div class="margin-top-10 margin-bottom-10">';
	html+='<label class="margin-right-20"><input type="radio" value="0" name="dateTime" checked class="margin-right-5" onchange="isOnsaleFun(this)">立即上架</label>';
	html+='<label><input type="radio" value="1" name="dateTime" class="margin-right-5" onchange="isOnsaleFun(this)">预约上架</label>';
	html+='</div>';
	html+='<div>';
	html+='<input type="text" id="indate" style="display: none; width: 160px;height: 30px;border: 1px solid #ccc;padding: 0 10px;" name="dateTimeVal" placeholder="请选择时间">';
	html+='<p class="color-red" style="display: none" id="tip_i">请选择上架时间</p>';
	html+='</div>';
	html+='</form>';
	html+='</div>';
	$.confirm(html,[{yes:"确定"},{close:'取消'}],function(type){
		if(type == 'yes'){
			if(timesVal == 1){
				if($('input[name="dateTimeVal"]').val()){
					dateTime = $('input[name="dateTimeVal"]').val();
					this.hide();
					onsale(id,dateTime)
				}else{
					$('#tip_i').show();
				}
			}else{
				dateTime = null;
				this.hide();
				onsale(id,dateTime)
			}
		}else{
			this.hide();
		}
	});
	$.jeDate("#indate", {
		format: "YYYY-MM-DD hh:mm:ss",
		isTime: true,
		minDate: $.nowDate(0)
	});
	$('#indate').click(function(){
		$('#tip_i').hide();
	})
}
function isOnsaleFun(that) {
	$('#tip_i').hide();
	if ($(that).val() == 1) {
		$('#indate').show();
		timesVal = 1;
	} else {
		$('#indate').hide();
		timesVal = 0;
	}
}
function onsale(id,dateTime) {
	// $.confirm('你确定要上架此商品吗? ',null,function(type){
	// 	if(type == 'yes'){
	// 		this.hide();
			$.ajax({
				url: host.website + '/goods/back/onsale',
				type: 'post',
				data: {
					goodsId: id,
					dateTime:dateTime
				},
				success: function (data) {
					if (data.code == 1) {
						$.alert('操作成功',true,function(){
							location.reload();
						});
					} else {
						$.alert(data.message)
					}
				}
			});
		// }else{
		// 	this.hide();
		// }
	// });

}
$('#J_goodsNameSearch').on('focus',function () {
	$(this).siblings('.tips').hide();
})
$('#J_goodsNameSearch').on('blur',function () {
	if($(this).val()){
		$(this).siblings('.tips').hide();
	}else{
		$(this).siblings('.tips').show();
	}
})
$('#J_goodsNameSearch').hover(function () {
	if($(this).val()){
		$('.cleanBtn').show();
	}else{
		$('.cleanBtn').hide();
	}
});
$('.cleanBtn').click(function(){
	$('#J_goodsNameSearch').val('');
	delete dataParm.goodsName;
	goPage(1);
});
function change(){
	var vals = $('#J_goodsNameSearch').val();
	if (vals) {
		dataParm.goodsName = vals;
		goPage(1);
	} else {
		$('#J_goodsNameSearch').siblings('.tips').show();
	}
	return false;
}