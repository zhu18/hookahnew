function loadPageData(data) {
  console.log(data);
  console.log(dataParm.type);
if(dataParm.type==3){

  overTimeDetail(data)
}else{
  var pointsListDOM=' <thead>\
        <tr>\
        <th width="180">行为时间</th>\
        <th width="112">行为</th>\
        <th width="156">行为来源</th>\
        <th width="100">数量</th>\
        <th>备注</th>\
        </tr>\
        </thead>\
        <tbody>';
  if(data.data.list==null){
    pointsListDOM+='<tr><td colspan="5">---没有积分相关信息---</td></tr></tbody>';
    $('.pointsListDetail').html(pointsListDOM)
  }else{
    $('.howManyPoints i').html(data.data2);
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
        getOrPayClass='gray';
        getOrPayStatus=''
      }
      data.data.list[i].note==null?data.data.list[i].note='无备注':data.data.list[i].note;
      pointsListDOM+='\
     <tr>\
        <td>'+data.data.list[i].addTime+'</td>\
        <td>'+getOrPay+'</td>\
        <td>'+data.data.list[i].actionDesc+'</td>\
        <td><span class="'+getOrPayClass+'">'+getOrPayStatus+data.data.list[i].score+'</span></td>\
        <td><div class="pointsDetail">'+data.data.list[i].note+'</div></td>\
      </tr>'
    }
    pointsListDOM+='</tbody>';
    $('.pointsListDetail').html(pointsListDOM)

  }
}



}

function overTimeDetail(data) {
  var pointsListDOM=' <thead>\
        <tr>\
        <th>结算时间</th>\
        <th>当月获得总量</th>\
        <th>当月剩余总量</th>\
        <th>结算积分最近有效期</th>\
        <th>当月过期数量</th>\
        <th>过期积分获得时间</th>\
        </tr>\
        </thead>\
        <tbody>';
  if(data.data.list==null){
    pointsListDOM+='<tr><td colspan="6">---没有积分相关信息---</td></tr></tbody>';
    $('.pointsListDetail').html(pointsListDOM)
  }else{
    $('.howManyPoints i').html(data.data2);
    for(var i=0;i<data.data.list.length;i++){
      pointsListDOM+='\
     <tr>\
        <td>'+data.data.list[i].settleDate+'</td>\
        <td>'+data.data.list[i].thisMonthGetTotal+'</td>\
        <td>'+data.data.list[i].thisMonthSurTotal+'</td>\
        <td>'+data.data.list[i].lastExpire+'</td>\
        <td>'+data.data.list[i].thisMonthExpTotal+'</td>\
        <td>'+data.data.list[i].obtainDate+'</td>\
      </tr>'
    }
    pointsListDOM+='</tbody>';
    $('.pointsListDetail').html(pointsListDOM)

  }
}

//点击查询按钮
$(document).on("click", ".listNav a",function () {
  $(this).addClass('active').siblings().removeClass('active');
  dataParm.type = $(this).attr('typeVal');
  goPage(1);
});





















