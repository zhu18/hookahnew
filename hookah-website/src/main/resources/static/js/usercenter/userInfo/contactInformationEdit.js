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
            $("#contactName").val(data.data.contactName);
            $("#contactPhone").val(data.data.contactPhone);
            $("#contactAddress").val(data.data.contactAddress);
            $("#postCode").val(data.data.postCode)
        }
    });

    $(".save").on("click",function () {
        $.ajax({
            url:host.website+'/supplier/updateContactInfo',
            data:{
                contactName:$("#contactName").val(),
                contactPhone:$("#contactPhone").val(),
                contactAddress:$("#contactAddress").val(),
                postCode:$("#postCode").val()
            },
            type:'post',
            success:function (data) {
                $(".contactPhone-msg").html("*"+data.message);
                if(data.code == "1"){
                    window.location.href= host.website+"/usercenter/contactInformation"
                }
            }
        });
    });
})
