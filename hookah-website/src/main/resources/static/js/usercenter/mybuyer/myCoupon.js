/**
 * Created by lss on 2017/11/7 0007
 * 优惠券分为三种状态：可用（0），已经使用（1），已经过期（2）   根据userCouponStatus字段判断
 * 可用中又有三种状态：新到，即将过期，null（不符合前两个条件）  根据tagName字段判断
 */
function loadPageData(data) {
    console.log(data);
    var list=data.data.list;
    var html='';
    if(list && list.length>0){
        for (var i=0;i<list.length;i++){
            var item =list[i];
            //对字段进行转换
            switch (item.applyPlatform){
                case 0:
                    applyPlatform='全品类通用券';
                    break;
                case 1:
                    applyPlatform='限指定商品';
                    break;
                case 2:
                    applyPlatform='限指定品类';
                    break;
            }
            switch (item.applyChannel){
                case 0:
                    applyChannel='无限制';
                    break;
                case 1:
                    applyChannel='满 <span>'+(item.discountValue/100)+'</span>元可用';
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

            if (item.userCouponStatus == 0){//可用优惠券

                if(item.tagName){ //新到 即将过期
                    html +='<li data-tip="'+item.tagName+'" class="tag"> ' ;
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
                }else { //null 的情况
                    html +='<li> ' ;
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

            }else if(item.userCouponStatus == 1){ //已经使用过
                html +='<li class="tag bg-gray" data-tip="已使用"> ' +
                    '<div class="bg-content"> ' +
                    '<div class="message clearfix">' +
                    '<div class="grid-left average message-money text-align-center">' +
                    '<div class="money">'+(item.faceValue/100)+'</div> </div> ' +
                    '<div class="grid-left average message-tag"> ' +
                    '<p>'+applyPlatform+'</p> ' +
                    '<p>'+applyChannel+'</p> </div> </div> ' +
                    '<div class="info"> ' +
                    '<p><span>适用平台：</span><span>'+applyPlatform+'</span></p> ' +
                    '<p><span>有效期至：</span><span>'+item.expiryEndDate+'</span></p> ' +
                    '<p><span>详细说明：</span><span>全场通用，不限条件，特殊商品除外。</span></p> </div> </div> ' +
                    '<div class="bg-wrapper"></div> ' +
                    '</li>'
            }else{ //已经过期的
                html +='<li class="bg-gray tag" data-tip="已过期" > ' +
                    '<div class="bg-content"> ' +
                    '<div class="message clearfix">' +
                    '<div class="grid-left average message-money text-align-center">' +
                    '<div class="money">'+(item.faceValue/100)+'</div> </div> ' +
                    '<div class="grid-left average message-tag"> ' +
                    '<p>'+applyPlatform+'</p> ' +
                    '<p>'+applyChannel+'</p> </div> </div> ' +
                    '<div class="info"> ' +
                    '<p><span>适用平台：</span><span>'+applyPlatform+'</span></p> ' +
                    '<p><span>有效期至：</span><span>'+item.expiryEndDate+'</span></p> ' +
                    '<p><span>详细说明：</span><span>全场通用，不限条件，特殊商品除外。</span></p> </div> </div> ' +
                    '<div class="bg-wrapper"></div> ' +
                    '</li>'
            }
        }
    }else {
      html='没有数据！';
    }
    $('#coupon-list').html(html);
}

$('#tagName li').click(function () { //按标签排序
    $(this).addClass('active').siblings().removeClass('active');
    dataParm.couponTag= $(this).attr('data-coupon-id');
    goPage("1");
});

function selectFn() {//优惠券类型函数
    dataParm.userCouponStatus= $('#selectId').val();
    goPage("1");
}