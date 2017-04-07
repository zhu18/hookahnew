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
        $(".checkall").prop("checked", $(this).is(':checked'));
    });
    $(".del").click(function(){
        if($(".checkboxes:checked")){
            $(".checkboxes:checked").parent("td").parent("tr").remove();
        }
        var len = $("tbody tr").length;
        if(len <= 0){
            $(".second tbody").html('<tr><td>暂时没有物品</td></tr>');
        }
    });
})