$.getUrlParam = function (key) {
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
    var result = window.location.search.substr(1).match(reg);
    return result ? decodeURIComponent(result[2]) : null;
};
var id = $.getUrlParam('newsId');
renderDetails();
function renderDetails(){
    $.ajax({
        url:'/sysNews/details',
        type:'post',
        data:{
            id:id
        },
        success:function(data){
            if(data.code == "1") {
                console.log(data.data)
            }else{
                console.log(data.message);
            }
        }
    });
}


