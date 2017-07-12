/**
 * Created by ki on 2017/7/12.
 */
$(function(){
    // 全选
    $("body").on("click", ".checkbox-title", function () {
        $("input[name='checkbox']").prop('checked', $(this).is(':checked'));
        $(".checkbox-title").prop("checked", $(this).is(':checked'));
    });
    $("body").on("click", ".check-all", function () {
        $("input[name='checkbox']").prop('checked', $(this).is(':checked'));
        $(".check-all").prop("checked", $(this).is(':checked'));
    });

    // 删除
    $("#delete").click(function(){
        $(".list .order table").hide();
        $(".list .tableBot").hide();
        $(".list").append("<div class='listNone'>暂无信息</div>");
    })

    $("#flag").click(function(){
        var item = $(".list tbody tr");
        item.each(function(){
            if($("input[name='checkbox']:checked")){
                $("input[name='checkbox']:checked").parents("tr").css({"color":"#000"});
                $("input[name='checkbox']").prop('checked', false);
            }else {
                $.confirm("请选择",true,function(){});
            }
        });
    })
})