/**
 * Created by lss on 2017/7/13 0013.
 */

function loadPageData(data){
    if(data.data.list.length > 0){
        var list = data.data.list;
        var html = '';
        for(var i=0; i<list.length; i++){
            html+="<tr >" ;
            html+="<td>992737131715</td>" ;
            html+="<td>￥50.00</td>" ;
            html+="<td>￥50.00</td>" ;
            html+="<td>￥50.00</td>" ;
            html+="<td>已清算</td>" ;
            html+="<td>2017-07-04 16:50:27</td>" ;
            html+="<td><a href='/usercenter/settlementRecordDetails'>查看</a></td>";
        }
        $('.settlementRecord-down-list-content').html(html);
    }else{
        $('.settlementRecord-down-list-content').html('暂无数据');
    }

    $(".tr-list").on('click',function () {
        // console.log($(this).find(".list-id").getAttribute("data-id"));
        var orderId=$(this).find(".list-id").attr("data-id")
        // console.log($(".list-id").getAttribute("data-id"));
        $.ajax({
            type: "get",
            url: host.website+'/order/viewSoldDetails',
            cache:false,
            data: {
                orderId:orderId
            },
            success: function (data) {
                if (data.code == "1") {
                    // console.log(data.data);
                    orderInfo(data.data);
                    Loading.stop();
                } else {
                    Loading.stop();
                    console.log(data.message);
                }
            }
        })
    });

    function orderInfo(data) {
        console.log(data);
        var html = '';
        // var item = list[i].mgOrderGoodsList;
        html += '<table>';
        html += '<thead>';
        html += '<tr class="tr-list" style="cursor: pointer">';
        html += '<th class="list-id" style="width: 280px;" >订单号:' + data.orderSn + '</th>';
        html += '<th class="text-align-left"  colspan=2 style="position: relative;">创建时间:' + data.addTime + '</th>';
        // html += '<th></th>';
        html += '<th colspan="2" style="width:190px;border: 1px solid #e5e5e5;">总额:￥' + (data.orderAmount / 100).toFixed(2) + '</th>';
        html += '</tr>';
        html += '</thead>';
        html += '<tbody>';
        var goods = data.mgOrderGoodsList;
        for (var ii = 0; ii < goods.length; ii++) {
            var mMat = null;
            switch (goods[ii].goodsFormat) {
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
            var catidS = (goods[ii].catId).substring(0, 3);
            var isOfflineInfo='';
            var goodsTypeInfo='';
            if(goods[ii].goodsType==0){
                goodsTypeInfo="数据源-离线数据"
            }else if(goods[ii].goodsType==1){
                goodsTypeInfo="数据源-API"
            }else if(goods[ii].goodsType==2){
                goodsTypeInfo="模型"
            }else if(goods[ii].goodsType==4){
                goodsTypeInfo="分析工具-独立部署软件"
            }else if(goods[ii].goodsType==5){
                goodsTypeInfo="分析工具-SaaS"
            }else if(goods[ii].goodsType==6){
                goodsTypeInfo="应用场景-独立部署软件"
            }else if(goods[ii].goodsType==7){
                goodsTypeInfo="应用场景-SaaS"
            }
            if(goods[ii].isOffline==1){
                isOfflineInfo="交付方式：线下"
            }else if(goods[ii].isOffline==0){
                isOfflineInfo="交付方式：线上"
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
            html += '<td class="text-align-left">x' + goods[ii].goodsNumber +
                '<br/>' + '规格:' + (goods[ii].goodsPrice / 100).toFixed(2) + '/' + mMat + '<br />'+ goodsTypeInfo+ '<br />'+ isOfflineInfo+'</td>';

            html += '<td style="width:190px;" class="">金额:￥&nbsp;' + ((goods[ii].goodsPrice / 100) * goods[ii].goodsNumber).toFixed(2) + '<br/><br/>' + data.payName + '</td>';//订单总金额
            if (ii == 0) {
                html += '<td rowspan="' + goods.length + '" class="border-left" style="width:190px;border: 1px solid #e5e5e5;">';
                html += '<span>已完成</span>';
                html += '</td>';
            }
            html += '</tr>';
        }
        html += '</tbody>';
        html += '</table>';
        html +=' <a href="/usercenter/trade" class="btn btn-full-orange grid-right">返回列表</a>';
        $('.trade-box').html(html);
    }


}
$(function () {

    $(".query-condition .btn-box a").on("click",function () {
        $(this).addClass("active").siblings().removeClass("active")
    })
    $(".query-condition .select-month a").on("click",function () {
        $(this).addClass("active").siblings().removeClass("active")
    })

    // 日历插件开始
    var start = {
        format: "YYYY-MM-DD hh:mm:ss",
        isTime: true,
        maxDate: $.nowDate(0),
        choosefun: function (elem, datas) {
            end.minDate = datas; //开始日选好后，重置结束日的最小日期
        }
    };
    var end = {
        format: "YYYY-MM-DD hh:mm:ss",
        isTime: true,
        maxDate: $.nowDate(0),
        choosefun: function (elem, datas) {
            start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
        }
    };
    $.jeDate("#startDate", start);
    $.jeDate("#endDate", end);
    // 日历插件结束


    $(".search-btn").on("click",function () {
        var status=null
        $(".query-condition .btn-box a").each(function () {
            if($(this).hasClass("active")){
                status=$(this).attr("data-status");
            }
        })
        dataParm.settleStatus=status || null;
        dataParm.orderSn=$("#orderSn").val() || null;
        dataParm.shopName=null;
        dataParm.startDate=$("#startDate").val() || null;
        dataParm.endDate=$("#endDate").val() || null;
        // $.ajax({
        //     url:host.website+'/settleOrder/getList',
        //     data:{
        //         currentPage:"1",
        //         pageSize:"15",
        //         settleStatus:status || null,
        //         orderSn:$("#orderSn").val() || null,
        //         shopName:null,
        //         startDate:$("#startDate").val() || null,
        //         endDate:$("#endDate").val() || null
        //     },
        //     type:'get',
        //     success:function (data) {
        //         console.log(data.data);
        //         $(".contactName").html(data.data.contactName)
        //         $(".contactPhone").html(data.data.contactPhone)
        //         $(".contactAddress").html(data.data.contactAddress)
        //         $(".postCode").html(data.data.postCode)
        //     }
        // })
    })


})