import ShelfController from './ShelfController'
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
      template: require('./list.html'),
      controller: ShelfController,
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
};
