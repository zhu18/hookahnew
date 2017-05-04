import GoodsController from './GoodsController';
import GoodsCheckController from './GoodsCheckController';
import ShelfController from '../shelf/ShelfController';
import ManageGoodsController from '../shelf/ManageGoodsController';
import pagination from 'angular-ui-bootstrap/src/pagination';
goodsRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function goodsRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/items/search');
  $stateProvider
    .state('items', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('shelf', {
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
    .state('items.lookDetail', {
      url: '/items/lookDetail',
      template: require('./lookDetail.html'),
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
    .state('shelf.search', {
      url: '/shelf/search',
      template: require('../shelf/list.html'),
      controller: ShelfController,
    })
    .state('shelf.add', {
      url: '/shelf/add',
      template: require('../shelf/add.html'),
      controller: ShelfController,
    })
    .state('shelf.update', {
      url: '/shelf/update',
      template: require('../shelf/update.html'),
      controller: ShelfController,
    })
    .state('shelf.manageGoods', {
      params:{'data':null},
      url: '/shelf/manageGoods',
      template: require('../shelf/manageGoods.html'),
      controller: ManageGoodsController,
    })

};
