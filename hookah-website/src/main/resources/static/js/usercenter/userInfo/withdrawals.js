/**
 * Created by lss on 2017/7/19 0019.
 */
function loadPageData(data){
    var list = data.data.list;
    if(list.length > 0){
        var html = '';
        for(var i=0; i<list.length; i++){
            switch (list[i].checkStatus) {
                case(0):
                    list[i].checkStatus = '待审核';
                    break;
                case(1):
                    list[i].checkStatus = '审核成功';
                    break;
                case(2):
                    list[i].checkStatus = '审核未成功';
                    break;
            }
            html+="<tr >" ;
            html+="<td>"+list[i].serialNo+"</td>" ;
            html+="<td>"+list[i].addTime+"</td>" ;
            html+="<td>￥"+(list[i].money / 100).toFixed(2)+"</td>" ;
            html+="<td>"+list[i].checkStatus +"</td>" ;
            html+="<td><a href=''>查看详情</td>" ;
            html+="</tr >" ;
        }
        $('.ithdrawals-down-list-content').html(html);
    }else{
        $('.ithdrawals-down-list-content').html('<tr class="no"><td colspan="8">暂无数据</td></tr>');
    }
}


$(function () {
    $("#goPay").on("click",function () {
        $.ajax({
            url:host.website+'/withdrawRecord/applyW',
            data:{
                money:$("#money").val(),
                payPwd:$("#password").val()
            },
            type:'get',
            success:function (data) {
                if(data.code=="1"){
                    window.location.href= host.website+'/usercenter/withdrawalStep2';
                }
            }
        });
    })

    // 类型点击事件
    $(".search-criteria .status li").on("click",function () {
        var status=null;
        $(this).addClass("active").siblings().removeClass("active");
        status=$(this).attr("data-status");
        dataParm.tradeStatus=status || null;
        goPage(1);
    });
    // 日历input事件
    $("#endDate").click(function () {
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
})