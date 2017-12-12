import ShelfOldController from "../shelf/ShelfOldController";
import ManageGoodsOldController from "../Shelf/ManageGoodsOldController";
import ShelfController from "../newShelf/ShelfController";
import ManageGoodsController from "../newShelf/ManageGoodsController";
import EditTagsController from "../newShelf/EditTagsController";
import EditController from "../newShelf/EditController";
import CategoryController from "../category/CategoryController";
import TagsListController from "./TagsListController";
import MoveController from "../category/MoveController";
import ManageAttrTypeController from "../category/ManageAttrTypeController";
import AttrTypeController from "../attrtype/AttrTypeController";
import GoodsListController from "./GoodsListController";
import GoodsDetailController from "./GoodsDetailController";
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
		.state('shelfold', {
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
			params: {'data': null},
			template: require('./goodsList.html'),
			controller: GoodsListController,
			permission: 'items_search'
		})
		.state('items.check', {
			url: '/items/check',
			params: {'data': null},
			template: require('./goodsList.html'),
			controller: GoodsListController,
			permission: 'items_check'
		})
		.state('items.checkedList', {
			url: '/items/checkedList',
			params: {'data': null},
			template: require('./goodsList.html'),
			controller: GoodsListController,
			permission: 'items_checkedList'
		})
		.state('items.goodsDetail', {
			url: '/items/goodsDetail/:id/:isEdit',
			params: {'data': null},
			template: require('./goodsDetail.html'),
			controller: GoodsDetailController,
			permission: 'items_goodsDetail'
		})
		.state('items.tags', {
			url: '/items/tags',
			template: require('./tagsList.html'),
			controller: TagsListController,
			permission: 'items_tags'
		})
		// .state('shelf.search', {
		// 	url: '/shelf/search',
		// 	template: require('../shelf/list.html'),
		// 	controller: ShelfController,
		// 	permission: 'shelf_search'
		// })
		// .state('shelf.add', {
		// 	url: '/shelf/add',
		// 	template: require('../shelf/add.html'),
		// 	controller: ShelfController,
		// 	permission: 'shelf_add'
		// })
		// .state('shelf.update', {
		// 	url: '/shelf/update',
		// 	template: require('../shelf/update.html'),
		// 	controller: ShelfController,
		// 	permission: 'shelf_update'
		// })
		// .state('shelf.manageGoods', {
		// 	params: {'data': null},
		// 	url: '/shelf/manageGoods',
		// 	template: require('../shelf/manageGoods.html'),
		// 	controller: ManageGoodsController,
		// 	permission: 'goodsGOshelfManageGoods'
		// })
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
		.state('category.move', {
			url: '/category/move',
			template: require('../category/move.html'),
			controller: MoveController,
			permission: 'category_move'
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
		.state('shelf.search', {
			url: '/shelf/search',
			template: require('../newShelf/list.html'),
			controller: ShelfController,
			permission: 'shelf_search'
		})
		.state('shelf.edit', {
			url: '/shelf/edit/:type/:id',
			params: {'data': null},
			template: require('../newShelf/add.html'),
			controller: EditController,
			permission: 'shelf_edit'
		})
		.state('shelf.update', {
			url: '/shelf/update',
			template: require('../newShelf/update.html'),
			controller: ShelfController,
			permission: 'shelf_update'
		})
		.state('shelf.manageGoods', {
			params: {'data': null},
			url: '/shelf/manageGoods/:id/:name',
			template: require('../newShelf/manageGoods.html'),
			controller: ManageGoodsController,
			permission: 'goodsGOshelfManageGoods'
		})
		.state('shelf.editTags', {
			url: '/shelf/editTags',
			template: require('../newShelf/editTags.html'),
			controller: EditTagsController,
			permission: 'shelf_editTags'
		})
		.state('shelfold.search', {
			url: '/shelfold/search',
			template: require('../shelf/list.html'),
			controller: ShelfOldController,
			permission: 'shelfold_search'
		})
		.state('shelfold.add', {
			url: '/shelfold/add',
			template: require('../shelf/add.html'),
			controller: ShelfOldController,
			permission: 'shelfold_add'
		})
		.state('shelfold.update', {
			url: '/shelfold/update',
			template: require('../shelf/update.html'),
			controller: ShelfOldController,
			permission: 'shelfold_update'
		})
		.state('shelfold.manageGoods', {
			params: {'data': null},
			url: '/shelfold/manageGoods',
			template: require('../shelf/manageGoods.html'),
			controller: ManageGoodsOldController,
			permission: 'goodsGOshelfManageGoods'
		})

};
