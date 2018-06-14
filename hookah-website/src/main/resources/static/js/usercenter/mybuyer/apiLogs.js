function loadPageData(data) {
    var list = data.data.list;
    if(list != null && list.length > 0){
        var html = '';
        for(var i=0; i<list.length; i++){
            html+="<tr >" ;
            html+="<td>"+list[i].createTime+"</td>" ;
            html+="<td>"+list[i].ip+"</td>" ;
            html+="<td>"+list[i].result +"</td>" ;
            if (list[i].resultName == 1){
                html+="<td>成功</td>" ;
            }else {
                html+="<td>失败</td>" ;
            }
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
	theme:{ bgcolor:"#ffa800",color:"#ffffff", pnColor:"#f9b32b"},
    isTime: true,
    donefun: function (elem, datas) {
        end.minDate = datas; //开始日选好后，重置结束日的最小日期
    }
};
var end = {
    format: "YYYY-MM-DD hh:mm:ss",
	theme:{ bgcolor:"#ffa800",color:"#ffffff", pnColor:"#f9b32b"},
    isTime: true,
    donefun: function (elem, datas) {
        start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
    }
};
jeDate("#startDate", start);
jeDate("#endDate", end);
$("#ip").blur(function () {
    if(this.value){
        if(/^((25[0-5]|2[0-4]\d|[01]?\d\d?)($|(?!\.$)\.)){4}$/.test(this.value)){
            $("#ip").css("border","1px solid #ccc");
        }else{
            $("#ip").css("border","1px solid red");
        }
    }else{
        $("#ip").css("border","1px solid #ccc");
    }
});
$("#search-btn").on('click',function () {
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    //var options=$("#status option:selected").val();
    dataParm.startDate = startDate ? startDate : null;
    dataParm.endDate = endDate ? endDate : null;
    dataParm.ip = $("#ip").val() ? $("#ip").val() : null;
    if(dataParm.ip && !/^((25[0-5]|2[0-4]\d|[01]?\d\d?)($|(?!\.$)\.)){4}$/.test(dataParm.ip)){
        $("#ip").css("border","1px solid red");
        return;
    }
    dataParm.status = $("#status option:selected").val() ? $("#status option:selected").val() : null;
    //dataParm.goodsType = options ? options : null;
    goPage(1);
});

