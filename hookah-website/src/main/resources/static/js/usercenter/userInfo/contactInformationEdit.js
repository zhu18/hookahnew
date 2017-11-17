/**
 * Created by lss on 2017/7/13 0013.
 */

$(function () {
    // 回显
    $.ajax({
        url:host.website+'/supplier/getContactInfo',
        data:{},
        type:'get',
        cache:false,
        success:function (data) {
            var contactName=data.data.contactName?data.data.contactName:"";
            var contactPhone=data.data.contactPhone?data.data.contactPhone:"";
            var contactAddress=data.data.contactAddress?data.data.contactAddress:"";
            var postCode=data.data.postCode?data.data.postCode:"";
            var email=data.data.email?data.data.email:"";
            $("#contactEmail").val(email);
            $("#contactName").val(contactName);
            $("#contactPhone").val(contactPhone);
            $("#contactAddress").val(contactAddress);
            $("#postCode").val(postCode);
        }
    });
    var regex = {  //手机号验证正则
        mobile: /^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$/,
        zip: /^[0-9]{6}$/,
        emial:/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
    };
    //让当前表单调用validate方法，实现表单验证功能
    $("#ff").validate({
        debug:true, //调试模式，即使验证成功也不会跳转到目标页面
        rules:{     //配置验证规则，key就是被验证的dom对象，value就是调用验证的方法(也是json格式)
            sname:{
                required:true,  //必填。如果验证方法不需要参数，则配置为true
                rangelength:[2,12]
            },
            sphone:{
                required:true,
                isMobile:true
            },
            saddress:{
                required:true,
                maxlength:50
            },
            semail:{
                required:true,
                 maxlength:50,
                isEmail:true
            },
            szip:{
                required:true,
                iszip:true
            }
        },
        messages:{
            sname:{
                required:"*请输入联系人",
                rangelength:$.validator.format("*用户名长度为{0}-{1}个字符"),
                remote:"*该联系人已存在！"
            },
            sphone:{
                required:"*请输入手机号",
                isMobile:"*请输入正确格式的手机号"
            },
            semail:{
                required:"*请输入电子邮箱",
                isEmail:"*请输入正确格式的电子邮箱",
                maxlength: $.validator.format("请输入一个长度最多是 {0} 的字符串")
            },
            address:{
                required:"*请输入地址",
                maxlength: $.validator.format("请输入一个长度最多是 {0} 的字符串")
            },
            szip:{
                required:"*请输入邮编",
                isMobile:"*请输入正确格式的邮编"
            }
        }
    });
    $.validator.addMethod("isMobile", function(value, element) {
        var mobile = regex.mobile.test(value);
        return this.optional(element) || (mobile);
    }, "*请填写有效的手机号");
    $.validator.addMethod("iszip", function(value, element) {
        var mobile = regex.zip.test(value);
        return this.optional(element) || (mobile);
    }, "*请填写有效的邮编号码");
    $.validator.addMethod("isEmail", function(value, element) {
        var emial = regex.emial.test(value);
        return this.optional(element) || (emial);
    }, "*请填写有效的电子邮箱");
    $(".save").on("click",function () {
        if($("#ff").valid()){
            $.ajax({
                url:host.website+'/supplier/updateContactInfo',
                data:{
                    contactName:$("#contactName").val(),
                    contactPhone:$("#contactPhone").val(),
                    contactAddress:$("#contactAddress").val(),
                    postCode:$("#postCode").val(),
                    contactEmail:$("#contactEmail").val()
                },
                type:'post',
                success:function (data) {
                    if(data.code == "1"){
                        $.alert('保存成功',true,function(){
                            window.location.href= host.website+"/usercenter/contactInformation"
                        });
                    }else{
                        $.alert(data.message);
                    }
                }
            });
        }

    });
})
