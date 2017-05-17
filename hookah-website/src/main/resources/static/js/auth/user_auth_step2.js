/**
 * Created by wcq on 2017/5/4.
 */
$(function(){
$(".button").on("click",userAuth);
$("#userCode").on("blur",checkIDCard);
$("#userName").on("blur",checkName);
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
    if($("#zm").attr("src")=="/static/images/auth/z.png" || $("#fm").attr("src")=="/static/images/auth/f.png" ){
        swal("请上传您的证件号！");
        return;
    }
    if(!$("#clickCheck").is(":checked")){
        swal("请点击已阅读并同意！");
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
//验证姓名
function checkName() {
    var name = $("#userName").val();
    var reg=/[\u4E00-\u9FA5]{2,10}(?:·[\u4E00-\u9FA5]{2,10})*/
    if(name==null||name==""){
        swal("请输入姓名！");
        return ;
    }
    if(!reg.test(name)){
        swal("请输入正确的姓名！");
        return ;
    }
}
//验证身份证号
function checkIDCard(){
    var idcard = $("#userCode").val();
    var reg=/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    if(idcard==null||idcard==""){
        swal("请输入身份证号！");
        return ;
    }
    if(!reg.test(idcard)){
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
                console.log(2);
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
