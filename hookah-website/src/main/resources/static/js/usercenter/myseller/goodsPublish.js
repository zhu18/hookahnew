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
    $('#pusblishBtn').click(function(){
        var category = $('.clearfix-content').text();
        window.location.href = "/usercenter/goodsEdit?catId="+pid+"&category="+category;
    })
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
$("#input-search").on("input propertychange", function () {
    if ($(this).val() == "") {
        $('.search-list').hide();
    } else {
        getSearchList($(this).val())
    }
});
$("#input-search").on("input focus", function () {
    if ($(this).val()) {
        if ($('.search-list li').length <= 0){
            getSearchList($(this).val())
        }else{
            $('.search-list').show();
        }
    }
    $(this).keydown(function (event) {
        if (event.keyCode == 40) {
            $(this).blur();
            $('.search-list').show().addClass('keyActive');
        } else if (event.keyCode == 13) {
            $("#input-search").blur();
            window.open(host.website+'/exchange/search?names=' + $(this).val());
            return;
        }
    });
});
$('#btn-search').click(function(){
    if($("#input-search").val().length > 0){
        $("#input-search").blur();
        window.open(host.website+'/exchange/search?names=' + $("#input-search").val());
        return;
    }
});
var ds = -1;
$(document).keydown(function (event) {
    if ($('.search-list').hasClass('keyActive')) {
        if($('.search-list li').length <= 0){
            $('.search-list').hide();
        }
        if(!$("#input-search").val()){
            $("#input-search").focus();
            return;
        }
        var itemLen = $('.search-list li').length - 1;
        if (event.keyCode == 40) {
            event.preventDefault();
            ds +=1;
            if(ds > itemLen){
                ds = itemLen;
            }
            $('.search-list li').eq(ds).addClass('current').siblings().removeClass('current')
        } else if (event.keyCode == 38) {
            ds -=1;
            if(ds <= -1){
                ds = -1;
                $('.search-list li').removeClass('current');
                $("#input-search").focus();
                return;
            }
            $('.search-list li').eq(ds).addClass('current').siblings().removeClass('current')
        } else if (event.keyCode == 13) {
            $('.search-list li').each(function(){
                if($(this).hasClass('current')){
                    $("#input-search").blur();
                    window.open($(this).children('a').attr('href'));
                    return;
                }
            })
        }else{
            event.initEvent("keydown", true, true);
        }
    }
});
$("#input-search").on("input blur", function () {
    if (!$('.search-list').hasClass('active')) {
        $('.search-list').hide();
    }
});
$('.search-list').on('click', function () {
    $("#input-search").focus();
});
$('.search-list').hover(function () {
    $(this).toggleClass('active');
});
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
        console.log(list);
        html += '<li><a target="_blank" href="'+host.website+'/exchange/search?names=' + list.catName + '">' + list.catName + '</a></li>';
    });
    $('.search-list').show().children('ul').html(html);
}
// 类目搜索结束