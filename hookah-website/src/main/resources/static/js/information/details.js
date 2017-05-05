// details详情页
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
                if(data.data.newsGroup=="information"){
                    $(".detail .top .text1").text("资讯中心");
                }
                if(data.data.newsSonGroup =="1"){
                    $(".detail .top .text2").text("中心动态");
                }else if(data.data.newsSonGroup =="2"){
                    $(".detail .top .text2").text("行业资讯");
                }else {
                    $(".detail .top .text2").text("中心公告");
                }

            }else{
                console.log(data.message);
            }
        }
    });
}