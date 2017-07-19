/**
 * Created by lss on 2017/7/19 0019.
 */
$.ajax({
    url:host.website+'/withdrawRecord/applyW',
    data:{
        money:$(".apply-money input[name=text]").val(),
        payPwd:$("#password").val()
    },
    type:'get',
    success:function (data) {

    }
});