/**
 * Created by wcq on 2017/4/11.
 */
//认证协议页面：点击下一步，跳转到company_auth_init_step2页面

$(function(){
    if(!$("#check").is(":checked")){
        $(".agreement-btn a").attr('href','javascript:;')
        $('.gray-btn2').css({
            "background-color":"gray",
            "cursor": "not-allowed"
        })
    }
    $("#check").click(function(){
        $(".gray-btn2").css({
            "background-color":"blue",
            "cursor": "pointer"
        });
        $("#next").attr("href","./user_auth_init_step2");
    })

})


