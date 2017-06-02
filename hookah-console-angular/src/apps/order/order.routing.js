import OrderController from './OrderController'
shelfRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function shelfRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/order/search');
  $stateProvider
    .state('order', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('order.search', {
      url: '/order/search',
      template: require('./list.html'),
      controller: OrderController,
    })
    .state('order.viewOrderDetails', {
      url: '/order/viewOrderDetails',
      template: require('./orderDetail.html'),
      controller: OrderController,
    })
    .state('order.viewGoodDetail', {
      url: '/order/viewGoodDetail',
      template: require('./viewGoodDetail.html'),
      controller: OrderController,
    })
};
