/**
 * Created by lss on 2017/4/7 0007.
 */
$(function () {
    // 列表切换功能
    function switchHover() {
      $(".goods-table .table-title li").on('click',function () {
          $(this).addClass('active').siblings().removeClass('active');
          var index=$(this).index();
          $(".goods-table .table-content .table-item").each(function () {
              if(index==$(this).index()){
                 $(this).css({
                     'display':'block'
                 }).siblings().css({
                     'display':'none'
                 })
              }
          })
      })
    }
    // 购买数量功能
    function purchaseQuantity() {
          var conut='';
        $(".purchase-quantity .btn-sub").on('click',function () {
            var val=$('.purchase-quantity input').val();
            if(val<1){
                return;
            }
                val=val-1;
            $('.purchase-quantity input').val(val);
        });
        $(".purchase-quantity .btn-plus").on('click',function () {
            var val=$('.purchase-quantity input').val();
            val=parseInt(val)+1;
            $('.purchase-quantity input').val(val);
        });
    }
    switchHover();
    purchaseQuantity();
});