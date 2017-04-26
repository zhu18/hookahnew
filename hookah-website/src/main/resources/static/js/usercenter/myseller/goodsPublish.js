var url = "/category/findByPId/1";
var pid = 0;
var datas = null ;
var nextAll = '';
var isTrue = false;

//

loadData(url,pid);
//点击加载。。。
function clickLoadData(url,pid,that){
    $(that).parents(".select-category-item").nextAll(".select-category-item").remove();
    $(that).addClass('active').siblings().removeClass('active');
    loadData(url,pid);
    currentCategory(that);
    nextAll = $(that).parents(".select-category-item").nextAll(".select-category-item");
    pusblishBtn();
}
function currentCategory(that){
    var indexs = $(that).parents(".select-category-item").index();
    if(indexs == 0){
        $('.clearfix-content > .clearfix-box').remove();
        $('.clearfix-content').append('<span class="clearfix-box clearfix-box-'+indexs+'">'+$(that).text()+'</span>');
    }else{
        $('.clearfix-content').children('.clearfix-box-'+indexs).remove();
        $('.clearfix-content').append('<span class="clearfix-box clearfix-box-'+indexs+'"> > '+$(that).text()+'</span>');
    }
}

function showBtn(isTrue){
    if(isTrue == true){
        $('#pusblishBtn').css({'cursor':'no-drop'}).removeClass('btn-full-orange').attr({'disabled':'disabled'});
    }else{
        $('#pusblishBtn').css({'cursor':'pointer'}).addClass('btn-full-orange').removeAttr('disabled');
    }
}


//加载。。。
function loadData(url,pid){
    $.ajax({
        type: "get",
        url: url,
        data: {
            pid:pid
        },
        success: function(msg){
            if(msg.code == 1){
                datas = msg.data;
                if(datas.length > 0){
                    var html = '';
                    html += '<div class="select-category-item grid-left">';
                    html += '<div class="item-box">';
                    html += '<dl>';
                    for(var i = 0; i < datas.length; i++){
                        html += '<dd onclick="clickLoadData(url,'+datas[i].catId+',this)">'+datas[i].catName+'</dd>';
                    }
                    html += '</dl>';
                    html += '</div>';
                    html += '</div>';
                    $('.select-category-box').append(html);
                    isTrue = true;
                }else{
                    isTrue = false;
                }
            }else{
                $.alert(msg.message)
            }
            showBtn(isTrue)
        }
    });
};

// 类目搜索
list();
function list(){
    $(".search-list ul").on('click','li',function(){
        console.log("text:"+$(this).text());
        $(this).children('label').addClass('current').end().siblings().children('label').removeClass('current');
        $(this).addClass("current").siblings().removeClass("current");
        $('.clearfix-content').html($(this).text());
        showBtn(false);
        pusblishBtn();
    });
}
$('#btn-search').click(function(){
    showBtn(true);
    $(".clearfix-content").html("");
    if($("#input-search").val().length > 0){
        getSearchList($("#input-search").val());
        $('.search-list').show();
        $(".select-category-box").hide();
    }
});

function pusblishBtn(){
    $('#pusblishBtn').click(function(){
        var category = $('.clearfix-content').text();
        window.location.href = "/usercenter/goodsEdit?catId="+pid+"&category="+category;
    });
}

function getSearchList(text){
    $.ajax({
        type:'get',
        url:'/search/v1/category',
        data:{
            keyword:text
        },
        success:function(data){
            if(data.code == 1){
                showBox(data.data);
            }else{
                console.log(data.message);
            }
        }
    })
}
function showBox(data) {
    var html = '';
    data.forEach(function (list) {
        html += '<li><label>' + list.fullName + '</label></li>';
    });
    $('.search-list').show().children('ul').html(html);
}
// 类目搜索结束