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
                list[i].settleStatus="已结算"
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
        $('.settlementRecord-down-list-content').html('<tr class="no"><td colspan="8">暂无数据</td></tr>');
    }


}

goPage(1);
$(function () {
     // 状态按钮点击事件
    $(".query-condition .btn-box a").on("click",function () {
        var status=null;
        $(this).addClass("active").siblings().removeClass("active");
        status=$(this).attr("data-status");
        dataParm.settleStatus=status || null;
        goPage(1);
    })
    // 月份选择事件
    $(".query-condition .select-month a").on("click",function () {
        $(this).addClass("active").siblings().removeClass("active")
        var html=$(this).html();
        var now = new Date();
        var date = new Date(now.getTime() - 1);
        var year = date.getFullYear();
        var month = date.getMonth() +1;
        var day = date.getDate();
        var hour = date.getHours();
        var minute = date.getMinutes();
        var second = date.getSeconds();
        $("#endDate").val(year + '-' + month + '-' + day  + ' ' + hour + ':' + minute + ':' + second)
        if(html=="本月"){
            month = now.getMonth()+1;
            day="1";
        } else if(html=="上个月"){
            month = date.getMonth() ;
        } else if(html=="3月"){
            month = date.getMonth()-2 ;
        } else if(html=="半年"){
            month = date.getMonth()-5;
        }
        $("#startDate").val(year + '-' + month + '-' + day  + ' ' + hour + ':' + minute + ':' + second)
        dataParm.settleStatus=status || null;
        dataParm.startDate=$("#startDate").val() || null;
        dataParm.endDate=$("#endDate").val() || null;
        goPage(1);
    })
    // 日历input事件
    $("#endDate").click(function () {
        console.log(1);
        var idInt = setTimeout(function(){
            if($("#endDate").val() && $("#startDate").val()){
                dataParm.startDate=$("#startDate").val() || null;
                dataParm.endDate=$("#endDate").val() || null;
                goPage(1);
                clearInterval(idInt);
            }
        },1500);
    });
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

    // 搜索按钮
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
    })
});