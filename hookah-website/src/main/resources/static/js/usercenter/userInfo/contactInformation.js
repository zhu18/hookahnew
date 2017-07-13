/**
 * Created by lss on 2017/7/13 0013.
 */

$(function () {
    $.ajax({
        url:host.website+'/supplier/getContactInfo',
        data:{},
        type:'get',
        success:function (data) {
            console.log(data.data);
            $(".contactName").html(data.data.contactName)
            $(".contactPhone").html(data.data.contactPhone)
            $(".contactAddress").html(data.data.contactAddress)
            $(".postCode").html(data.data.postCode)
        }
    });
})
