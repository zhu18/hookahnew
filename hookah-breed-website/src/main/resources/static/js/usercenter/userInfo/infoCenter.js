/**
 * Created by wcq on 2017/4/7.
 */
$(function () {
    //蓝色小圆点的显示和隐藏，点击后字体颜色的变化
    //定义标识符，标识信息：是否已读 false:未读，true:已读
    $(".item .info").on("click", function () {
        // $(this).addClass("active").find(".round").css("visibility", "hidden");
        $(this).addClass("active");
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
    $(".delete").click(function () {
        if ($(".checkbox:checked")) {
            $(".checkbox:checked").parent("td").parent("tr").remove();
        }
        var len = $("tbody tr").length;
        if (len <= 0) {
            $("table").hide();
            $(".bottom").html("<div class='no-info'>暂时没有消息</div>");//显示加载动画
        }
    });


});