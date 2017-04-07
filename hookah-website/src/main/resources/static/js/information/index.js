function loadPageData(data){ //渲染页面数据
    return console.log(JSON.stringify(data))

    if(data.data.dataList){
        var list = data.data.list;
        var html = '';
        for(var i=0; i<list.length; i++){
            html += '<tr>';
            html += '<td>'+(i+1)+'</td>';
            html += '<td>'+list[i].newsTitle+'</td>';
            html += '<td class="imgs"><div><img src="'+list[i].pictureUrl+'"></div></td>';
            html += '<td>'+format(list[i].sytTime)+'</td>';
            html += '<td>'+judge(list[i].sysUser)+'</td>';
            html += '<td>';
            html += '<a href="javascript:deleteData(\''+list[i].newsId+'\');">删除</a>';
            html += '<a href="/usercenter/publishArticle?id='+list[i].newsId+'">修改</a>';
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
        function judge(user){
            if(user == ""||user == null){
                return "-";
            }
            return user;
        }
        $('.article-box .order-items tbody').html(html);
    }
}



