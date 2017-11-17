function loadPageData(data) {
  console.log(data);
  if(data.data.list==null){
    $('.pointsListDetail tbody').html('<tr><td colspan="5">---没有积分相关信息---</td></tr>')
  }else{
    var pointsListDOM='';
    var getOrPay='';
    var getOrPayStatus='';
    var getOrPayClass='';
    for(var i=0;i<data.data.list.length;i++){

      if(data.data.list[i].action==1){
        getOrPay='获取';
        getOrPayClass='red';
        getOrPayStatus='+'
      }else{
        getOrPay='兑换';
        getOrPayClass='gray'
      }
      pointsListDOM+='\
      <tr>\
        <td>'+data.data.list[i].addTime+'</td>\
        <td>'+getOrPay+'</td>\
        <td>'+data.data.list[i].actionDesc+'</td>\
        <td><span class="'+getOrPayClass+'">'+getOrPayStatus+data.data.list[i].score+'</span></td>\
        <td><div class="pointsDetail">'+data.data.list[i].note+'</div></td>\
      </tr>'
    }
    $('.pointsListDetail tbody').html(pointsListDOM)

  }


}


function getDataPackage(goodsId) {
  $.ajax({
    url: '/jf/getList',
    type: 'get',
    data: {
      goodsId: goodsId
    },
    success: function (data) {
      if (data.code == 1) {
      } else {
        $.alert(data.message)
      }
    },
    error: function (data) {
      $.alert(data.message);
    }
  });
}








//点击查询按钮
$(".listNav a").on("click", function () {
  dataParm.type = $(this).attr('typeVal');
  goPage(1);
});





















