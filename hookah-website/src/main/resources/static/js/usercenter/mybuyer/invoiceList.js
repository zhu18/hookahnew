/**
 * Created by wcq on 2017/4/20.
 */
// 全选
$("body").on("click", ".checkbox-title", function () {
    $("input[name='checkbox']").prop('checked', $(this).is(':checked'));
    $(".checkbox-title").prop("checked", $(this).is(':checked'));
});
//单选
$("body").on("click", ".checkbox", function () {
    if ($(".checkbox:checked").length == $('.checkbox').length) {
        $(".checkbox-title").prop("checked", true);
    }
    else {
        $(".checkbox-title").prop("checked", false);
    }
});
111