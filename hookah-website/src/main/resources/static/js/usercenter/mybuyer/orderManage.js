function loadPageData(data){
    $("#payCount").html(data.data.paidCount);
    $("#noPayCount").html(data.data.unpaidCount);
    if(data.data.orders.list.length > 0){
        var list = data.data.orders.list;
        var html = '';
        for(var i=0; i<list.length; i++){
            html += '<tr class="content border-bottom">';
            html += '<td class="text-align-center">'+list[i].orderSn+'</td>';
            html += '<td class="text-align-right moneyTotal">￥&nbsp;'+(list[i].orderAmount/100).toFixed(2)+'</td>';//订单总金额
            html += '<td>'+format(list[i].addTime)+'</td>';
            html += '<td>已付款</td>';
            html += '<td class="text-align-center">';
            html += '<a target="_blank" href="/order/viewDetails?orderId='+list[i].orderId+'&num=1" class="display-block">查看详情</a>';
            if(list[i].commentFlag==0){
                html += '<a target="_blank" href="/order/sunAlone?orderId='+list[i].orderId+'" class="display-block">评价晒单</a>';
            }else if(list[i].commentFlag==1){
                html += '<a href="" class="display-block">已评价</a>';
            }
            html += '<a href="javascript:confirmDelete(\''+list[i].orderId+'\');" class="display-block deleteRadio">删除</a>';
            html += '</td>';
            html += '</tr>';
        }

        $('.order tbody').html(html);
    }else{
		$('.order tbody').html('<tr class="noData"><td colspan="5">您暂时没有已付款的订单！</td></tr>');
    }
}
var start = {
    format: "YYYY-MM-DD hh:mm:ss",
    isTime: true,
    maxDate: $.nowDate(0),
    choosefun: function(elem,datas){
        end.minDate = datas; //开始日选好后，重置结束日的最小日期
    }
};
var end = {
    format: "YYYY-MM-DD hh:mm:ss",
    isTime: true,
    maxDate: $.nowDate(0),
    choosefun: function(elem,datas){
        start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
    }

};
$.jeDate("#startDate", start);
$.jeDate("#endDate",end);
//点击查询按钮
$(".searchQuery .search").on("click",function(){
    //评论状态：0：未评论；1：已评论
    var radioChecked = $(".comment-status input:radio[name='comment']:checked");
    dataParm.commentFlag = radioChecked.val();
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    dataParm.startDate = startDate?startDate:format(new Date());
    dataParm.endDate = endDate?endDate:format(new Date());
    if(!startDate){
        $("#startDate").val(format(new Date()));
    }
    if(!endDate){
        $("#endDate").val(format(new Date()));
    }
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
            if (data.code == 1) {
                location.reload(true);
            } else {
                console.log("删除失败！");
            }
        }
    })
}
function confirmDelete(orderId){
    if(confirm("确定要删除该订单吗？")){
        deleteRadio(orderId);
    }else{

    }
}







