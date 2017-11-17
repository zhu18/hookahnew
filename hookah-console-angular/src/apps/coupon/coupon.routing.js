import couponController from "./couponController";
import queryController from "./queryController";
import couponDetailController from "./couponDetailController";
import userDetailController from "./userDetailController";
import addController from "./addController";
import giveCouponController from "./giveCouponController";

supplierRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function supplierRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/coupon/list');
  $stateProvider
    .state('coupon', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('coupon.list', { //优惠券列表
      url: '/coupon/list',
      template: require('./list.html'),
      controller: couponController,
      permission: 'list'
    })
    .state('coupon.query', { //优惠券用户查询
      url: '/coupon/query',
      template: require('./query.html'),
      controller: queryController,
      permission: 'query'
    })
    .state('coupon.couponDetail', { //优惠券详情
      url: '/coupon/couponDetail/:id',
      template: require('./couponDetail.html'),
      controller: couponDetailController,
      permission: 'couponDetail'
    })
    .state('coupon.userDetail', { //优惠券用户详情
      url: '/coupon/userDetail/:id',
      template: require('./userDetail.html'),
      controller: userDetailController,
      permission: 'userDetail'
    })
    .state('coupon.add', { //优惠券添加
      url: '/coupon/add/:id',
      template: require('./add.html'),
      controller: addController,
      permission: 'add'
    })
    .state('coupon.giveCoupon', { //赠送优惠券
      url: '/coupon/giveCoupon/:id',
      template: require('./giveCoupon.html'),
      controller: giveCouponController,
      permission: 'add'
    })
};
