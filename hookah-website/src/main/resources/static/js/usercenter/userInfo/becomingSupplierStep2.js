/**
 * Created by lss on 2017/7/17 0017.
 */
$(function () {
    $.ajax({
        url:host.website+'/supplier/getBaseInfo',
        data:{},
        type:'get',
        success:function (data) {
            $("#contactPhone").val(data.data.contactPhone);
            $("#contactName").val(data.data.contactName);
            $("#contactAddress").val(data.data.contactAddress);
            $("#lawPersonName").html(data.data.lawPersonName);
            $("#orgName").html(data.data.orgName);
            $("#certificateCode").html(data.data.certificateCode);
        }
    });
    var regex = {  //手机号验证正则
        mobile: /^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$/,
        zip:/^[1-9][0-9]{5}$/
    };
    //让当前表单调用validate方法，实现表单验证功能
    $("#ff").validate({
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
            szip:{
                required:true,
                iszip:true
            }
        },
        messages:{
            sname:{
                required:"*请输入业务联系人",
                rangelength:$.validator.format("*业务联系人长度为{0}-{1}个字符"),
                remote:"*该业务联系人已存在！"
            },
            sphone:{
                required:"*请输入手机号",
                isMobile:"*请输入正确格式的手机号"
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
    $(".btn").on("click",function () {
        if($("#ff").valid()) {
            $.ajax({
                url: host.website + '/supplier/toBeSupplier',
                data: {
                    contactPhone: $("#contactPhone").val() || null,
                    contactName: $("#contactName").val() || null,
                    contactAddress: $("#contactAddress").val() || null,
                },
                type: 'post',
                success: function (data) {
                    window.location.href = host.website + "/usercenter/becomingSupplierStep3"
                }
            });
        }
    });

})