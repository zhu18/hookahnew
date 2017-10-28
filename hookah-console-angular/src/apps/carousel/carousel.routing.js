import CarouselController from './CarouselController'
import EditController from './EditController'
carouselRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function carouselRouting($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise('/carousel/search');
	$stateProvider
		.state('carousel', {
			template: '<div ui-view></div>',
			showSubMenu: true
		})
		.state('carousel.search', {
			url: '/carousel/search',
			template: require('./list.html'),
			controller: CarouselController,
			permission: 'carousel_search'
		})
		.state('carousel.edit', {
			url: '/carousel/edit/:type/:id',
			params: {'data': null},
			template: require('./edit.html'),
			controller: EditController,
			permission: 'carousel_edit'
		})

};
