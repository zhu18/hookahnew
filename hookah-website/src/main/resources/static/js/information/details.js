// details详情页
var id = $.getUrlParam('newsId');
renderDetails();
var num ='';
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
                    num =1;
                    $(".detail .top .text2").text("中心动态");
                }else if(data.data.newsSonGroup =="2"){
                    num =2;
                    $(".detail .top .text2").text("行业资讯");
                }else if(data.data.newsSonGroup =="3") {
                    num =3;
                    $(".detail .top .text2").text("中心公告");
                }
                isHot();
            }else{
                console.log(data.message);
            }
        }
    });
}

function isHot(){
    $.ajax({
        url:'/sysNews/listByGroup',
        type:'get',
        data:{
            pageNumber:1,
            pageSize:2,
            newsGroup:"information",
            newsSonGroup:num
        },
        success:function(data){
            if(data.code=="1"){
                var html="";
                if (data.data.list.length > 0){
                    var list = data.data.list;
                    for (var i = 0; i < list.length; i++){
                        console.log("hot:"+list[i].isHot);
                        var n;
                        if(list[i].isHot=="1"){
                            n++;
                            // for(var j=0;j<2;j++){
                                var content = list[i].contentValidity ? list[i].contentValidity : '暂无简介';
                                html +='<div class="line"></div>';
                                html +='<a href="javascript:;" class="one">';
                                html +='<img src="'+list[i].pictureUrl+'" alt="" class="margin-bottom-10 margin-top-10">';
                                html +='</a>';
                                html +='<a href="javascript:;" >';
                                html +='<div class="description margin-bottom-10">';
                                html +='<p class="first">'+content+'</p>';
                                html +='</div>';
                                html +='</a>';
                            // }
                            $(".hotBox").html(html);
                        }
                        if(n>2){
                            return;
                        }
                    }
                }
            }
        }
    })
}