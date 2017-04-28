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
                $('#newsAdminName').html(data.data.userName);
                $('#newsTime').html(format(data.data.sytTime));
                $('#content').html(data.data.content);
                $('#userImg').attr('src',data.data.headImg);
            }else{
                console.log(data.message);
            }
        }
    });
}


