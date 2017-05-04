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
                "identityCardFrontPath":'',
                "identityCardReversePath":''
            },
            type:'post',
            success : function(data) {
                if (data.retCode == 1) {
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
function upPhoto() {
    upinput=$(this);
    console.log($(this));
    $(this).parent().ajaxSubmit({
        type : "POST",
        url : "/data/account/upPhoto",
        dataType : "json",
        success : function(data) {
            if (data.retCode == 1) {
                var img = data.data;
                if ($(upinput).attr("id") == "submit1") {
                    $("#zpath").val(data.data);
                    //$("#zm").attr("src",img);
                    //var fileObj=$("#zm");
                    $("#zm").attr("src",window.URL.createObjectURL($(upinput).get(0).files[0]));

                } else {
                    $("#fpath").val(data.data);
                    //$("#fm").attr("src",img);
                    $("#fm").attr("src",window.URL.createObjectURL($(upinput).get(0).files[0]));
                }
                // alert();
            } else {
                alert(data.errMsg);
            }
        }
    });
}