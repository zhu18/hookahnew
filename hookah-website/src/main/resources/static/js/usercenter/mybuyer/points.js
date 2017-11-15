function loadPageData(data) {

}
$.jeDate("#startDate", start);
$.jeDate("#endDate", end);
//点击查询按钮
$(".searchQuery .search").on("click", function () {

  goPage(1);
});



function getDataPackage(goodsId) {
  $.ajax({
    url: host.website + '/help/exportWords',
    type: 'get',
    data: {
      goodsId: goodsId
    },
    success: function (data) {
      if (data.code == 1) {
        // window.location.href = data.data;
        window.location.href = data.data;
      } else {
        $.alert(data.message)
        // $.alert('下载失败')
      }
    },
    error: function (data) {
      $.alert(data.message);
    }
  });
}


















