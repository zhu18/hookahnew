import GoodsController from "./GoodsController";
import GoodsCheckController from "./GoodsCheckController";
import ReadyGoodsCheckController from "./ReadyGoodsCheckController";
import ShelfController from "../shelf/ShelfController";
import ManageGoodsController from "../shelf/ManageGoodsController";
import CategoryController from "../category/CategoryController";
import ManageAttrTypeController from "../category/ManageAttrTypeController";
import AttrTypeController from "../attrtype/AttrTypeController";
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
      permission: 'items_search'
    })
    .state('items.searchByCon', {
      // params:{'searchName':null, 'searchSn':null, 'searchCheckStatus':null, 'searchOnSaleStatus':null},
      url: '/items/searchByCon',
      template: require('./list.html'),
      controller: GoodsController,
      permission: 'items_searchByCon'
    })
    .state('items.update', {
      url: '/items/update',
      template: require('./update.html'),
      controller: GoodsController,
      permission: 'items_update'
    })
    .state('items.lookDetail', {
      url: '/items/lookDetail',
      template: require('./lookDetail.html'),
      controller: GoodsController,
      permission: 'items_lookDetail'
    })
    .state('items.check', {
      url: '/items/check',
      template: require('./checkList.html'),
      controller: ReadyGoodsCheckController,
      permission: 'items_check'
    })
    .state('items.check2', {
      url: '/items/check2',
      template: require('./checkList.html'),
      controller: ReadyGoodsCheckController,
      permission: 'items_check2'
    })
    .state('items.checkDetail', {
      url: '/items/checkDetail',
      template: require('./checkDetail.html'),
      controller: GoodsCheckController,
      permission: 'items_checkDetail'
    })
    .state('items.checkedList', {
      url: '/items/checkedList',
      template: require('./goodsCheckedList.html'),
      // template: require('./checkedList.html'),
      controller: GoodsCheckController,
      permission: 'items_checkedList'
    })
    .state('items.checkedList2', {
      url: '/items/checkedList2',
      template: require('./goodsCheckedList.html'),
      controller: GoodsCheckController,
      permission: 'items_checkedList2'
    })
    .state('items.goodsDetail', {
      url: '/items/goodsDetail',
      template: require('./goodsDetail.html'),
      controller: GoodsCheckController,
      permission: 'items_goodsDetail'
    })
    .state('items.checkGoodsDetail', {
      url: '/items/checkGoodsDetail',
      template: require('./goodsDetail.html'),
      controller: ReadyGoodsCheckController,
      permission: 'items_checkGoodsDetail'
    })
    .state('shelf.search', {
      url: '/shelf/search',
      template: require('../shelf/list.html'),
      controller: ShelfController,
      permission: 'shelf_search'
    })
    .state('shelf.add', {
      url: '/shelf/add',
      template: require('../shelf/add.html'),
      controller: ShelfController,
      permission: 'shelf_add'
    })
    .state('shelf.update', {
      url: '/shelf/update',
      template: require('../shelf/update.html'),
      controller: ShelfController,
      permission: 'shelf_update'
    })
    .state('shelf.manageGoods', {
      params: {'data': null},
      url: '/shelf/manageGoods',
      template: require('../shelf/manageGoods.html'),
      controller: ManageGoodsController,
      permission: 'shelf_manageGoods'
    })
    .state('category.search', {
      url: '/category/search',
      template: require('../category/list.html'),
      controller: CategoryController,
      permission: 'category_search'
    })
    .state('category.add', {
      url: '/category/add',
      template: require('../category/add.html'),
      controller: CategoryController,
      permission: 'category_add'
    })
    .state('category.edit', {
      url: '/category/edit/:data',
      template: require('../category/edit.html'),
      controller: CategoryController,
      permission: 'category_edit'
    })
    .state('category.add_child', {
      url: '/category/add_child',
      template: require('../category/add_child.html'),
      controller: CategoryController,
      permission: 'category_add_child'
    })
    .state('category.edit_child', {
      url: '/category/edit_child',
      template: require('../category/add_child.html'),
      controller: CategoryController,
      permission: 'category_edit_child'
    })
    .state('category.manageAttrType', {
      url: '/category/manageAttrType/:cateId:cateName',
      template: require('../category/manageAttrType.html'),
      controller: ManageAttrTypeController,
      permission: 'category_manageAttrType'
    })
    .state('attrtype.search', {
      url: '/attrtype/search',
      template: require('../attrtype/list.html'),
      controller: AttrTypeController,
      permission: 'attrtype_search'
    })
    .state('attrtype.add', {
      url: '/attrtype/add',
      template: require('../attrtype/add.html'),
      controller: AttrTypeController,
      permission: 'attrtype_add'
    })
    .state('attrtype.edit', {
      url: '/attrtype/edit',
      template: require('../attrtype/edit.html'),
      controller: AttrTypeController,
      permission: 'attrtype_edit'
    })
    .state('attrtype.add_child', {
      url: '/attrtype/add_child',
      template: require('../attrtype/add_child.html'),
      controller: AttrTypeController,
      permission: 'attrtype_add_child'
    })
    .state('attrtype.edit_child', {
      url: '/attrtype/edit_child',
      template: require('../attrtype/add_child.html'),
      controller: AttrTypeController,
      permission: 'attrtype_edit_child'
    })
};
