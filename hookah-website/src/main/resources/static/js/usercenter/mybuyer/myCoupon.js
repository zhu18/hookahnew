/**
 * Created by lss on 2017/11/7 0007
 */
function selectFn() {//优惠券类型函数
    dataParm.userCouponStatus= $('#selectId').val();
    goPage("1");
}
$(function () {
    $('#tagName li').click(function () { //按标签排序
        $(this).addClass('active').siblings().removeClass('active');
        dataParm.couponTag= $(this).attr('data-coupon-id');
        goPage("1");
    });
})
