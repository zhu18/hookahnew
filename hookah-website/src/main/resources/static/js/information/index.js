function loadPageData(data){ //渲染页面数据
    // return console.log(JSON.stringify(data))

    if(data.data.list){
        var list = data.data.list;
        var html = '';
        for(var i=0; i<list.length; i++){
            html += '<div class="public padding-left-10 padding-top-30 padding-bottom-30 ">';
            html += '<a href="/information/details?newsId='+list[i].newsId+'">';
            html += '<img src="'+list[i].pictureUrl+' alt="" class="margin-right-20 news-img">';
            html += '</a>';
            html += '<a href="/information/details?newsId='+list[i].newsId+'">';
            html += '<p class="margin-bottom-10 padding-right-50">'+list[i].newsTitle+'</p>';
            html += '</a>';
            html += '<a href="/information/details?newsId='+list[i].newsId+'">';
            html += '<p class="padding-right-50 margin-bottom-10">2017大数据标准化论坛喜讯，贵阳大数据交易所荣获“全国信标委大数据标准工作组2016年优秀单位”。此荣誉既肯定了交易所以往业绩，又鼓励了交易所未来发展。按照国务院、工信部、贵阳市等的统筹规划，贵阳大数据交易所将积极参建我国大数据交易标准。</p>';
            html += '</a>';
            html += '<div class="auth">';
            html += '<img src="/static/images/auth.png" alt="">&nbsp;&nbsp;&nbsp;<span style="color:#B2B2B2;" class="padding-right-20">李治国</span>';
            html += '<span class="padding-right-10"><svg class="icon" style="color:#D6D6D6;width: 1.4em; height: 1.4em;vertical-align: middle;fill: currentColor;overflow: hidden;" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="14461"><path d="M512 64C264.96 64 64 264.96 64 512s200.96 448 448 448 448-200.96 448-448S759.04 64 512 64z m159.264 512H480.16c-17.664 0-32.16-14.464-32.16-32.16V289.088c0-17.664 14.336-32 32-32s32 14.336 32 32V512h159.264c17.696 0 32 14.336 32 32 0 17.696-14.336 32-32 32z" p-id="14462"></path></svg></span>';
            html += '<span style="color:#B2B2B2;">'+format(list[i].sytTime)+'</span>';
            html += '</div>';
            html += '<div class="line"></div>';
            html += '</div>';
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
        $('.public-box').html(html);
    }
}

function renderChange(that,num){
    $(that).addClass('active').siblings().removeClass('active');
    dataParm.newsSonGroup=num;
    goPage("1");
}

