import AccountController from "./AccountController";
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
    .state('account.role', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('account.role.search', {
      url: '/account/role/search',
      template: require('./edit.html'),
      controller: AccountController,
    })
    .state('account.role.add', {
      url: '/account/role/add',
      template: require('./edit.html'),
      controller: AccountController,
    })
    .state('account.role.setting', {
      url: '/account/role/setting',
      template: require('./edit.html'),
      controller: AccountController,
    })
    .state('account.permission', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('account.permission.search', {
      url: '/account/permission/search',
      template: require('./edit.html'),
      controller: AccountController,
    })
    .state('account.permission.add', {
      url: '/account/permission/add',
      template: require('./edit.html'),
      controller: AccountController,
    })
    .state('account.permission.setting', {
      url: '/account/permission/setting',
      template: require('./edit.html'),
      controller: AccountController,
    })
      .state('account.editPassword', {
          url: '/account/editPassword',
          template: require('./editPassword.html'),
          controller: AccountController,
      })

};
