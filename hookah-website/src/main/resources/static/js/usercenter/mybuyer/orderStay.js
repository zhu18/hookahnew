/**
 * Created by wcq on 2017/4/14.
 */
function loadPageData(data){
    $("#payCount").html(data.data.paidCount);
    $("#noPayCount").html(data.data.unpaidCount);
    if(data.data.orders.list.length > 0){
        var list = data.data.orders.list;
        var html = '';
        for(var i=0; i<list.length; i++){
            html+= '<tr class="content border-bottom">';
            html+= '<td class="text-align-center">'+list[i].orderSn+'</td>';
            html+= '<td class="text-align-right moneyTotal">￥&nbsp;'+(list[i].orderAmount/100).toFixed(2)+'</td>';
            html+= '<td>'+list[i].addTime+'</td>';
            html+= '<td>未付款</td>';
            html+= '<td class="text-align-center">';
            html+= '<a href="'+host.website+'/order/payOrder?orderSn='+list[i].orderSn+'"  class="display-inline-block goPay btn btn-full-orange">去支付</a>';
            html+= '<a target="_blank" href="/order/viewDetails?orderId='+list[i].orderId+'&num=2" class="display-block padding-top-5">查看详情</a>';
            html+= '<a href="javascript:confirmDelete(\''+list[i].orderId+'\');" class="display-block">删除</a>';
            html+= '</td>';
            html+= '</tr>';
        }
        $('.order tbody').html(html);
    }else{
        $('.order tbody').html('<tr class="noData"><td colspan="5">暂无订单！</td></tr>');
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
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    dataParm.startDate = startDate?startDate:format(new Date());
    dataParm.endDate = endDate?endDate:format(new Date());
    // if(!startDate){
    //     $("#startDate").val(format(new Date()));
    // }
    // if(!endDate){
    //     $("#endDate").val(format(new Date()));
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