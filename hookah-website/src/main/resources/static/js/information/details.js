$.getUrlParam = function (key) {
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
    var result = window.location.search.substr(1).match(reg);
    return result ? decodeURIComponent(result[2]) : null;
};
var id = $.getUrlParam('newsId');
renderDetails();
function renderDetails(){
    $.ajax({
        url:'/sysNews/details',
        type:'get',
        data:{
            id:id
        },
        success:function(data){
            if(data.code == "1") {
                $('#newsTitle').html(data.data.newsTitle);
                $('#newsAdminName').html(data.data.sysUser);
                $('#newsTime').html(format(data.data.sytTime));
                $('#content').html(data.data.content);
                $('#userImg').attr('src',data.data.headImg);
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
            }else{
                console.log(data.message);
            }
        }
    });
}


