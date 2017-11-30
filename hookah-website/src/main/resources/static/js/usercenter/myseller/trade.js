
//日期插件结束
$(function () {
    $("#search-btn").on('click',function () {
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        var options=$("#sel option:selected").val();
        dataParm.startDate = startDate ? startDate : null;
        dataParm.endDate = endDate ? endDate : null;
        dataParm.goodsType = options ? options : null;
        goPage(1);
    });
    //日期插件开始
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
});


