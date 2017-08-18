function loadPageData(data) {
  $("#J_allOrder").html(data.data.totalCount);
  $("#payCount").html(data.data.paidCount);
  $("#noPayCount").html(data.data.unpaidCount);
  $("#J_cancelOrder").html(data.data.deletedCount);
  if (data.data.orders.list.length > 0) {
    var list = data.data.orders.list;
    console.log(list)

    var html = '';
    for (var i = 0; i < list.length; i++) {
      html += '<table>';
      html += '<thead>';
      html += '<tr>';
      html += '<th class="" style="width: 280px;">' + '订单号:' + list[i].orderSn + '</th>';
      html += '<th class="text-align-left"  colspan=2 style="position: relative;">创建时间:' + list[i].addTime + '</th>';
      // html += '<th></th>';
      html += '<th colspan="2" style="width:190px;">总额:￥' + (list[i].orderAmount / 100).toFixed(2) + '</th>';
      html += '</tr>';
      html += '</thead>';
      html += '<tbody>';
      var goods = list[i].mgOrderGoodsList;
      for (var ii = 0; ii < goods.length; ii++) {
        var mMat = null;
        switch (goods[ii].goodsFormat) {
            case(0):
                mMat = '次';
                break;
            case(1):
                mMat = '天';
                break;
            case(2):
                mMat = '年';
                break;
            case(3):
                mMat = '套';
                break;
        }
        var catidS = (goods[ii].catId).substring(0, 3);
        var isOfflineInfo='';
        var goodsTypeInfo='';
        if(goods[ii].goodsType==0){
           goodsTypeInfo="数据源-离线数据"
        }else if(goods[ii].goodsType==1){
           goodsTypeInfo="数据源-API"
        }else if(goods[ii].goodsType==2){
           goodsTypeInfo="模型"
        }else if(goods[ii].goodsType==4){
           goodsTypeInfo="分析工具-独立部署软件"
        }else if(goods[ii].goodsType==5){
           goodsTypeInfo="分析工具-SaaS"
        }else if(goods[ii].goodsType==6){
           goodsTypeInfo="应用场景-独立部署软件"
        }else if(goods[ii].goodsType==7){
           goodsTypeInfo="应用场景-SaaS"
        }
        if(goods[ii].isOffline==1){
          isOfflineInfo="交付方式：线下"
        }else if(goods[ii].isOffline==0){
          isOfflineInfo="交付方式：线上"
        }
      /*case 0:
        return '常规商品'; //离线数据包    数据源-离线数据
      case 1:
        return 'API'; //不做修改  下载     数据源-API
      case 2:
        return '数据模型';//模型       模型
      case 4:
        return '分析工具--独立软件';//独立部署软件     分析工具-独立部署软件
      case 5:
        return '分析工具--SaaS';//saas          分析工具-SaaS
      case 6:
        return '应用场景--独立软件';//独立部署软件     应用场景-独立部署软件
      case 7:
        return '应用场景--SaaS'; //saas    应用场景-SaaS
        break;*/
        html += '<tr class="content border-bottom">';
        html += '<td class="text-align-center" style="width: 280px;">';
        html += '<div class="p-img">';
        html += '<a href="/exchange/details?id=' + goods[ii].goodsId + '" target="_blank">';
        html += '<img src="'+host.static+'/' + goods[ii].goodsImg + '" alt="">';
        html += '</a>';
        html += '</div>';
        html += '<div class="desc margin-top-10 marign-bottom-10" >';
        html += '<a href="/exchange/details?id=' + goods[ii].goodsId + '" target="_blank">' + goods[ii].goodsName + '</a>';
        html += '</div>';
        html += '</td>';
        if(goods[ii].isDiscussPrice == 1){
            html += '<td class="text-align-left">x' + goods[ii].goodsNumber + '<br/>' + '面议参考价:'+ (goods[ii].goodsPrice / 100).toFixed(2) +'元<br />'+ goodsTypeInfo+ '<br />'+ isOfflineInfo+'</td>';
        }else {
            html += '<td class="text-align-left">x' + goods[ii].goodsNumber + '<br/>' + '规格:' + (goods[ii].goodsPrice / 100).toFixed(2) + '/' + mMat + '<br />'+ goodsTypeInfo+ '<br />'+ isOfflineInfo+'</td>';
        }
        // html += '<td><a href="/exchange/orderEndDetails?id='+goods[ii].goodsId+'&orderSn='+list[i].orderSn+'">下载<br/><span class="fa fa-download font-size-18"></span></a></td>';
        /*
         if (catidS == '104') {
         html += '<td><a target="_blank" href="javascript:getDataPackageD(\'' + goods[ii].goodsId + '\' , \'' + goods[ii].sourceId + '\',\'' + list[i].orderSn + '\');">查看<br/><span class="fa fa-eye font-size-18"></span></a></td>';
         } else {
         */
        // html += '<td><a href="javascript:getDataPackageD(\'' + goods[ii].goodsId + '\' , \'' + goods[ii].sourceId + '\',\'' + list[i].orderSn + '\');">获取密钥<br/><span class="fa fa-download font-size-18"></span></a></td>';
        /*if(goods[ii].goodsType == 1){ //如果是API产品 点击下载
          html += '<td><a href="javascript:getDataPackageD(\'' + goods[ii].goodsId + '\' , \'' + goods[ii].sourceId + '\',\'' + list[i].orderSn + '\');">交付信息<br/><span class="fa fa-download font-size-18"></span></a></td>';

        }else{*/
          html += '<td><a href="javascript:getKey(\'' + goods[ii].goodsId + '\' , \'' + goods[ii].sourceId + '\',\'' + list[i].orderId + '\',\'' + goods[ii].goodsType + '\',\'' + goods[ii].isOffline + '\');">交付信息<br/><span class="fa fa-download font-size-18"></span></a></td>';

        //}

        // }
        html += '<td style="width:190px;" class="">金额:￥&nbsp;' + ((goods[ii].goodsPrice / 100) * goods[ii].goodsNumber).toFixed(2) + '<br/><br/>' + list[i].payName + '</td>';//订单总金额
        // html += '<td class="text-align-center">';
        // if (list[i].commentFlag == 0) {
        //     html += '<a target="_blank" href="/order/sunAlone?orderId=' + list[i].orderId + '" class="display-block">评价晒单</a>';
        // } else if (list[i].commentFlag == 1) {
        //     html += '<span class="display-block">已评价</span>';
        // }
        // html += '<br><a href="/exchange/details?id='+goods[ii].goodsId+'" class="display-inline-block goPay btn btn-full-orange">再次购买</a>';
        // html += '</td>';
        if (ii == 0) {
          html += '<td rowspan="' + goods.length + '" class="border-left" style="width:190px;">';
          html += '<span>已完成</span>';
          if (list[i].commentFlag == 0) {
            html += '<a target="_blank" href="/order/sunAlone?orderId=' + list[i].orderId + '" class="display-block margin-top-5 margin-bottom-5">评价晒单</a>';
          } else if (list[i].commentFlag == 1) {
            html += '<span class="display-block margin-top-5 margin-bottom-5">已评价</span>';
          }
          html += '<a href="/exchange/details?id=' + goods[ii].goodsId + '" class="display-inline-block goPay btn btn-full-orange margin-top-5 margin-bottom-5">再次购买</a>';
          // html += '<a target="_blank" href="/order/viewDetails?orderId=' + list[i].orderId + '&num=1" class="display-block color-blue margin-top-5 margin-bottom-5">订单详情</a>';
          html += '</td>';
        }

        html += '</tr>';

      }
      html += '</tbody>';
      html += '</table>';
    }
    $('.order').html(html);
  } else {
    $('.order').html('<tr class="noData"><td colspan="5">暂时无订单！</td></tr>');
  }

}
var start = {
  format: "YYYY-MM-DD hh:mm:ss",
  isTime: true,
  maxDate: $.nowDate(0),
  choosefun: function (elem, datas) {
    end.minDate = datas; //开始日选好后，重置结束日的最小日期
  }
};
var end = {
  format: "YYYY-MM-DD hh:mm:ss",
  isTime: true,
  maxDate: $.nowDate(0),
  choosefun: function (elem, datas) {
    start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
  }

};
$.jeDate("#startDate", start);
$.jeDate("#endDate", end);
//点击查询按钮
$(".searchQuery .search").on("click", function () {
  //评论状态：0：未评论；1：已评论
  var radioChecked = $(".comment-status input:radio[name='comment']:checked");
  dataParm.commentFlag = radioChecked.val();
  var startDate = $("#startDate").val();
  var endDate = $("#endDate").val();
  dataParm.startDate = startDate ? startDate : null;
  dataParm.endDate = endDate ? endDate : null;
  // if(!startDate){
  //     $("#startDate").val(format(new Date()));
  // }
  // if(!endDate){
  //     $("#endDate").val(format(new Date()));
  // }
  goPage(1);
});


