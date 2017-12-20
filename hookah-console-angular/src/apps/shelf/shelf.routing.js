import ShelfOldController from './ShelfOldController'
import ManageGoodsOldController from './ManageGoodsOldController'
shelfRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function shelfRouting($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise('/shelfold/search');
	$stateProvider
		.state('shelfold', {
			template: '<div ui-view></div>',
			showSubMenu: true
		})
		.state('shelfold.search', {
			url: '/shelfold/search',
			template: require('./list.html'),
			controller: ShelfOldController,
			permission: 'shelfold_search'
		})
		.state('shelfold.add', {
			url: '/shelfold/add',
			template: require('./add.html'),
			controller: ShelfOldController,
			permission: 'shelfold_add'
		})
		.state('shelfold.update', {
			url: '/shelfold/update',
			template: require('./update.html'),
			controller: ShelfOldController,
			permission: 'shelfold_update'
		})
		.state('shelfold.manageGoods', {
			params: {'data': null},
			url: '/shelfold/manageGoods',
			template: require('./manageGoods.html'),
			controller: ManageGoodsOldController,
			permission: 'shelfold_manageGoods'
		})
		.state('shelfold.category', {
			url: '/shelfold/category',
			template: require('./list.html'),
			controller: ShelfOldController,
			permission: 'shelfold_category'
		})
		.state('shelfold.detail', {
			url: '/innerMsg/read/{categoryId:[0-9]{1,4}}',
			templateUrl: '/msc/list',
			controller: ShelfOldController,
			permission: 'shelfold_detail'
		})
};
