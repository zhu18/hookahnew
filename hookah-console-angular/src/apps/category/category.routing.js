import CategoryController from './CategoryController';
import pagination from 'angular-ui-bootstrap/src/pagination';
categoryRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function categoryRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/category/search');
  $stateProvider
    .state('category', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('category.search', {
      url: '/category/search',
      template: require('./list.html'),
      controller: CategoryController,
    })
    .state('add.category', {
      url: '/add/category',
      template: require('./add.html'),
      controller: CategoryController,
    })
    .state('del.category', {
      url: '/innerMsg/read/{categoryId:[0-9]{1,4}}',
      templateUrl: '/msc/list',
      controller: CategoryController,
    })
};
