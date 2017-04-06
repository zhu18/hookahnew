import AccountController from './AccountController';
accountRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function accountRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/account/search');
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
    .state('account.edit', {
      url: '/account/edit',
      template: require('./edit.html'),
      controller: AccountController,
    })
};
