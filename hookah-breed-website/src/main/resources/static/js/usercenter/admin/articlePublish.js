createEidtor();
function createEidtor() {
	//富文本
	editor = new wangEditor('content');
	//上传图片（举例）
	editor.config.uploadImgUrl = host.static + '/upload/wangeditor';
	editor.config.uploadImgFileName = 'filename';
	//关闭菜单栏fixed
	editor.config.menuFixed = false;
	editor.config.menus = $.map(wangEditor.config.menus, function (item, key) {
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
	var url = '';
	data.newsGroup = $('#newsGroup').val();
	data.newsTitle = $('#newsTitle').val();
	data.contentValidity = $('#newsInfo').val();
	data.isHot = $("input[name='isHot']:checked").val();
	data.content = $('#content').val();
	data.newsSonGroup = $('#newsSonGroup').val();
	data.pictureUrl = imgSrc;
	url = "/sysNews/insert";
	ajaxFun(url,data)
}
function ajaxFun(url,data) {
	$.ajax({
		type: "POST",
		url: url,
		data: data,
		success: function (msg) {
			if (msg.code == 1) {
				// $.alert('提交成功');
				$.alert({
					content:'提交成功'
				});

				window.location.href = "/admin/articleManage";
			} else {
				// $.alert(msg.message)
				$.alert({
					content:msg.message
				})
			}
		}
	});
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
		// $.alert('请输入文章内容！', true, function () {
		// })
		$.alert({
			content:'请输入文章内容！',
			button:true
		})
	} else {
		$('body').append(html);
	}
	$('#close').click(function () {
		$('#preview-wrap').remove();
	})
});
//替换图片的隐显

$("#publishArticle").validate({
	rules: {
		newsGroup: 'required',
		newsSonGroup: 'required',
		newsTitle: {
			required: true,
			isNewsName: true
		},
		newsInfo: {
			required: true,
			isNewsBrief: true
		},
		goodsImges: {
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
		newsTitle: {
			required: '请输入文章标题'
		},
		newsInfo: {
			required: '请选择文章简介'
		},
		goodsImges: {
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
function getLength(str) {
	return str.replace(/[\u0391-\uFFE5]/g, "aa").length;
}
$('#newsTitle').on('input onporpertychange', function () {
	$('#showcontent').html(getLength($(this).val()));
});
$('#newsInfo').on('input onporpertychange', function () {
	$('#showcontent2').html(getLength($(this).val()));
});
$.validator.addMethod("isNewsName", function (value, element) {
	var len = value.replace(/[\u0391-\uFFE5]/g, "aa").length;
	return this.optional(element) || (10 <= len && len <= 60);
}, "长度为10-60个字符（每个汉字为2个字符）");
$.validator.addMethod("isNewsBrief", function (value, element) {
	var len = value.replace(/[\u0391-\uFFE5]/g, "aa").length;
	return this.optional(element) || (30 <= len && len <= 300);
}, "长度为30-400个字符（每个汉字为2个字符）");
$('#submit-article').click(function () {
	if ($("#publishArticle").valid()) {
		if ($.trim(editor.$txt.text()).length>0) {
			published()
		} else {
			// $.alert('文章描述不能为空', true, function () {
             //    // $('#content').focus()
			// })
			$.alert({
				content:'文章描述不能为空',
				button:true
			})

		}
	}
});








var getImg = "";
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
			// $.alert('文件过大');
			$.alert({
				content:'文件过大'
			});
			return;
		}
		data.submit();
	},
	done: function (e, data) {
		if (data.result.code == 1) {
			var obj = data.result.data[0];
			$("#preview-img").attr("src", obj.absPath);
			$('input[name="goodsImges"]').val(obj.absPath);
			imgSrc = obj.absPath;
		} else {
            $.alert({
                content:data.result.message
            });
		}

	},
	progressall: function (e, data) {

	}
})

$('#preview-div').hover(function () {
	if ($('#preview-img').attr('src')) {
		$('#replace-btn').toggle();
	}
});




