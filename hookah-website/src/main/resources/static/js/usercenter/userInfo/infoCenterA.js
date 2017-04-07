/**
 * Created by wcq on 2017/4/7.
 */
//蓝色小圆点的显示和隐藏，点击后字体颜色的变化

$(".item .info").on("click", function () {
    $(this).addClass("active").find(".round").css("visibility", "hidden");
});
//全选

$("body").on("click", ".checkbox-title", function () {
    $("input[name='checkbox']").prop('checked', $(this).is(':checked'));
    $(".checkbox-title").prop("checked", $(this).is(':checked'));
});

$("body").on("click", ".check-all", function () {
    $("input[name='checkbox']").prop('checked', $(this).is(':checked'));
    $(".check-all").prop("checked", $(this).is(':checked'));
});
//单选

$("body").on("click", ".checkbox", function () {
    if ($(".checkbox:checked").length == $('.checkbox').length) {
        $(".check-all").prop("checked", true);
        $(".checkbox-title").prop("checked", true);
    }
    else {
        $(".check-all").prop("checked", false);
        $(".checkbox-title").prop("checked", false);
    }
});
//删除按钮
$(".delete").click(function(){
    if($(".checkbox:checked")){
        $(".checkbox:checked").parent("td").parent("tr").remove();
    }
    var len = $("tbody tr").length;
    if(len <= 0){
        $("tbody").html('<tr><td colspan="4">暂时没有消息</td></tr>');//显示加载动画
    }
});

//标记已读
$(".readed").click(function(){
    if($(".checkbox:checked")){
        $(".checkbox:checked").parent("td").parent("tr").addClass("active").find(".round").css("visibility", "hidden");;
    }
});
//全部已读
$(".read-all").click(function(){
  $(".active").parent("tr").find("input").prop("checked",true);
});