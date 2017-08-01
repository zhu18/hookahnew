/**
 * Created by lss on 2017/7/19 0019.
 */
function loadPageData(data){
    var list = data.data.list;
    if(list.length > 0){
        var html = '';
        for(var i=0; i<list.length; i++){
            switch (list[i].checkStatus) {
                case(0):
                    list[i].checkStatus = '待审核';
                    break;
                case(1):
                    list[i].checkStatus = '审核成功';
                    break;
                case(2):
                    list[i].checkStatus = '审核未成功';
                    break;
            }
            html+="<tr >" ;
            html+="<td>"+list[i].serialNo+"</td>" ;
            html+="<td>"+list[i].addTime+"</td>" ;
            html+="<td>￥"+(list[i].money / 100).toFixed(2)+"</td>" ;
            html+="<td>"+list[i].checkStatus +"</td>" ;
            html+="<td><a href='javascript:void(0)' onclick='getDetail("+list[i].id+")'>查看详情</td>" ;
            html+="</tr >" ;
        }
        $('.ithdrawals-down-list-content').html(html);
    }else{
        $('.ithdrawals-down-list-content').html('<tr class="no"><td colspan="8">暂无数据</td></tr>');
    }
}
function getDetail(id) {
    window.location.href= host.website+'/usercenter/withdrawalStep2?id='+id;
}
$(function () {
    $("#withdrawals-btn").on("click",function () {
        $("#money").val($(".apply-money .money").html())
    })
    $("#goPay").on("click",function () {
        var money=parseInt($("#money").val());
        if(money==0){
            $.alert("金额能为0!");
        }else if(money>parseInt($(".apply-money .money").html())){
            $.alert("金额不可大于余额！");
        }else {
            if($("#form").valid()){
                $.ajax({
                    url:host.website+'/withdrawRecord/applyW',
                    data:{
                        money:$("#money").val(),
                        payPwd:$("#password").val(),

                    },
                    type:'get',
                    success:function (data) {
                        if(data.code=="1"){
                            window.location.href= host.website+'/usercenter/withdrawalStep2';
                        }
                    }
                });
            }

        }

    })
    // 表格验证开始
    var regex = {  //手机号验证正则
        money: /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/,
    };
    //让当前表单调用validate方法，实现表单验证功能
    $("#form").validate({
        rules:{     //配置验证规则，key就是被验证的dom对象，value就是调用验证的方法(也是json格式)
            money:{
                required:true,
                isMoney:true
            },
            password:{
                required:true,
                isPassword:true
            }
        },
        messages:{
            money:{
                required:"*请输入金额",
                isMoney:"*请输入正确格式的金额"
            },
            password:{
                required:"*请输入交易密码",
                isPassword:"*请输入正确的交易密码"
            }
        }
    });
    $.validator.addMethod("isMoney", function(value, element) {
        var mobile = regex.money.test(value);
        return this.optional(element) || (mobile);
    }, "*请输入正确格式的金额");
    $.validator.addMethod("isPassword", function(value, element) {

        if(value.length==6){
            var mo=false;
            $.ajax({
                url:host.website+'/usercenter/verifyPayPassword',
                data:{
                    passWord:$.md5(value)
                },
                type:'post',
                async:false,
                success:function (data) {
                    if(data.code == 1){
                        mo=true;
                    }else if(data.code == 0){
                        return false;
                    }else{
                        return false;
                    }
                }
            });

        }
        return mo;
    }, "*请输入正确的交易密码");
    // 类型点击事件
    $(".search-criteria .status li").on("click",function () {
        var status=null;
        $(this).addClass("active").siblings().removeClass("active");
        status=$(this).attr("data-status");
        dataParm.tradeStatus=status || null;
        goPage(1);
    });
    // 日历input事件
    $("#endDate").click(function () {
        var idInt = setTimeout(function(){
            if($("#endDate").val() && $("#startDate").val()){
                dataParm.startDate=$("#startDate").val() || null;
                dataParm.endDate=$("#endDate").val() || null;
                goPage(1);
                clearInterval(idInt);
            }
        },1500);
    });
    // 日历插件开始
    var start = {
        format: "YYYY-MM-DD hh:mm:ss",
        isTime: true,
        maxDate: $.nowDate(0),
        choosefun: function (elem, datas) {
            end.minDate = datas; //开始日选好后，重置结束日的最小日期
        }
    };
    var end = {
        format: "YYYY-MM-DD hh:mm:ss",
        isTime: true,
        maxDate: $.nowDate(0),
        choosefun: function (elem, datas) {
            start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
        }
    };
    $.jeDate("#startDate", start);
    $.jeDate("#endDate", end);
    // 日历插件结束
})