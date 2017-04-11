/**
 * Created by wcq on 2017/4/11.
 */
//认证协议页面：点击下一步，跳转到company_auth_init_step2页面
$("#next").click(function(){
    console.log($("#check").is(":checked"));
    if($("#check").is(":checked")){
        $(this).attr("href","./user_auth_init_step2");
    }else{

    }
});
