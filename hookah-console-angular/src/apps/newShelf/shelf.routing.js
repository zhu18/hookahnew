import ShelfController from './ShelfController'
import ManageGoodsController from './ManageGoodsController'
import EditTagsController from './EditTagsController'
shelfRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function shelfRouting($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise('/shelf/search');
	$stateProvider
		.state('shelf', {
			template: '<div ui-view></div>',
			showSubMenu: true
		})
		.state('shelf.search', {
			url: '/shelf/search',
			template: require('./list.html'),
			controller: ShelfController,
		})
		.state('shelf.add', {
			url: '/shelf/add',
			template: require('./add.html'),
			controller: ShelfController,
		})
		.state('shelf.update', {
			url: '/shelf/update',
			template: require('./update.html'),
			controller: ShelfController,
			permission: 'updateShelf'

		})
		.state('shelf.manageGoods', {
			url: '/shelf/manageGoods/:id/:name',
			params: {'data': null},
			template: require('./manageGoods.html'),
			controller: ManageGoodsController,
			permission: 'shelf_manageGoods'

		})
		.state('shelf.category', {
			url: '/shelf/category',
			template: require('./list.html'),
			controller: ShelfController,
		})
		.state('shelf.detail', {
			url: '/innerMsg/read/{categoryId:[0-9]{1,4}}',
			templateUrl: '/msc/list',
			controller: ShelfController,
		})
		.state('shelf.editTags', {
			url: '/shelf/editTags',
			template: require('./editTags.html'),
			controller: EditTagsController,
			permission: 'shelf_editTags'
		})
};
