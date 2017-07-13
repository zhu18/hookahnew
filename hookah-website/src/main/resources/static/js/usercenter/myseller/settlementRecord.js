/**
 * Created by lss on 2017/7/13 0013.
 */
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

        $.ajax({
            url:host.website+'/settleOrder/getList',
            data:{
                currentPage:"1",
                pageSize:"15",
                settleStatus:status || null,
                orderSn:$("#orderSn").val() || null,
                shopName:null,
                startDate:$("#startDate").val() || null,
                endDate:$("#endDate").val() || null
            },
            type:'get',
            success:function (data) {
                console.log(data.data);
                $(".contactName").html(data.data.contactName)
                $(".contactPhone").html(data.data.contactPhone)
                $(".contactAddress").html(data.data.contactAddress)
                $(".postCode").html(data.data.postCode)
            }
        })
    })
})