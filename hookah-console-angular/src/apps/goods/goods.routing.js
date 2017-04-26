import GoodsController from './GoodsController';
import GoodsCheckController from './GoodsCheckController';
import pagination from 'angular-ui-bootstrap/src/pagination';
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
    .state('items.update', {
      url: '/items/update',
      template: require('./update.html'),
      controller: GoodsController,
    })
    .state('items.category', {
      url: '/items/category',
      template: require('./list.html'),
      controller: GoodsController,
    })
    .state('items.check', {
        url: '/items/check',
        template: require('./checkList.html'),
        controller: GoodsCheckController,
    })
    .state('items.checkDetail', {
        url: '/items/checkDetail',
        template: require('./checkDetail.html'),
        controller: GoodsCheckController,
    })
    .state('items.checkedList', {
        url: '/items/checkedList',
        template: require('./checkedList.html'),
        controller: GoodsCheckController,
    })
    .state('items.goodsDetail', {
        url: '/items/goodsDetail',
        template: require('./goodsDetail.html'),
        controller: GoodsCheckController,
    })
    .state('items.detail', {
      url: '/innerMsg/read/{categoryId:[0-9]{1,4}}',
      templateUrl: '/msc/list',
      controller: GoodsController,
    })

};
