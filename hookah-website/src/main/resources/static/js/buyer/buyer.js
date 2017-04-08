/**
 * Created by ki on 2017/4/7.
 */

$(function(){
    // tab切换
    $('.all').on('click', 'li', function () {
        var index = $(this).siblings().removeClass('active').end().addClass('active').index();
        $('.list').hide().eq(index).show();
    });
    // cart全选
    $("body").on("click", ".checkall", function () {
        $("input[type='checkbox']").prop('checked', $(this).is(':checked'));
    });
    // cart删除
    $(".del").click(function(){
        if($(".checkboxes:checked")){
            $(".checkboxes:checked").parent("td").parent("tr").remove();
        }
    });
})