/**
 * Created by wcq on 2017/4/11.
 */

//点击不同的主体类型，对应的内容显示和隐藏
//点击不同的主题类型，改变对应的类型的背景色，
var type = $(".auth-info .type li");
type.on("click",function(){
    $(this).addClass("active").siblings().removeClass("active");
    var index = $(this).index();
    $(".typeBox>li:eq("+index+")").addClass("activeLi").siblings().removeClass("activeLi");

});