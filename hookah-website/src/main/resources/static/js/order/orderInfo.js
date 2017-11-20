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

    $('#coupon-list li .list-item').click(function () {
        console.log(1);
        var val=$("input[name='payMoney']").val();
        if($(this).hasClass('active')){
            $(this).removeClass('active');
            $('#coupon-list li .list-item').addClass('active-blue');
            $('#pay-money').html(val);
		}else {
            $(this).addClass('active').parent().siblings().find('.list-item').removeClass('active active-blue');
            var id=$(this).find("input[name='couponId']").val();
            console.log(id);
            $("input[name='userCouponId']").val(id);
            var faceVal=$(this).find('.j_faceValue').html();
            var payVal=parseFloat(val)-parseFloat(faceVal);

            if(payVal < 0.01){
                payVal=0;
			}
            $('#pay-money').html(payVal);
		}

    });

});
