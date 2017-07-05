/**
 * Created by wcq on 2017/5/8.
 */
$(function(){
    //地域加载
    var regionParam = 100000;
    loadRegion('province', regionParam); //加载地域
});
$('.upLoad').mouseover(function(){
    $(this).children('.upLoad_warp').show();
});
$('.upLoad').mouseleave(function(){
	$(this).children('.upLoad_warp').hide();
});
//地域加载
function loadRegion(id,regionParam) {
    var parentId = '';
    if(regionParam == 100000){
        parentId = 100000;
    }else{
        parentId = $(regionParam).val();
    }
    $(regionParam).nextAll().html('<option value="-1"></option>')
    $.ajax({
        type: "get",
        url: "/region/getRegionCodeByPid",
        data: {
            parentId: parentId
        },
        success: function (data) {
            if (data.code == 1) {
                if(data.data.length > 0){
                    renderRegion(id,data.data)
                }
            } else {
                $.alert(data.message)
            }
        }
    });
}
function renderRegion(id,data){
    var html = '<option value="">全部</option>';
    data.forEach(function(e){
        html += '<option value="'+e.id+'">'+e.name+'</option>';
    });
    $('#'+id).html(html);
}

//上传图片
var fileUploadUrl = host.static+'/upload/fileUpload';
$('.unloadBtn').fileupload({
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
            console.log(2);
            var obj = data.result.data[0];
            $(this).parent().siblings("img").attr("src", obj.absPath);
            $(this).parent().siblings(".input_hide").val(obj.filePath);
            $(this).parent().siblings(".input_op").val(obj.filePath);
            $(this).parent().siblings(".upLoad_warp").html('点击替换');
        } else {
            $.alert(data.result.message)
        }

    },
    progressall: function (e, data) {

    }
});
function companyAuth(){
    $.ajax({
        url : "/auth/orgAuth",
        data : {
            "orgName":$("input[name='governmentName']").val(),//政府全称
            "taxCode":$("input[name='taxRegCertificate']").val(),//税务登记编号
            "licenseCode":$("input[name='businessLicence']").val(),//营业执照编号
            "certificateCode":$("input[name='creditCode']").val(),//信用代码
            "taxPath":$("input[name='taxPath']").val(),//税务登记存放路径
            "licensePath":$("input[name='licensePath']").val(),//营业执照存放路径
            "certifictePath":$("input[name='certifictePath']").val(),//企业代码存放路径
            "lawPersonName":$("input[name='companyLegal']").val(),//企业法人代表
            "region":$('select[name="city"] option:selected').val(),//所在地
            "contactAddress":$("input[name='address']").val(),//详细地址
            "orgPhone":$("input[name='tel']").val(),//联系电话
            "industry":$("input[name='mainBusiness']").val(),//行业
        },
        type:"post",
        success : function(data) {
            if (data.code == 1) {
                window.location.href = './company_auth_init_step3.html';
            } else {
                alert(data.errMsg);
            }
        }
    });
}
$("#companyForm").validate({
	rules: {
		governmentName:'required',
		// creditCode:'required',
		// businessLicence:'required',
		// taxRegCertificate:'required',
		companyLegal:'required',
		mainBusiness:'required',
		// province:'required',
		city:'required',
		address:'required',
		// licensePath:'required',
		// taxPath:'required',
		// certifictePath:'required',
		tel:{
		    required:true,
			isMobile:true
		}
	},
	messages: {
		governmentName:'单位名称不能为空',
		creditCode:'信用代码不能为空',
		businessLicence:'营业执照不能为空',
		taxRegCertificate:'不能为空',
		companyLegal:'法人代表不能为空',
		mainBusiness:'主营业务不能为空',
		// province:'所在地区不能为空',
		city:'所在地区不能为空',
		address:'详细地址不能为空',
		licensePath:'营业执照必须上传',
		taxPath:'税务登记证必须上传',
		certifictePath:'企业代码证必须上传',
		tel:{
			required:'手机号不能为空',
			isMobile:'手机号格式不正确'
		}
	},
	showErrors:function(errorMap,errorList) {
		if(errorList.length){
			errorList[0].element.focus();
		}
		this.defaultShowErrors();
	}
});
$.validator.addMethod("isMobile", function(value, element) {
    var reg = /^1[3|4|5|7|8][0-9]{9}$/;
	var tel = value;
	return this.optional(element) || (reg.test(tel));
}, "长度为10-60个字符（每个汉字为2个字符）");
$('#verifyBtn').click(function(){
	if($("#companyForm").valid()){
		companyAuth();
	}
});
