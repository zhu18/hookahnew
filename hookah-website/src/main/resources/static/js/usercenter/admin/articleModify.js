/**
 * Created by wcq on 2017/4/19.
 */
var getImg = "";
var id = $.getUrlParam('id');//文章的id
var editor = null;
function createEditor() {
    //富文本
    editor = new wangEditor('content');
    //上传图片（举例）
    editor.config.uploadImgUrl = host.static+'/upload/wangeditor';
    editor.config.uploadImgFileName = 'filename';
    //关闭菜单栏fixed
    editor.config.menuFixed = false;
    editor.config.menus = $.map(wangEditor.config.menus, function (item, key) {
        if (item === 'location'|| item === 'video') {
            return null;
        }
        return item;
    });
    editor.create();
}

if (id) {
    $.ajax({
        type: "get",
        url: "/sysNews/detailsAjax",
        data: {id: id},
        success: function (data) {
            $("#newsGroup").val(data.data.newsGroup);//文章的一级分类
            if(data.data.newsGroup == 'information'){
				$("#newsSonGroup").val(data.data.newsSonGroup).show();
				$('.select-line').show();
            }
            $("#newsTitle").val(data.data.newsTitle);//文章的标题
            $("#newsInfo").val(data.data.contentValidity);//文章的标题
            $("input[name='isHot'][value=" + data.data.isHot + "]").attr("checked", true);
            $("#preview-img").attr('src', data.data.pictureUrl);
            $(".pulish-btn button").addClass("btn-full-blue");
            // $("#content").html(data.data.content);
            // alert(data.data.content);
			$('#showcontent').html(getLength($("#newsTitle").val()));
			$('#showcontent2').html(getLength($("#newsInfo").val()));
            createEditor();
			editor.$txt.html(data.data.content);
            getImg = data.data.pictureUrl;
        }
    })
}
var imgSrc = '';
//上传图片

//选择文件之后执行上传
var fileUploadUrl = host.static + '/upload/fileUpload';
$('#filename').fileupload({
    url: fileUploadUrl,
    dataType: 'json',
	add: function (e, data) {
		var filesize = data.files[0].size;
		if(Math.ceil(filesize / 1024) > 1024*5){
			console.log('文件过大'+filesize);
			$.alert('文件过大');
			return;
		}
		data.submit();
	},
    done: function (e, data) {
        if (data.result.code == 1) {
            var obj = data.result.data[0];
            $("#preview-img").attr("src", obj.absPath);
            imgSrc = obj.absPath;
			getImg = obj.absPath;
        } else {
            $.alert(data.result.message);
        }
    },
    progressall: function (e, data) {

    }
});
function changes(that) {
    var thisVal = $(that).val();
    $('.select-hide').hide();
    if (thisVal == 'information') {
        //    立即发表
        $('#newsSonGroup').show();
        $('.select-line').show();
    } else {
        $('#newsSonGroup').hide();
        $('.select-line').hide();
    }

}
function published() {
    var data = {};
    data.newsId = id;
    data.newsGroup = $('#newsGroup').val();
    data.newsTitle = $('#newsTitle').val();
    data.contentValidity = $('#newsInfo').val();
    data.isHot = $("input[name='isHot']:checked").val();
    data.content = $('#content').val();
    data.newsSonGroup = $('#newsSonGroup').val();
    data.pictureUrl = getImg;
    if (data.newsGroup == 0) {
        $('#newsGroup').focus();
        $('#newsGroup').parents().siblings('.errors').show();
        return;
    } else if (data.newsGroup === 'information') {
        if (data.newsSonGroup == 0) {
            $('#newsSonGroup').focus();
            $('#newsSonGroup').parents().siblings('.errors').show();
            return;
        }
    }
    if (data.newsTitle == "" || data.newsTitle == null) {
        $('#newsTitle').focus();
        return;
    } else if (data.content == "") {
        $.alert('请输入文章内容！', true, function () {
        });
        return;
    } else if (data.pictureUrl == "") {
        $.alert('请上传图片！', true, function () {
        });
        return;
    } else {
        $.ajax({
            type: "POST",
            url: '/sysNews/update',
            data: data,
            success: function (msg) {
                if (msg.code == 1) {
                    $.alert('提交成功');
                    // window.location.href="/admin/articleManage";
                } else {
                    $.alert(msg.message);
                }
            }
        });
    }
}

//     文章内容预览
$('#preview-content').click(function () {
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
    if (content == "") {
        $.alert('请输入文章内容！', true, function () {
        })
    } else {
        $('body').append(html);
    }
    $('#close').click(function () {
        $('#preview-wrap').remove();
    });
});
//替换图片的隐显
$('#preview-div').hover(function () {
    if ($('#preview-img').attr('src')) {
        $('#replace-btn').toggle();
    }
});
// $(function(){
//     $("#newsTitle").bind("input propertychange",function(){
//         var len = $.trim($("#newsTitle").val().length);
//         $(".input-count strong").text(len);
//         if(len>60){
//             var value =$("#newsTitle").val();
//             $("#newsTitle").val(value.substring(0,60));
//             $(".input-count strong").text(60);
//         }
//     })
//     $("#newsInfo").bind("input propertychange",function(){
//         var len = $.trim($("#newsInfo").val().length);
//         $(".input-count .info").text(len);
//         if(len>60){
//             var value =$("#newsInfo").val();
//             $("#newsInfo").val(value.substring(0,60));
//             $(".input-count .info").text(60);
//         }
//     })
// })





$("#publishArticle").validate({
	rules: {
		newsGroup: 'required',
		newsSonGroup: 'required',
		newsTitle:{
			required: true,
			isNewsName:true
		},
		newsInfo:{
			required: true,
			isNewsBrief:true
		},
		goodsImges:{
			required: true
		}
	},
	messages: {
		newsGroup: {
			required: '请选择文章分类'
		},
		newsSonGroup: {
			required: '请选择文章分类'
		},
		newsTitle:{
			required: '请输入文章标题'
		},
		newsInfo:{
			required: '请选择文章简介'
		},
		goodsImges:{
			required: '文章图片必须上传'
		}

	},
	showErrors: function (errorMap, errorList) {
		if (errorList.length) {
			errorList[0].element.focus();
		}
		this.defaultShowErrors();
	}
});
function getLength(str){
	return str.replace(/[\u0391-\uFFE5]/g,"aa").length;
}
$('#newsTitle').on('input onporpertychange',function () {
	$('#showcontent').html(getLength($(this).val()));
});
$('#newsInfo').on('input onporpertychange',function () {
	$('#showcontent2').html(getLength($(this).val()));
});
$.validator.addMethod("isNewsName", function(value, element) {
	var len = value.replace(/[\u0391-\uFFE5]/g,"aa").length;
	return this.optional(element) || (10 <= len && len <= 60);
}, "长度为10-60个字符（每个汉字为2个字符）");
$.validator.addMethod("isNewsBrief", function(value, element) {
	var len = value.replace(/[\u0391-\uFFE5]/g,"aa").length;
	return this.optional(element) || (30 <= len && len <= 300);
}, "长度为30-400个字符（每个汉字为2个字符）");
$('#submit-article').click(function () {
	if ($("#publishArticle").valid()) {
		if($('#content').val() && $('#content').val() != '<p><br></p>'){
			published()
		}else{
			$.alert('文章描述不能为空',true,function () {

			})

		}
	}
});
