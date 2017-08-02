import OrderController from './OrderController'
import OrderDetailController from './OrderDetailController'
import viewOrderSupplier from './viewOrderSupplier'
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
      permission: 'order_search'
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
    .state('order.viewOrderPending', {//订单页面列表待结算页面
      url: '/order/viewOrderPending',
      template: require('./viewOrderPending.html'),
      controller: OrderController,
      permission: 'viewOrderPending'

    })
    .state('order.viewOrderSupplier', {//订单页面列表页面
      url: '/order/viewOrderSupplier',
      template: require('./viewOrderSupplier.html'),
      controller: viewOrderSupplier,
    })
};
