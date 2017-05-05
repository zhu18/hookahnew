/**
 * Created by wcq on 2017/5/4.
 */
$(function(){
$(".button").on("click",userAuth);
$("#userCode").on("blur",checkIDCard);
$("#submit1").on("change", upPhoto);
$("#submit2").on("change", upPhoto);

});
var idcardcheck=false;
function userAuth(){
   if($("#userName").val()==''){
       alert("请输入姓名");
       return;
   }
    if(!idcardcheck){
        alert("请验证身份证号");
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
                    window.location.href = './user_auth_init_step3.html';
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
        alert("请输入身份证号");
        return ;
    }
    if(idcard.length<18){
        alert("请输入正确的身份证编号");
        return ;
    }
    idcardcheck=true;
}
var upinput;
var imgSrc = '';
//选择文件之后执行上传
var fileUploadUrl = host.static+'/upload/fileUpload';
function upPhoto() {
    upinput=$(this);
    console.log($(this));
    $(this).parent().ajaxSubmit({
        type : "post",
        url: fileUploadUrl,
        dataType: "json",
        // contentType: "mutiple;charset=UTF-8",
        success: function (e, data) {
            console.log(111);
            if (data.code == 1) {
                 imgSrc = data.data[0].absPath;
                if ($(upinput).attr("id") == "submit1") {
                    $("#zm").attr("src", imgSrc);
                    // var fileObj = $("#zm");
                    // $("#zm").attr("src", window.URL.createObjectURL($(upinput).get(0).files[0]));
                // } else {
                    $("#fm").attr("src", imgSrc);
                    // $("#fm").attr("src", window.URL.createObjectURL($(upinput).get(0).files[0]));
                }
            }else{
                $.alert(data.message);
            }
        }
        // progressall: function (e, data) {
        //
        // }
    });
}


