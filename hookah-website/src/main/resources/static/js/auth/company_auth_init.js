/**
 * Created by wcq on 2017/4/11.
 */
//认证协议页面：点击下一步，跳转到company_auth_init_step2页面
$(function(){
    judge();
    function judge(){
        if(!$("#check").is(":checked")){
            $(".agreement-btn a").attr('href','javascript:;')
            $('.gray-btn2').css({
                "background-color":"#efefef",
                "cursor": "not-allowed"
            })
        }
    }
    $("#check").click(function(){
        if($("#check").is(":checked")){
            $(".gray-btn2").css({
                "background-color":"#0781e8",
                "cursor": "pointer"
            });
            $("#next").attr("href","./company_auth_init_step2");
        }else{
            judge();
        }

    })

})

