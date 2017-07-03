import OrderController from './OrderController'
import OrderDetailController from './OrderDetailController'
shelfRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function shelfRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/order/search');
  $stateProvider
    .state('order', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('order.search', {//订单页面列表页面
      url: '/order/search',
      template: require('./list.html'),
      controller: OrderController,
    })
    .state('order.viewOrderDetails', {//单条订单详情页
      url: '/order/viewOrderDetails/:id',
      template: require('./orderDetail.html'),
      controller: OrderDetailController,
    })
    .state('order.viewGoodDetail', {
      url: '/order/viewGoodDetail',
      template: require('./viewGoodDetail.html'),
      controller: OrderController,
    })
};
