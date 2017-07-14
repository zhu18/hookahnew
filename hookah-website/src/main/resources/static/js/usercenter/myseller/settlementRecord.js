/**
 * Created by lss on 2017/7/13 0013.
 */

function loadPageData(data){
    if(data.data.list.length > 0){
        var list = data.data.list;
        var html = '';
        for(var i=0; i<list.length; i++){
            if(!list[i].goodsName){
                list[i].goodsName=""
            }
            if(list[i].settleStatus== "0"){
                list[i].settleStatus="待结算"
            }else if(list[i].settleStatus== "1"){

            }
            html+="<tr >" ;
            html+="<td>"+list[i].orderSn+"</td>" ;
            html+="<td>"+list[i].goodsName+"</td>" ;
            html+="<td>￥"+(list[i].orderAmount / 100).toFixed(2)+"</td>" ;
            html+="<td>￥"+(list[i].hasSettleAmount / 100).toFixed(2)+"</td>" ;
            html+="<td>￥"+(list[i].noSettleAmount / 100).toFixed(2)+"</td>" ;
            html+="<td>"+list[i].settleStatus+"</td>" ;
            html+="<td>"+list[i].orderTime+"</td>" ;
            html+="<td><a href='/usercenter/settlementRecordDetails?id="+list[i].id+"'>查看</a></td>";
            html+="</tr >" ;
        }
        $('.settlementRecord-down-list-content').html(html);
    }else{
        $('.settlementRecord-down-list-content').html('暂无数据');
    }


}

goPage(1);
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
        var status=null;
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

        goPage(1);
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