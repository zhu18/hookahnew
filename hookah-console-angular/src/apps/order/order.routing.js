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
    .state('order.detail', {
      url: '/order/detail',
      templateUrl: require('./list.html'),
      controller: OrderController,
    })
};
