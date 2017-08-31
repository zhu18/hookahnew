/**
 * Created by wcq on 2017/5/4.
 */
$(function(){
    $(".button").on("click",userAuth);
    $("#userCode").on("focus",function(){
		$('.code_error_tip').hide();
    });
    $("#userCode").on("blur",checkIDCard);
    $("#userName").on("focus",function () {
        $('.name_error_tip').hide();
	});
    $("#userName").on("blur",checkName);
    $("#zform").on("click", upPhoto);
    $("#fform").on("click", upPhoto);
    $('.submit-file .file_item').mouseover(function(){
        $(this).children('.warp').show();
    })
	$('.submit-file .file_item').mouseleave(function(){
		$(this).children('.warp').hide();
	})
});
var idcardcheck=false;
function userAuth(){
	var name = $("#userName").val();
	var reg=/[\u4E00-\u9FA5]{2,10}(?:·[\u4E00-\u9FA5]{2,10})*/;
	var idcard = $("#userCode").val();
	var reg2 = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;

   if(!name){
       $('.name_error_tip').html("姓名不能为空").show();
       return;
   }else if(!reg.test(name)){
	   $('.name_error_tip').html("姓名不符合要求").show();
	   return;
   }
    if(!idcard){
		$('.code_error_tip').html("身份证号不能为空").show();
        return;
    }else if(!reg2.test(idcard)){
		$('.code_error_tip').html("身份证号不符合要求").show();
		return;
    }
    if(!$("#z_hidVal").val()){
		$('.z_error_tip').html("请上传身份证正面照片或扫描件").show();
		return;
    }
    if(!$("#f_hidVal").val()){
		$('.f_error_tip').html("请上传身份证正背面照片或扫描件").show();
		return;
    }
    $.ajax({
            url : "/auth/personAuth",
            data : {
                "realName":$("#userName").val(),
                "cardNum":$("#userCode").val(),
                "identityCardFrontPath":$("#z_hidVal").val(),
                "identityCardReversePath":$("#f_hidVal").val()
            },
            type:"post",
            success : function(data) {
                if (data.code == 1) {
                    window.location.href = './user_auth_init_step3.html';
                } else {
                    alert(data.errMsg);
                }
            }
        });


}
//验证姓名
function checkName() {
    var name = $("#userName").val();
    var reg=/[\u4E00-\u9FA5]{2,10}(?:·[\u4E00-\u9FA5]{2,10})*/;
    if(name==null||name==""){
		$('.name_error_tip').html("姓名不能为空").show();
        return;
    }
    if(!reg.test(name)){
		$('.name_error_tip').html("姓名不符合要求").show();
        return;
    }
}
//验证身份证号
function checkIDCard(){
    var idcard = $("#userCode").val();
    var reg2 = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    if(!idcard){
		$('.code_error_tip').html("身份证号不能为空").show();
        return;
    }
    if(!reg2.test(idcard)){
		$('.code_error_tip').html("身份证号不符合要求").show();
        return;
    }
}
var fileUploadUrl = host.static+'/upload/fileUpload';
function upPhoto(){
    $(this).find("input").fileupload({
        url: fileUploadUrl,
        dataType: 'json',
        done: function (e, data) {
            if (data.result.code == 1) {
                var obj = data.result.data[0];
                $(this).siblings("img").attr("src", obj.absPath);
                $(this).parent().siblings(".hidVal").val(obj.filePath);
				$(this).parents('.file_item').siblings(".error_tip").hide();

            } else {
                $.alert(data.result.message)
            }
        },
        progressall: function (e, data) {

        }
    })
}
