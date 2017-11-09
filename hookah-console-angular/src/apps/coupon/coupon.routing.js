import couponController from "./couponController";
import queryController from "./queryController";
import detailController from "./detailController";
import addController from "./addController";

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
    .state('coupon.query', { //优惠券查询
      url: '/coupon/query',
      template: require('./query.html'),
      controller: queryController,
      permission: 'query'
    })
    .state('coupon.detail', { //优惠券详情
      url: '/coupon/detail',
      template: require('./detail.html'),
      controller: detailController,
      permission: 'detail'
    })
    .state('coupon.add', { //优惠券添加
      url: '/coupon/add',
      template: require('./add.html'),
      controller: addController,
      permission: 'add'
    })
};
