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
      controller: 'WorkorderListCtrl',
    })
    .state('items.add', {
      url: '/items/add',
      template: require('./add.html'),
      controller: 'WorkorderListCtrl',
    })
    .state('items.category', {
      url: '/items/category',
      template: require('./list.html'),
      controller: 'WorkorderListCtrl',
    })
    .state('items.detail', {
      url: '/innerMsg/read/{categoryId:[0-9]{1,4}}',
      templateUrl: '/msc/list',
      controller: 'WorkorderListCtrl',
    })
};
