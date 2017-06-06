import GoodsController from './GoodsController';
import GoodsCheckController from './GoodsCheckController';
import ReadyGoodsCheckController from './ReadyGoodsCheckController';
import ShelfController from '../shelf/ShelfController';
import ManageGoodsController from '../shelf/ManageGoodsController';
import CategoryController from '../category/CategoryController';
import ManageAttrTypeController from '../category/ManageAttrTypeController';
import AttrTypeController from '../attrtype/AttrTypeController';
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
    .state('category', {
      template: '<div ui-view></div>',
      showSubMenu: true
     })
    .state('attrtype', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('items.search', {
      url: '/items/search',
      template: require('./list.html'),
      controller: GoodsController,
    })
    .state('items.searchByCon', {
        // params:{'searchName':null, 'searchSn':null, 'searchCheckStatus':null, 'searchOnSaleStatus':null},
      url: '/items/searchByCon',
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
        controller: ReadyGoodsCheckController,
    })
    .state('items.check2', {
        url: '/items/check2',
        template: require('./checkList.html'),
        controller: ReadyGoodsCheckController,
    })
    .state('items.checkDetail', {
        url: '/items/checkDetail',
        template: require('./checkDetail.html'),
        controller: GoodsCheckController,
    })
    .state('items.checkedList', {
        url: '/items/checkedList',
        template: require('./goodsCheckedList.html'),
        // template: require('./checkedList.html'),
        controller: GoodsCheckController,
    })
    .state('items.checkedList2', {
        url: '/items/checkedList2',
        template: require('./goodsCheckedList.html'),
        controller: GoodsCheckController,
    })
    .state('items.goodsDetail', {
        url: '/items/goodsDetail',
        template: require('./goodsDetail.html'),
        controller: GoodsCheckController,
    })
    .state('items.checkGoodsDetail', {
        url: '/items/checkGoodsDetail',
        template: require('./goodsDetail.html'),
        controller: ReadyGoodsCheckController,
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
  .state('category.search', {
      url: '/category/search',
      template: require('../category/list.html'),
      controller: CategoryController,
  })
  .state('category.add', {
      url: '/category/add',
      template: require('../category/add.html'),
      controller: CategoryController,
  })
  .state('category.edit', {
      url: '/category/edit/:data',
      template: require('../category/edit.html'),
      controller: CategoryController,
  })
  .state('category.add_child', {
      url: '/category/add_child',
      template: require('../category/add_child.html'),
      controller: CategoryController,
  })
  .state('category.edit_child', {
      url: '/category/edit_child',
      template: require('../category/add_child.html'),
      controller: CategoryController,
  })
  .state('category.manageAttrType',  {
      url: '/category/manageAttrType/:cateId:cateName',
      template: require('../category/manageAttrType.html'),
      controller: ManageAttrTypeController,
  })
  .state('attrtype.search', {
      url: '/attrtype/search',
      template: require('../attrtype/list.html'),
      controller: AttrTypeController,
  })
  .state('attrtype.add', {
      url: '/attrtype/add',
      template: require('../attrtype/add.html'),
      controller: AttrTypeController,
  })
  .state('attrtype.edit', {
      url: '/attrtype/edit',
      template: require('../attrtype/edit.html'),
      controller: AttrTypeController,
  })
  .state('attrtype.add_child', {
      url: '/attrtype/add_child',
      template: require('../attrtype/add_child.html'),
      controller: AttrTypeController,
  })
  .state('attrtype.edit_child', {
      url: '/attrtype/edit_child',
      template: require('../attrtype/add_child.html'),
      controller: AttrTypeController,
  })
};
