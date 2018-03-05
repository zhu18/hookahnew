/**
 * Created by wubingbing on 2017/4/11.
 */
// window.history.go(-1);
// if(window.history && window.history.pushState){
// 	alert('1')
// }else{
// 	alert('2')
// }
function check() {
	// alert();
	return true;
}
$(function () {

    $('#coupon-list li .list-item').click(function () { //优惠券的点击事件
        var val=$("input[name='payMoney']").val();//商品的总价格
        $('.J_couponM').html("￥0");
        if($(this).hasClass('active')){ //没有选中任何优惠券
            $(this).removeClass('active');
            $('#coupon-list li .list-item').addClass('active-blue');
            $('#pay-money').html(val);
            $('.J_arrived').html("0");
            $('.J_couponM').html("￥0");
            $("input[name='userCouponId']").val("");
		}else {//选中单个优惠券
            $(this).addClass('active').parent().siblings().find('.list-item').removeClass('active active-blue');
            //把选中的优惠券id传给后台
            var id=$(this).find("input[name='couponId']").val();
            $("input[name='userCouponId']").val(id);

            var faceVal=$(this).find('.j_faceValue').html();//选中优惠卷的面值
            var payVal=(parseFloat(val)*100-parseFloat(faceVal)*100)/100;
            $('.J_arrived').html(faceVal);
            if(payVal < 0.01){
                payVal=0;
			}
            $('#pay-money').html(payVal);
            $('.J_couponM').html("-￥"+faceVal);
		}

    });
    $('#J-editTranslate').click(function(){//点击修改发票按钮
        $('.translate-bg').show();
        $('.invoiceInfo').show();
		getInvoiceInfo()
    });

    $('#J-noTranslate').click(function(){
		$('.editAddress input[name=titleId]').val('');
		$('.editAddress input[name=addressId]').val('');
		$('.titleIdO').val('');
		$('.addressIdO').val('');
		$('.translateInfo span').html('不开发票');
		$('.translateInfo-g').html('');
		$('#J-noTranslate').hide();
    })


});


