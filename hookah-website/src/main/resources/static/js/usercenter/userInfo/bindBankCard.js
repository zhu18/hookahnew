/**
 * Created by lss on 2017/7/19 0019.
 */
$(function () {
    // 数据回现
    $.ajax({
        url:host.website+'/payBankCard/searchBankInfo',
        data:{},
        type:'get',
        success:function (data) {
            var list=data.data.bank;
            var html = '<option value="">全部</option>';
            for(var i=0;i<list.length;i++){
                html += '<option value="'+list[i].id+'">'+list[i].bankName+'</option>';
                $('#bindName').html(html);
            }
            $("#account-name").html(data.data.cardOwner);
            if(data.data.bankAccountType==1){
                data.data.bankAccountType="对公银行账户";
            }else {
                data.data.bankAccountType="对私银行账户";
            }
            $('#bankAccountType').html(data.data.bankAccountType);
            $('#bankAccountType').attr("data-flog","1")

        }
    });
    // 表格验证开始
    var regex = {  //手机号验证正则
        mobile: /^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$/,
        zip:/^[1-9][0-9]{5}$/,
        bank: /^\d{16}|\d{19}$/
    };
    //让当前表单调用validate方法，实现表单验证功能
    $("#form").validate({
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
            bank:{
                required:true,
                isBank:true
            },
            bindName:{
                required:true
            },
            verificationCode:{
                required:true
            }
        },
        messages:{
            sname:{
                required:"*请输入用户名",
                rangelength:$.validator.format("*用户名长度为{0}-{1}个字符"),
                remote:"*该用户名已存在！"
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
            },
            bank:{
                required:"*请输入银行卡号",
                isBank:"*请输入正确的银行卡号"
            },
            bindName:{
                required:"*请选择银行名称"
            },
            verificationCode:{
                required:"*请填写验证码"
            }
        }
    });
    $.validator.addMethod("isMobile", function(value, element) {
        var mobile = regex.mobile.test(value);
        return this.optional(element) || (mobile);
    }, "*请填写有效的手机号");
    $.validator.addMethod("isBank", function(value, element) {
        var mobile = regex.bank.test(value);
        return this.optional(element) || (mobile);
    }, "*请输入正确的银行卡号");
    // 表格验证结束
    // 获取手机验证码
    $(".verification-code").on("click",function () {
        var that = $(this);
        $.ajax({
            url:host.auth+'/sms/send',
            type:'post',
            data:{
                mobile:$('#phoneNumber').val(),
                type:3
            },
            success:function(data){
                if(data.code == 1){
                    $.alert(data.data);
                    settime(that);
                }else{
                    $.alert('获取验证码失败，请重新获取');
                }
            },
            error: function() {
                console.log(1);
            }
        });
    })
    // 提交事件
    $(".btn").on("click",function () {
        if($("#form").valid()){
           $.ajax({
                url:host.website+'/payBankCard/addBankInfo',
                data:{
                    cardCode:$('#cardCode').val(),
                    openBank:$('#openBank').val(),
                    phoneNumber:$('#phoneNumber').val(),
                    payBankId:$('#bindName').val(),
                    bankAccountType:parseInt($('#bankAccountType').attr("data-flog")),
                    cardOwner:$('#account-name').html()
                },
                type:'get',
                success:function (data) {
                    if (data.code=="1"){
                        $.alert('绑定银行卡成功',true,function(){
                            window.location.href= host.website+'/usercenter/fundmanage';
                        });
                    }else {
                        $.alert('绑定银行卡失败')
                    }
                }
            });
        }

    });
    var countdown = 120;
    function settime(that) {//获取验证码倒计时
        if (countdown == 0) {
            that.removeAttr("disabled");
            that.html("重新获取验证码");
            that.removeClass('btn-disabled');
            countdown = 120;
            return;
        } else {
            that.attr("disabled", true);
            that.html("重新获取(" + countdown + ")");
            that.addClass('btn-disabled');
            countdown--;
        }
        setTimeout(function () {
                settime(that)
            }
            , 1000)
    }

});
