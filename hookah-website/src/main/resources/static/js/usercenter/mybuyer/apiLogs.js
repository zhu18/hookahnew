function loadPageData(data) {
    var list = data.data.list;
    if(list.length > 0){
        var html = '';
        for(var i=0; i<list.length; i++){
            html+="<tr >" ;
            html+="<td>"+list[i].createTime+"</td>" ;
            html+="<td>"+list[i].ip+"</td>" ;
            html+="<td>"+list[i].resultName +"</td>" ;
            html+="<td>"+list[i].hasCount +"</td>" ;
            html+="</tr >" ;
        }
        $('.search-list-content').html(html);
    }else{
        $('.search-list-content').html('<tr class="no"><td colspan="8">暂无数据</td></tr>');
    }
}

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
$("#search-btn").on('click',function () {
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    //var options=$("#status option:selected").val();
    dataParm.startDate = startDate ? startDate : null;
    dataParm.endDate = endDate ? endDate : null;
    //dataParm.goodsType = options ? options : null;
    goPage(1);
});

