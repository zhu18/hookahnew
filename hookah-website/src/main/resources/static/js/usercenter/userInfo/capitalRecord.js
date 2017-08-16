/**
 * Created by lss on 2017/7/18 0018.
 */
function loadPageData(data){
    var list = data.data.list;
    if(list.length > 0){
        var html = '';
        for(var i=0; i<list.length; i++){

            switch (list[i].tradeStatus) {
                case(0):
                    list[i].tradeStatus = '处理中';
                    break;
                case(1):
                    list[i].tradeStatus = '已成功';
                    break;
                case(2):
                    list[i].tradeStatus = '未成功';
                    break;
            }
            switch (list[i].tradeType) {
                case(1):
                    list[i].tradeType = '充值';
                    break;
                case(2):
                    list[i].tradeType = '提现';
                    break;
                case(5):
                    list[i].tradeType = '手动充值';
                    break;
                case(4001):
                    list[i].tradeType = '支付';
                    break;
                case(3001):
                    list[i].tradeType = '收入';
                    break;
                case(8):
                    list[i].tradeType = '冲账';
                    break;
                case(10):
                    list[i].tradeType = '冻结';
                    break;
                case(11):
                    list[i].tradeType = '解冻';
                    break;
            }
            html+="<tr >" ;
            html+="<td>"+list[i].orderSn+"</td>" ;
            html+="<td>"+list[i].addTime+"</td>" ;
            html+="<td>"+list[i].tradeType +"</td>" ;
            html+="<td>￥"+(list[i].money / 100).toFixed(2)+"</td>" ;
            html+="<td>"+list[i].tradeStatus +"</td>" ;
            html+="</tr >" ;
        }
        $('.search-list-content').html(html);
    }else{
        $('.search-list-content').html('<tr class="no"><td colspan="8">暂无数据</td></tr>');
    }
}
$(function () {

    // 交易类型点击事件
    $(".search-criteria .transaction-status li").on("click",function () {
        var status=null;
        $(this).addClass("active").siblings().removeClass("active");
        status=$(this).attr("data-status");
        dataParm.tradeType=status || null;
        goPage(1);
    });
    // 类型点击事件
    $(".search-criteria .status li").on("click",function () {
        var status=null;
        $(this).addClass("active").siblings().removeClass("active");
        status=$(this).attr("data-status");
        dataParm.tradeStatus=status || null;
        goPage(1);
    });
    // 月份选择事件-最近
    $(".search-criteria .select-month a").on("click",function () {
        $(this).addClass("active").siblings().removeClass("active");
        var html=$(this).html();
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth() +1;
        var day = date.getDate();
        var hour = date.getHours();
        var minute = date.getMinutes();
        var second = date.getSeconds();
        $("#endDate").val(year + '-' + month + '-' + day  + ' ' + hour + ':' + minute + ':' + second)
        if(html=="本月"){
            month = date.getMonth()+1;
            day="1";
        } else if(html=="1周"){
            var data = new Date(date.getTime()-7*24*3600*1000);
            day = data.getDate();
            month = data.getMonth()+1 ;
        } else if(html=="1个月"){
            month = date.getMonth() ;
        } else if(html=="3个月"){
            month = date.getMonth()-2;
        }
        $("#startDate").val(year + '-' + month + '-' + day  + ' ' + hour + ':' + minute + ':' + second)
        dataParm.tradeStatus=status || null;
        dataParm.tradeType=status || null;
        dataParm.startDate=$("#startDate").val() || null;
        dataParm.endDate=$("#endDate").val() || null;
        goPage(1);
    });
    // 日历插件开始
    var start = {
        format: "YYYY-MM-DD hh:mm:ss",
        isTime: true,
        maxDate: $.nowDate(0),
        choosefun: function (elem, datas) { //日历选择完毕回调函数
            end.minDate = datas; //开始日选好后，重置结束日的最小日期
            if($("#endDate").val() && $("#startDate").val()){
                dataParm.startDate=$("#startDate").val() || null;
                dataParm.endDate=$("#endDate").val() || null;
                goPage(1);
            }
        },
        clearfun:function(val) {  //清除日历按钮完毕回调函数
            dataParm.startDate=$("#startDate").val() || null;
            dataParm.endDate=$("#endDate").val() || null;
            goPage(1);
        }
    };
    var end = {
        format: "YYYY-MM-DD hh:mm:ss",
        isTime: true,
        maxDate: $.nowDate(0),
        choosefun: function (elem, datas) {
            start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
            if($("#endDate").val() && $("#startDate").val()){
                dataParm.startDate=$("#startDate").val() || null;
                dataParm.endDate=$("#endDate").val() || null;
                goPage(1);
            }
        },
        clearfun:function(val) {
            dataParm.startDate=$("#startDate").val() || null;
            dataParm.endDate=$("#endDate").val() || null;
            goPage(1);
        }
    };
    $.jeDate("#startDate", start);
    $.jeDate("#endDate", end);
    // 日历插件结束

})