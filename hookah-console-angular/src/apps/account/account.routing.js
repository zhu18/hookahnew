import AccountController from "./AccountController";
import RoleController from "./RoleController";
import PermissionController from "./PermissionController";
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
      permission: "account_search"
    })
    .state('account.add', {
      url: '/account/add',
      template: require('./add.html'),
      controller: AccountController,
      permission: "account_add"
    })
    .state('account.edit', {
      url: '/account/edit',
      template: require('./edit.html'),
      controller: AccountController,
      permission: "account_edit"
    })
    .state('account.role', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('account.role.search', {
      url: '/account/role/search',
      template: require('./role_list.html'),
      controller: RoleController,
      permission: "account_role_search"
    })
    .state('account.role.add', {
      url: '/account/role/add',
      template: require('./role_add.html'),
      controller: RoleController,
      permission: "account_role_add"
    })
    .state('account.role.edit', {
      url: '/account/role/edit',
      template: require('./role_add.html'),
      controller: RoleController,
      permission: "account_role_edit"
    })
    .state('account.permission', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('account.permission.search', {
      url: '/account/permission/search',
      template: require('./permission_list.html'),
      controller: PermissionController,
      permission: "account_permission_search"
    })
    .state('account.permission.add', {
      url: '/account/permission/add',
      template: require('./permission_add.html'),
      controller: PermissionController,
      permission: "account_permission_add"
    })
    .state('account.permission.setting', {
      url: '/account/permission/setting',
      template: require('./permission_add.html'),
      controller: PermissionController,
      permission: "account_permission_setting"
    })
      .state('account.editPassword', {
          url: '/account/editPassword',
          template: require('./editPassword.html'),
          controller: AccountController,
        permission: "account_editPassword"
      })

};
