function loadPageData(data){ //渲染页面数据
    // return console.log(JSON.stringify(data))
    if(data.data.list){
        var list = data.data.list;
        var html = '';
        for(var i=0; i<list.length; i++){
            html += '<li>';
            html += '<div class="item-top">';
            html += '<p><img src="'+list.goodsImg+'" alt=""/></p>';
            html += '<p>'+list.goodsName+'</p>';
            html += '<p>'+list.goodsBrief+'</p>';
            html += '<p ><span>'+list.goodsName+'</span></p>';
            html += '</div>';
            html += '<div class="item-down">';
            html += '<span class="grid-left">编号：<s>18769883892</s></span>';
            html += '<a class="grid-right" href="javascript:void(0)">下载使用</a>';
            html += '</div>';
            html += '</li>';
        }
        $('.order-list ul').html(html);
    }
}


