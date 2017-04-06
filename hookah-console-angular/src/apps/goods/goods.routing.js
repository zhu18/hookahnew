import GoodsController from './GoodsController';
goodsRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function goodsRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/items/search');
  $stateProvider
    .state('items', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('items.search', {
      url: '/items/search',
      template: require('./list.html'),
      controller: GoodsController,
    })
    .state('items.category', {
      url: '/items/category',
      template: require('./list.html'),
      controller: GoodsController,
    })
    .state('items.detail', {
      url: '/innerMsg/read/{categoryId:[0-9]{1,4}}',
      templateUrl: '/msc/list',
      controller: GoodsController,
    })
};
