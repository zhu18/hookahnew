/**
 * Created by lss on 2017/11/7 0007.
 */
function loadPageData(data) {
    console.log(data);
    var list=data.data.list;
    var html='';
    if(list && list.length>0){
        for (var i=0;i<list.length;i++){

            var item =list[i];

            switch (item.applyPlatform){
                case 0:
                    applyPlatform='全品类通用券';
                    break;
            }
            switch (item.applyChannel){
                case 0:
                    applyChannel='无限制';
                    break;
                case 1:
                    applyChannel='满 <span>'+item.discountValue+'</span>元可用';
                    break;
                case 2:
                    applyChannel='折扣';
                    break;
            }
            switch (item.applyGoods){
                case 0:
                    applyGoods='全部商品';
                    break;
                case 1:
                    applyGoods='部分商品';
                    break;
                case 2:
                    applyGoods='部分分类';
                    break;
            }
            if (item.tagName == '已过期'){
                html +='<li class="bg-gray"> ' +
                    '<div class="bg-content"> ' +
                    '<div class="message clearfix">' +
                    '<div class="grid-left average message-money text-align-center">' +
                    '<div class="money">'+(item.faceValue/100)+'</div> </div> ' +
                    '<div class="grid-left average message-tag"> ' +
                    '<p>'+applyPlatform+'</p> ' +
                    '<p>'+applyChannel+'<</p> </div> </div> ' +
                    '<div class="info"> ' +
                    '<p><span>适用平台：</span><span>'+applyPlatform+'</span></p> ' +
                    '<p><span>有效期至：</span><span>'+item.expiryEndDate+'</span></p> ' +
                    '<p><span>详细说明：</span><span>全场通用，不限条件，特殊商品除外。</span></p> </div> </div> ' +
                    '<div class="bg-wrapper"></div> ' +
                    '</li>'
            }else{
                html +='<li data-tip="'+item.tagName+'"> ' ;
                html +='<div class="bg-content"> ' ;
                html +='<div class="message clearfix"> ' ;
                html +='<div class="grid-left average message-money text-align-center"> ' ;
                html +='<div class="money">'+(item.faceValue/100)+'</div> </div> ' ;
                html +='<div class="grid-left average message-tag"> ' ;
                html +='<p>'+applyPlatform+'</p> ' ;
                html +='<p>'+applyChannel+'</p> </div> </div> ' ;
                html +='<a class="btn coupon-btn" href="'+host.website+'/exchange/list">立即使用</a> ' ;
                html +='<div class="info"> <p><span>适用平台：</span><span>'+applyPlatform+'</span></p> ' ;
                html +='<p><span>有效期至：</span><span>'+item.expiryEndDate+'</span></p> ';
                html +='<p><span>详细说明：</span><span>全场通用，不限条件，特殊商品除外。</span></p> </div> </div> ';
                html +='<div class="bg-wrapper"></div> ' ;
                html +='</li>';
            }

        }

      $('#coupon-list').html(html);

    }else {

    }


}