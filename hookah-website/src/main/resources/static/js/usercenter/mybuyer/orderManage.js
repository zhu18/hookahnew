function loadPageData(data){
    if(data.data.list){
        var list = data.data.list;
        var html = '';
        for(var i=0; i<list.length; i++){
			html += '<tr class="content border-bottom">';
			html += '<td class="text-align-center">2017022813474081863964</td>';
			html += '<td class="text-align-center">';
			html += '<img src="/static/images/xm.png" alt="">';
			html += '<a href="" class="display-block">银行卡实名认证接口（需个人授权）</a>';
			html += '</td>';
			html += '<td class="text-align-center">1</td>';
			html += '<td class="text-align-center">次</td>';
			html += '<td class="text-align-right">￥&nbsp;200.00</td>';
			html += '<td>2017-02-28&nbsp;&nbsp;19:20:20</td>';
			html += '<td>已付款</td>';
			html += '<td class="text-align-center">';
			html += '<a href="" class="display-block">查看详情</a>';
			html += '<a href="" class="display-block">评价晒单</a>';
			html += '<a href="" class="display-block">取消</a>';
			html += '</td>';
			html += '</tr>';
        }

        $('.order tbody').html(html);
    }
}