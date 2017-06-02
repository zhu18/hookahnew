function loadPageData(data){
    $("#payCount").html(data.data.paidCount);
    $("#noPayCount").html(data.data.unpaidCount);
    if(data.data.orders.list.length > 0){
        var list = data.data.orders.list;
        var html = '';
        for(var i=0; i<list.length; i++) {
            html += '<table>';
            html += '<thead>';
            html += '<tr>';
            html += '<th class="">' + list[i].addTime + '</th>';
            html += '<th class="">' + '订单号:' + list[i].orderSn + '</th>';
            html += '<th rowspan="4">九次方大数据交易集团</th>';
            html += '</tr>';
            html += '</thead>';
            var goods = list[i].mgOrderGoodsList;
            for (var ii = 0; ii < goods.length; ii++) {
                html += '<tbody>';
                html += '<tr class="content border-bottom">';
                html += '<td class="text-align-center">'
                html += '<div class="p-img">'
                html += '<a href="/exchange/details?id=' + goods[ii].goodsId + '" target="_blank">'
                html += '<img src="' + goods[ii].goodsImg + '" alt="">'
                html += '</a>'
                html += '</div>'
                html += '<div class="desc margin-top-10 marign-bottom-10" >'
                html += '<a href="/exchange/details?id=' + goods[ii].goodsId + '" target="_blank">' + goods[ii].goodsName + '</a>'
                html += '</div>'
                html += '</td>'
                html += '<td>' + goods[ii].goodsNumber +'<br/>'+ '规格:1000/年</td>';
                html += '<td><a href="" ng-click="getDataPackage('+goods[ii].goodsId+')">下载</a><br/>'+ '规格:1000/年</td>';
                html += '<td class="text-align-right moneyTotal" rowspan="'+goods.length+'">总额&nbsp;￥&nbsp;' + (list[i].orderAmount / 100).toFixed(2) + '<br/>' + list[i].payName + '</td>';//订单总金额
                html += '<td rowspan="'+goods.length+'">已完成' + '<a target="_blank" href="/order/viewDetails?orderId=' + list[i].orderId + '&num=1" class="display-block">订单详情</a>' + '</td>';
                html += '<td class="text-align-center" rowspan="'+goods.length+'">';
                if (list[i].commentFlag == 0) {
                    html += '<a target="_blank" href="/order/sunAlone?orderId=' + list[i].orderId + '" class="display-block">评价晒单</a>';
                } else if (list[i].commentFlag == 1) {
                    html += '<span class="display-block">已评价</span>';
                }
                html += '<a href="/exchange/details?id='+goods[ii].goodsId+'" class="btn-full-blue display-inline-block">再次购买</a>'
                html += '</td>';
                html += '</tr>';
                html += '</tbody>';
            }
            html += '</table>';
        }
        $('.order').html(html);
    }else{
        $('.order').html('<tr class="noData"><td colspan="5">暂时无订单！</td></tr>');
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
    dataParm.startDate = startDate ? startDate : null;
    dataParm.endDate = endDate ? endDate : null;
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







