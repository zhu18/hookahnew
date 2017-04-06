import AccountController from './AccountController';
accountRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function accountRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/items/search');
  $stateProvider
    .state('account', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('account.search', {
      url: '/account/search',
      template: require('./list.html'),
      controller: AccountController,
    })
    .state('account.add', {
      url: '/account/add',
      template: require('./add.html'),
      controller: AccountController,
    })
    .state('account.detail', {
      url: '/innerMsg/read/{categoryId:[0-9]{1,4}}',
      templateUrl: '/msc/list',
      controller: AccountController,
    })
};
