var getImg = "";
$.getUrlParam = function (key) {
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
    var result = window.location.search.substr(1).match(reg);
    return result ? decodeURIComponent(result[2]) : null;
};
var id = $.getUrlParam('id');
function createEidtor(){
    //富文本
    var editor = new wangEditor('content');
    //上传图片（举例）
    editor.config.uploadImgUrl = '/upload';
    //关闭菜单栏fixed
    editor.config.menuFixed = false;
    editor.config.menus = $.map(wangEditor.config.menus, function(item, key) {
        if (item === 'location') {
            return null;
        }
        if (item === 'video') {
            return null;
        }
        return item;
    });
    editor.create();
}
if(id){
    $(".user-center-box .center-title").text("编辑文章");
    $.ajax({
        type:"get",
        url:"/sysNews/details",
        data:{ id: id},
        success:function(data){
            $("#newsGroup").val(data.data.newsGroup);
            $("#newsTitle").val(data.data.newsTitle);
            $("input[name='isHot'][value="+data.data.isHot+"]").attr("checked",true);
            $("#preview-img").attr('src',data.data.pictureUrl);
            $("#content").html(data.data.content) ;
            createEidtor();
            getImg = data.data.pictureUrl;
        }
    })
}else{
    createEidtor();
}

var imgSrc = '';
//上传图片

//选择文件之后执行上传
$('#preview-div').click(function(){
    if($('#preview-img').attr('src')){
        console.log('ysessss');
        $('#filename').val("");
        sda();
    }else{
        console.log("nononono");
        sda();
    }
    function sda(){
        $('#filename').on('change', function() {
            $.ajaxFileUpload({
                url:'/upload/fileUpload',
                secureuri:false,
                fileElementId:'filename',//file标签的id
                dataType: 'json',//返回数据的类型
                success: function (data, status) {
                    //把图片替换
                    var obj= data.data[0];
                    $("#preview-img").attr("src", "http://"+obj.absPath);
                    imgSrc = obj.absPath;
                    if(typeof(data.error) != 'undefined') {
                        if(data.error != '') {
                            alert(data.error);
                        } else {
                            alert(data.msg);
                        }
                    }
                },
                error: function (data, status, e) {
                    alert(e);
                }
            });
        });
    }
})
function changes(that){
    var thisVal = $(that).val();
    $('.select-hide').hide();
    if(thisVal == 'information'){
        //    立即发表
        $('#newsSonGroup').show();
        $('.select-line').show();
    }else{
        $('#newsSonGroup').hide();
        $('.select-line').hide();
    }
//        if($(that).val() == 0){
//            $(that).parents().siblings('.errors').show();
//        }else{
//            $(that).parents().siblings('.errors').hide();
//        }
}

function published(){
    var data = {};
    var url = '';
    data.newsGroup = $('#newsGroup').val();
    data.newsTitle = $('#newsTitle').val();
    data.isHot = $("input[name='isHot']:checked").val();
    data.content = $('#content').val();
    data.newsSonGroup = $('#newsSonGroup').val();
    if (id){
        data.pictureUrl = getImg;
        url = "/sysNews/update";
        data.newsId = id;
    }else{
        data.pictureUrl = imgSrc;
        url = "/sysNews/insert";
    }
    if(data.newsGroup == 0){
        $('#newsGroup').focus();
        $('#newsGroup').parents().siblings('.errors').show();
        return;
    }else if(data.newsGroup === 'information'){
        if(data.newsSonGroup == 0){
            $('#newsSonGroup').focus();
            $('#newsSonGroup').parents().siblings('.errors').show();
            return;
        }
    }
    if(data.newsTitle == "" || data.newsTitle == null){
        $('#newsTitle').focus();
        return;
    }else if(data.content == "") {
        $.alert('请输入文章内容！',true,function(){ })
        return;
    }else if(data.pictureUrl == "") {
        $.alert('请上传图片！',true,function(){ })
        return;
    }else{
        if(id){
            ajaxFun(id,url)
        }else{
            ajaxFun(id,url)
        }
    }
    function ajaxFun(id,url){
        $.ajax({
            type: "POST",
            url: url,
            data: data,
            success: function(msg){
                if(msg.code == 1){
                    $.alert('修改成功')
                }else{
                    $.alert(msg.message)
                }
            }
        });
    }
}

//     文章内容预览
$('#preview-content').click(function(){
    var content = $('#content').val();
    var html = '';
    html += '<div id="preview-wrap" class="preview-wrap" style="display: block;">';
    html += '<div class="preview-box">';
    html += '<h3 class="preview-title">预览<span id="close">x</span></h3>';
    html += '<div class="preview-content">';
    html += content;
    html += '</div>';
    html += '</div>';
    html += '</div>';
    if(content == ""){
        $.alert('请输入文章内容！',true,function(){})
    }else{
        $('body').append(html);
    }
    $('#close').click(function(){
        $('#preview-wrap').remove();
    })
})
//替换图片的隐显
$('#preview-div').hover(function(){
    if($('#preview-img').attr('src')){
        $('#replace-btn').toggle();
    }
})