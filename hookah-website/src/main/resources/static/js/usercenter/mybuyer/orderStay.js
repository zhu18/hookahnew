/**
 * Created by wcq on 2017/4/14.
 */
function loadPageData(data){
    $("#payCount").html(data.data.paidCount);
    $("#noPayCount").html(data.data.unpaidCount);
	if(data.data.orders.list.length > 0){
		var list = data.data.orders.list;
		var html = '';
		for(var i=0; i<list.length; i++) {
			var wacthNm = 0;
			html += '<table>';
			html += '<thead>';
			html += '<tr>';
			html += '<th class="" style="width: 280px;">' + '订单号:' + list[i].orderSn + '</th>';
			html += '<th class="text-align-left">创建时间:' + list[i].addTime + '</th>';
			html += '<th></th>';
			html += '<th style="width:180px;">总额:￥' + (list[i].orderAmount / 100).toFixed(2) +'</th>';
			html += '</tr>';
			html += '</thead>';
			html += '<tbody>';
			var goods = list[i].mgOrderGoodsList;
			for (var ii = 0; ii < goods.length; ii++) {
				var mMat = null;
				switch(goods[ii].goodsFormat){
					case(0):
						mMat = '次';
						break;
					case(1):
						mMat = '天';
						break;
					case(2):
						mMat = '年';
						break;
				}
				if(goods[ii].isOnsale != 1){
					wacthNm ++;
				}
				html += '<tr class="content border-bottom">';
				html += '<td class="text-align-center" style="width: 280px;">';
				html += '<div class="p-img">';
				html += '<a href="/exchange/details?id=' + goods[ii].goodsId + '" target="_blank">';
				html += '<img src="' + goods[ii].goodsImg + '" alt="">';
				html += '</a>';
				html += '</div>';
				html += '<div class="desc margin-top-10 marign-bottom-10" >';
				html += '<a href="/exchange/details?id=' + goods[ii].goodsId + '" target="_blank">' + goods[ii].goodsName + '</a>';
				html += '</div>';
				html += '</td>';
				html += '<td class="text-align-left">x' + goods[ii].goodsNumber +'<br/><br/>'+ '规格:'+ (goods[ii].goodsPrice / 100).toFixed(2) +'/'+ mMat +'</td>';
				html += '<td class="">金额:￥&nbsp;' + ((goods[ii].goodsPrice / 100) * goods[ii].goodsNumber).toFixed(2) + (wacthNm == 0 ? '' : '<br/><br/><span class="color-red">商品已下架</span>') + '</td>';//订单总金额

				if(ii == 0){
					html += '<td rowspan="'+goods.length+'" class="border-left" style="width:190px;">';
					html += '<span class="margin-bottom-5">未付款</span><br>';
					if(wacthNm == 0){
						// html += '<span class="margin-bottom-5 margin-top-5 color-red">此订单不能支付</span>'
						html += '<a href="' + host.website + '/order/payOrder?orderSn=' + list[i].orderSn + '"  class="display-inline-block goPay btn btn-full-orange margin-bottom-5 margin-top-5">去支付</a>';
					}
					// html += '<a target="_blank" href="/order/viewDetails?orderId=' + list[i].orderId + '&num=2" class="display-block color-blue margin-bottom-5">订单详情</a>';
					html += '<a href="javascript:confirmDelete(\'' + list[i].orderId + '\');" class="display-block margin-bottom-5">删除</a>';
					html += '</td>';
				}

				html += '</tr>';

			}
			html += '</tbody>';
			html += '</table>';
		}
		$('.order').html(html);
	}else{
		if(dataParm.startDate ==format(new Date())){
            // $('.order').html('<tr class="noData"><td colspan="5">请输入查询时间!</td></tr>');
            $('.order').html('<div class="font-size-18" style="width:978px;height: 30px;overflow:hidden; text-align:center;line-height: 30px;">请输入查询时间!</div>');
		}else{
            // $('.order').html('<tr class="noData"><td colspan="5">暂时无订单!</td></tr>');
            $('.order').html('<div class="font-size-18" style="width:978px;height: 30px;overflow:hidden; text-align:center;line-height: 30px;">暂时无订单!</div>');
		}
	}
}
var start = {
    format: "YYYY-MM-DD hh:mm:ss",
    isTime: true,
    maxDate: $.nowDate(0)
    // choosefun: function(elem,datas){
    //     end.minDate = datas; //开始日选好后，重置结束日的最小日期
    // }
};
var end = {
    format: "YYYY-MM-DD hh:mm:ss",
    isTime: true,
    maxDate: $.nowDate(0)
    // choosefun: function(elem,datas){
    //     start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
    // }

};
$.jeDate("#startDate", start);

$.jeDate("#endDate",end);
//点击查询按钮
$(".searchQuery .search").on("click",function(){
    $('.order div').css("display","none");
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    dataParm.startDate = startDate?startDate:null;
    dataParm.endDate = endDate?endDate:null;
    // if(!startDate){
    //     delete dataParm.startDate;
    // }
    // if(!endDate){
		// delete dataParm.endDate;
    // }
    goPage(1);
});

// 删除订单
 function deleteRadio(orderId) {
    $.ajax({
        url: '/order/delete',
        type: 'get',
        data:{
            orderId:orderId
        },
        success: function (data) {
            if (!(data.code == 0)) {
				$.alert('删除成功', true, function () {
					location.reload()
				});
            } else {
                console.log("删除失败！");
            }
        }
    })
}

function confirmDelete(orderId){
	$.confirm('确定要删除该订单吗？',null,function(type){
		if(type == 'yes'){
			deleteRadio(orderId);
			this.hide();
		}else{
			this.hide();
		}
	});
}

function getDataPackage(goodsId){
    $.ajax({
        url: host.website+'/help/exportWords',
        type:'get',
        data:{
            goodsId:goodsId
        },
        success:function(data){
            if(data.code == 1){
                // window.location.href = data.data;
                window.location.href = data.data;
            }else{
                $.alert(data.message)
                // $.alert('下载失败')
            }
        }
    });
}