/**
 * Created by lss on 2017/7/17 0017.
 */
$(function () {
    $.ajax({
        url:host.website+'/supplier/getBaseInfo',
        data:{},
        type:'get',
        success:function (data) {
            $("#contactPhone").val(data.data.contactPhone);
            $("#contactName").val(data.data.contactName);
            $("#contactAddress").val(data.data.contactAddress);
            $("#lawPersonName").html(data.data.lawPersonName);
            $("#orgName").html(data.data.orgName);
            $("#certificateCode").html(data.data.certificateCode);
        }
    });

    $(".btn").on("click",function () {
        $.ajax({
            url:host.website+'/supplier/toBeSupplier',
            data:{
                contactPhone:$("#contactPhone").val() || null,
                contactName:$("#contactName").val() || null,
                contactAddress:$("#contactAddress").val() || null,
            },
            type:'post',
            success:function (data) {

            }
        });
    });

})