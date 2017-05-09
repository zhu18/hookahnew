/**
 * Created by wcq on 2017/5/4.
 */
$(function(){
$(".button").on("click",userAuth);
$("#userCode").on("blur",checkIDCard);
$("#zform").on("click", upPhoto);
$("#fform").on("click", upPhoto);

});
var idcardcheck=false;
function userAuth(){
   if($("#userName").val()==''){
       swal("请输入您的姓名！");
       return;
   }
    if(!idcardcheck){
        swal("请验证您的身份证号！");
        return;
    }
    $.ajax({
            url : "/auth/personAuth",
            data : {
                "realName":$("#userName").val(),
                "cardNum":$("#userCode").val(),
                "identityCardFrontPath":$("#zm").attr("src"),
                "identityCardReversePath":$("#fm").attr("src")
            },
            type:"post",
            success : function(data) {
                if (data.code == 1) {
                    window.location.href = './user_auth_init_step4.html';
                } else {
                    alert(data.errMsg);
                }
            }
        });


}
//验证身份证号
function checkIDCard(){
    var idcard = $("#userCode").val();
    if(idcard==null||idcard==""){
        swal("请输入身份证号！");
        return ;
    }
    if(idcard.length<18){
        swal("请输入正确的身份证编码！");
        return ;
    }
    idcardcheck=true;
}
var imgSrc = '';
var fileUploadUrl = host.static+'/upload/fileUpload';
function upPhoto(){
    $(this).find("input").fileupload({
        url: fileUploadUrl,
        dataType: 'json',
        done: function (e, data) {
            if (data.result.code == 1) {
                var obj = data.result.data[0];
                $(this).siblings("img").attr("src", obj.absPath);
                imgSrc = obj.absPath;
            } else {
                $.alert(data.result.message)
            }

        },
        progressall: function (e, data) {

        }
    })
}