// 删除订单
function deleteRadio(orderId) {
  $.ajax({
    url: '/order/delete',
    type: 'get',
    data: {
      orderId: orderId
    },
    success: function (data) {
      if (!(data.code == 0)) {
        $.alert('删除成功', true, function () {
          location.reload()
        });
      } else {
        console.log("删除失败！");
      }
    }
  })
}
function confirmDelete(orderId) {
  $.confirm('确定要删除该订单吗？', null, function (type) {
    if (type == 'yes') {
      deleteRadio(orderId);
      this.hide();
    } else {
      this.hide();
    }
  });
}
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
function getDataPackageD(goodsId, sourceId, orderSn) {
  console.log(goodsId, sourceId, orderSn)
  $.ajax({
    url: host.website + '/help/exportWords',
    type: 'get',
    data: {
      goodsId: goodsId,
      sourceId: sourceId,
      orderNo: orderSn
    },
    success: function (data) {
      if (data.code == 1) {
        // window.location.href = data.data;
        window.location.href = data.data;
      } else {
        $.alert(data.message)
        // $.alert('下载失败')
      }
    }
  });
}


//前台页面-订单管理 添加 获取密钥（弹出内容）
var keyDialogHtml = '<div id="myDialog" title="My Dialog">\
  <p>您获取的商品密钥为：</p>\
  <div>\
    <p>&nbsp;&nbsp;用户名：<em class="j_username">CCC</em></p>\
    <p>&nbsp;&nbsp;密码：<em class="j_pass">DDD</em></p>\
    <p>&nbsp;&nbsp;有效期：<em class="j_time">2342342</em></p>\
  </div>\
';
function getKey(goodsId, sourceId, orderId, goodsType, isOffline) {

  console.log('goodsId：' + goodsId, "sourceId：" + sourceId, "orderId：" + orderId, "goodsType：" + goodsType, "isOffline：" + isOffline);
  /*if (goodsType == 1){
    return false;
  }*/
  $.ajax({
    url: '/order/getRemark',
    type: 'get',
    data: {
      goodsId: goodsId,
      orderId: orderId
    },
    success: function (data) {
      console.log(data)
      if (data.code == 1) {
        // window.location.href = data.data;
        // window.location.href = data.data;
        var tempHtml = null;
        if (isOffline == 0) {

          if (goodsType == 0) {
            var tempUrl = null;
            if (data.data.data.isOnline == 0) {
              tempUrl = data.data.data.localUrl;
            } else {
              tempUrl = data.data.data.onlineUrl;
            }
            tempHtml = '<div class="confirmKey"><h4>您获取的商品密钥为：</h4>\
           <div>\
             <h5>&nbsp;&nbsp;离线数据包下载地址：<a target="_blank" style="text-decoration: underline;color:blue; " href="' + tempUrl + '">下载</a></h5>\
             <h5>&nbsp;&nbsp;数据包解压密码：<span>' + data.data.data.dataPwd + '</span></h5>\
           </div></div>'
          } else if (goodsType == 1) { //API
               Loading.start();
               var apiWordUrl;
                //获取api文档下载地址
               $.ajax({
                  url: host.website + '/help/exportWords',
                  type: 'get',
                  async: false ,
                  data: {
                    goodsId: goodsId,
                    sourceId: sourceId,
                    orderNo: ""
                  },
                  success: function (data) {
                    if (data.code == 1) {
                        apiWordUrl=data.data;
                    } else {
                      $.alert(data.message)
                    }
                  }
                });
             var apiObj=JSON.parse(data.data.result);
             tempHtml = "<div class='confirmKey'><h4>提取密钥：</h4>" +
                        "<h5>您购买的API商品token为：</h5>" +
                        "<h5><input id='token' readonly='readonly' style='width: 270px;margin-left: 15px;border: 0;' value='"+apiObj.data.token+"'/><a style='color: blue;margin-left: 10px;' href=\"javascript:copyText();\">复制token</a><a style='color: blue;margin-left: 10px;' href=\"javascript:resetToken('"+goodsId+"','"+orderId+"');\">重置token</a></h5>" +
                        "<h5>当前API状态：</h5>" +
                        "<h5><span style='margin-left: 15px;'> 已调用次数："+(apiObj.data.totalCount-apiObj.data.hasCount)+"</span><span style='margin-left: 15px;'> 剩余次数："+apiObj.data.hasCount+"</span><a style='color:blue;margin-left: 20px;' href='"+apiWordUrl+"'>详情下载</a></h5></div>";
              Loading.stop();
          } else if (goodsType == 2) { //模型
            tempHtml = "<div class='confirmKey'><h4>模型压缩包下载地址：</h4>" +
              "<h5>联系人姓名：<span>" + data.data.data.concatInfo.concatName + "</span></h5>" +
              "<h5>联系人电话：<span>" + data.data.data.concatInfo.concatPhone + "</span></h5>" +
              "<h5>联系人邮箱：<span>" + data.data.data.concatInfo.concatEmail + "</span></h5>" +
              "<h5>模型文件：<span><a style='color:blue; text-decoration: underline' target='_blank' href='" + data.data.data.modelFile.fileAddress + "'>下载</a></span></h5>" +
              "<h5>模型文件密码：<span>" + data.data.data.modelFile.filePwd + "</span></h5>" +
              "<h5>配置文件：<span><a style='color:blue; text-decoration: underline' target='_blank' href='" + data.data.data.configFile.fileAddress + "'>下载</a></span></h5>" +
              "<h5>配置文件密码：<span>" + data.data.data.configFile.filePwd + "</span></h5>" +
              "<h5>配置参数：<span><a style='color:blue; text-decoration: underline' target='_blank' href='" + data.data.data.paramFile.fileAddress + "'>下载</a></span></h5>" +
              "<h5>配置参数密码：<span>" + data.data.data.paramFile.filePwd + "</span></h5></div>"
            ;

          } else if (goodsType == 4 || goodsType == 6) { //独立软件 手动填写数据
            tempHtml = "<div class='confirmKey'><h4>商品交付信息：</h4>" +
              "<h5>安装包下载地址：<span>" + data.data.url.dataAddress + "</span></h5>" +
              "<h5>序列号：<span>" + data.data.payInfoSerialNumber + "</span></h5>" +
              "<h5>许可文件获取地址：<span>" + data.data.payInfoFileUrl + "</span></h5></div>";

          } else if (goodsType == 5 || goodsType == 7) { //SaaS  手动填写数据
            tempHtml = "<div class='confirmKey'><h4>商品交付信息：</h4>" +
              "<h5>在线访问地址：<span>" + data.data.url.dataAddress + "</span></h5>" +
              "<h5>用户名：<span>" + data.data.payInfoUserName + "</span></h5>" +
              "<h5>密码：<span>" + data.data.payInfoPassword + "</span></h5></div>";//sass
          }


        } else {
          tempHtml = '<div class="confirmKey"><h4>线下交付联系方式：</h4>\
           <div>\
             <h5>&nbsp;&nbsp;联系人姓名：<span>' + data.data.data.concatName + '</span></h5>\
             <h5>&nbsp;&nbsp;联系人电话：<span>' + data.data.data.concatPhone + '</span></h5>\
             <h5>&nbsp;&nbsp;联系人邮箱：<span>' + data.data.data.concatEmail + '</span></h5>\
           </div></div>'
        }

        $.confirm(tempHtml, [{close: '关闭'}],
          function () {
            var vals = $('#inputss').serializeArray();
            console.log(JSON.stringify(vals));
            this.hide();


          }, {width: "500"});
      } else {
        // $.alert(data.message)
        // $.alert('下载失败')
      }
    }
  });
}
//拷贝
function copyText(){
   var Url2=document.getElementById("token");
     Url2.select(); // 选择对象
    document.execCommand("Copy"); // 执行浏览器复制命令
    alert("已复制好，可贴粘。");
}
//重置TOKEN
function resetToken(goodsId,orderId){
    $.ajax({
      url: host.website + '/order/reCreateToken',
      type: 'get',
      data: {
        goodsId: goodsId,
        orderId: orderId
      },
      success: function (data) {
        if (data.code == 1) {
          $("#token").val(JSON.parse(data.data.result).data);
        } else {
          $.alert(data1.message);
        }
      }
    });
}
/*


 $.confirm('<p>您获取的商品密钥为：</p>\
 <div>\
 <p style="text-align: left">&nbsp;&nbsp;用户名：<em class="j_username">CCC</em></p>\
 <p style="text-align: left">&nbsp;&nbsp;密码：<em class="j_pass">DDD</em></p>\
 <p style="text-align: left">&nbsp;&nbsp;有效期：<em class="j_time">2342342</em></p>\
 </div>', [{close: '关闭'}], function () {

 var vals = $('#inputss').serializeArray();
 console.log(JSON.stringify(vals));
 this.hide();

 });
 */


























