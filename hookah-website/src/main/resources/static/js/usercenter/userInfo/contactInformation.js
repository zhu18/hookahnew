/**
 * Created by lss on 2017/7/13 0013.
 */
$(function () {
    Loading.start();
    $.ajax({
        url:host.website+'/supplier/getContactInfo',
        data:{},
        type:'get',
        cache:false,
        success:function (data) {
            Loading.stop();
            if(data.code == "1"){
                var contactName=data.data.contactName?data.data.contactName:"";
                var contactPhone=data.data.contactPhone?data.data.contactPhone:"";
                var contactAddress=data.data.contactAddress?data.data.contactAddress:"";
                var postCode=data.data.postCode?data.data.postCode:"";
                var email=data.data.email?data.data.email:"";
                $(".contactEmail").html(email);
                $(".contactName").html(contactName);
                $(".contactPhone").html(contactPhone);
                $(".contactAddress").html(contactAddress);
                $(".postCode").html(postCode);
                if(!contactName && !contactPhone && !contactAddress && !postCode){
                    $(".edit").html("新增");
                }else {
                    $(".edit").html("修改");
                }
            }else {
                $.alert(data.message)
            }

        }
    });
})
