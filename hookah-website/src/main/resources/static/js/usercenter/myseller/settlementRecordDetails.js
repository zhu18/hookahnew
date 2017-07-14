/**
 * Created by Administrator on 2017/7/13 0013.
 */

$(function () {
    var id = $.getUrlParam('id');
    $.ajax({
        url:host.website+'/settleOrder/getListBySettleId/'+id,
        data:{},
        type:'get',
        success:function (data) {
            console.log(data.data.settleRecords);
            console.log();
            var list = data.data.settleRecords;
            var orderDetail=data.data.waitSettleRecord
            if(list.length>0){
                var html = '';
                for(var i=0; i<list.length; i++){
                    if(!list[i].goodsName){
                        list[i].goodsName=""
                    }
                    if(list[i].settleStatus== "0"){
                        list[i].settleStatus="待结算"
                    }else if(list[i].settleStatus== "1"){
                        list[i].settleStatus="已结清"
                    }
                    html+="<tr >" ;
                    html+="<td>"+(i+1)+"</td>" ;
                    html+="<td>"+list[i].settleDate+"</td>" ;
                    html+="<td>￥"+(list[i].settleAmount / 100).toFixed(2)+"</td>" ;
                    html+="<td>￥"+(list[i].supplierAmount / 100).toFixed(2)+"</td>" ;
                    html+="<td>￥"+(list[i].refundAmount / 100).toFixed(2)+"</td>" ;
                    html+="<td></td>";
                    html+="</tr >" ;
                }
                $('.settlementRecordDetails-list tbody').html(html);
            }else {}

            if(!orderDetail.goodsName){
                orderDetail.goodsName=""
            }
            if(orderDetail.settleStatus== "0"){
                orderDetail.settleStatus="待结算"
            }else if(orderDetail.settleStatus== "1"){
                orderDetail.settleStatus="已结清"
            }
            var html1 = '';
            html1 +="<tr>";
            html1 +="<td>订单编号："+orderDetail.orderSn+" </td>";
            html1 +="<td> 订单时间："+orderDetail.orderTime+" </td>";
            html1 +="<td> 商品名称："+orderDetail.goodsName+" </td>";
            html1 +="<td> 结算状态："+orderDetail.settleStatus+" </td>";
            html1 +="</tr>";
            html1 +="<tr>";
            html1 +="<td> 订单金额："+(orderDetail.orderAmount / 100).toFixed(2)+" </td>";
            html1 +="<td> 已结算金额："+(orderDetail.hasSettleAmount / 100).toFixed(2)+"</td>";
            html1 +="<td> 剩余金额："+(orderDetail.noSettleAmount / 100).toFixed(2)+" </td>";
            html1 +="</tr>";
            $('.settlementRecordDetails-top table').html(html1);
        }
    });
})