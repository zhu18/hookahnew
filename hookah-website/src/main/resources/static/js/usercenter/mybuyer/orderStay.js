/**
 * Created by wcq on 2017/4/14.
 */
function loadPageData(data){
    if(data.data.list){
        var list = data.data.list;
        var html = '';
        for(var i=0; i<list.length; i++){
            html+= '<tr class="content border-bottom">';
            html+= '<td class="text-align-center">'+ 2017022813474081863964 +'</td>';
            html+= '<td class="text-align-center">';
            html+= '<img src='+"/static/images/xm.png"+' alt="">';
            html+= '<a href="" class="display-block">银行卡实名认证接口（需个人授权）</a>';
            html+= '</td>';
            html+= '<td class="text-align-center">1</td>';
            html+= '<td class="text-align-center">次</td>';
            html+= '<td class="text-align-right">￥&nbsp;200.00</td>';
            html+= '<td>2017-02-28&nbsp;&nbsp;19:20:20</td>';
            html+= '<td>未付款</td>';
            html+= '<td class="text-align-center">';
            html+= '<a href="" class="display-inline-block goPay btn btn-full-orange">去支付</a>';
            html+= '<a href="" class="display-block">删除</a>';
            html+= '</td>';
            html+= '</tr>';
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
    dataParm.startDate = $("#startDate").val();
    dataParm.endDate = $("#endDate").val();
    goPage(1);
});