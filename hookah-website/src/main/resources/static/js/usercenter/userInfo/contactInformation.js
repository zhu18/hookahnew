/**
 * Created by lss on 2017/7/13 0013.
 */

$(function () {
    $.ajax({
        url:host.website+'/supplier/getContactInfo',
        data:{},
        type:'get',
        cache:false,
        success:function (data) {
            var contactName=data.data.contactName?data.data.contactName:"";
            var contactPhone=data.data.contactPhone?data.data.contactPhone:"";
            var contactAddress=data.data.contactAddress?data.data.contactAddress:"";
            var postCode=data.data.postCode?data.data.postCode:"";
            $(".contactName").html(contactName);
            $(".contactPhone").html(contactPhone);
            $(".contactAddress").html(contactAddress);
            $(".postCode").html(postCode);
            if(!(contactName && contactPhone && contactAddress && postCode)){
                $(".edit").html("新增");
            }else {
                $(".edit").html("修改");
            }

        }
    });
})
