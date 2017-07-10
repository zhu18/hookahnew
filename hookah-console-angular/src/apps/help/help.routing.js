import HelpController from "./HelpController";
import HelpCategoryController from "./HelpCategoryController";
helpRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function helpRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/help/search');
  $stateProvider
    .state('help', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('help.search', {
      url: '/help/search',
      template: require('./list.html'),
      controller: HelpController,
      permission: 'help_search'
    })
    .state('help.add', {
      url: '/help/add',
      template: require('./add.html'),
      controller: HelpController,
      permission: 'help_add'
    })
    .state('help.edit', {
      url: '/help/edit',
      template: require('./add.html'),
      controller: HelpController,
      permission: 'help_edit'
    })
    .state('help.category', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('help.category.search', {
      url: '/help/category/search',
      template: require('./help_category_list.html'),
      controller: HelpCategoryController,
      permission: 'help_category_search'
    })
    .state('help.category.add', {
      url: '/help/category/add',
      template: require('./help_category_add.html'),
      controller: HelpCategoryController,
      permission: 'help_category_add'
    })
    .state('help.category.edit', {
      url: '/help/category/edit',
      template: require('./help_category_edit.html'),
      controller: HelpCategoryController,
      permission: 'help_category_edit'
    })
};
