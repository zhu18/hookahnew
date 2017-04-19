function loadPageData(data){
    if(data.data.list){
        var list = data.data.list;
        var html = '';
        for(var i=0; i<list.length; i++){
            html += '<tr class="content border-bottom">';
            html += '<td class="text-align-center">'+list[i].mgOrderGoodsList.orderId+'</td>';
            html += '<td class="text-align-center">';
            html += '<a href="" class="display-block">'+list[i].mgOrderGoodsList.goodsName+'</a>';
            html += '</td>';
            html += '<td class="text-align-center">'+list[i].mgOrderGoodsList.goodsNumber+'</td>';
            html += '<td class="text-align-center">次</td>';
            html += '<td class="text-align-right">￥&nbsp;‘+list[i].mgOrderGoodsList.goodsPrice+’</td>';
            html += '<td>'+format(list[i].addTime)+'</td>';
            html += '<td>已付款</td>';
            html += '<td class="text-align-center">';
            html += '<a href="" class="display-block">查看详情</a>';
            if(list.commentFlag==0){
                html += '<a href="" class="display-block">未评价</a>';
            }else if(list.commentFlag==1){
                html += '<a href="" class="display-block">已评价</a>';
            }
            html += '<a href="" class="display-block">删除</a>';
            html += '</td>';
            html += '</tr>';
        }
        function add(m){ return m < 10 ? '0'+ m:m };
        function format(time){
            var date = new Date(time);
            var year = date.getFullYear() ;
            var month = date.getMonth()+1;
            var date1 = date.getDate() ;
            var hours = date.getHours();
            var minutes = date.getMinutes();
            var seconds = date.getSeconds();
            return year+'-'+add(month)+'-'+add(date1)+' '+add(hours)+':'+add(minutes)+':'+add(seconds);
        };
        $('.order tbody').html(html);
    }else{
		$('.order tbody').html('<div class="noData">暂无数据</div>');
    }
}
$.jeDate("#startDate", {
    format: "YYYY-MM-DD hh:mm:ss",
    isTime: true,
    minDate: $.nowDate(0)
});
$.jeDate("#endDate", {
    format: "YYYY-MM-DD hh:mm:ss",
    isTime: true,
    minDate: $.nowDate(0)
});
//点击查询按钮
$(".searchQuery .search").on("click",function(){
    //评论状态：0：未评论；1：已评论
    var radioChecked = $(".comment-status input:radio[name='comment']:checked");
    dataParm.commentFlag = radioChecked.val();
    dataParm.startDate = $("#startDate").val();
    dataParm.endDate = $("#endDate").val();
    goPage(1);
});


