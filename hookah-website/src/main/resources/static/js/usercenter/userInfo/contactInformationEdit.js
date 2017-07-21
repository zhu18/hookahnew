/**
 * Created by lss on 2017/7/13 0013.
 */

$(function () {
    // 回现
    $.ajax({
        url:host.website+'/supplier/getContactInfo',
        data:{},
        type:'get',
        success:function (data) {
            var contactName=data.data.contactName?data.data.contactName:"";
            var contactPhone=data.data.contactPhone?data.data.contactPhone:"";
            var contactAddress=data.data.contactAddress?data.data.contactAddress:"";
            var postCode=data.data.postCode?data.data.postCode:"";
            $("#contactName").val(contactName);
            $("#contactPhone").val(contactPhone);
            $("#contactAddress").val(contactAddress);
            $("#postCode").val(postCode);
        }
    });

    //让当前表单调用validate方法，实现表单验证功能
    $("#ff").validate({
        debug:true, //调试模式，即使验证成功也不会跳转到目标页面
        rules:{     //配置验证规则，key就是被验证的dom对象，value就是调用验证的方法(也是json格式)
            sname:{
                required:true,  //必填。如果验证方法不需要参数，则配置为true
                rangelength:[2,12]
            },
            sphone:{
                required:true
            },
            saddress:{
                required:"",
                maxlength:50
            },
            szip:'required'
        },
        messages:{
            sname:{
                required:"*请输入用户名",
                rangelength:$.validator.format("*用户名长度为{0}-{1}个字符"),
                remote:"*该用户名已存在！"
            },
            sphone:{
                required:"*请输入手机号"
            },
            address:{
                required:"*请输入地址",
                maxlength: $.validator.format("请输入一个长度最多是 {0} 的字符串")
            },
            szip:"*请填写邮编"
        }
    });
    $(".save").on("click",function () {
        if($("#ff").valid()){
            $.ajax({
                url:host.website+'/supplier/updateContactInfo',
                data:{
                    contactName:$("#contactName").val(),
                    contactPhone:$("#contactPhone").val(),
                    contactAddress:$("#contactAddress").val(),
                    postCode:$("#postCode").val()
                },
                type:'post',
                success:function (data) {
                    if(data.code == "1"){
                        $.alert('保存成功',true,function(){
                            window.location.href= host.website+"/usercenter/contactInformation"
                        });
                    }
                }
            });
        }

    });
})
