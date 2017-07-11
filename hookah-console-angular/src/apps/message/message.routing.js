import CategoryController from './CategoryController';
import ManageAttrTypeController from './ManageAttrTypeController';
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
    .state('category.add', {
      url: '/category/add',
      template: require('./add.html'),
      controller: CategoryController,
    })
    .state('category.del', {
      url: '/innerMsg/read/{categoryId:[0-9]{1,4}}',
      templateUrl: '/msc/list',
      controller: CategoryController,
    })
    .state('category.edit', {
        url: '/category/edit/:data',
        template: require('./edit.html'),
        controller: CategoryController,
    })
    .state('category.add_child', {
        url: '/category/add_child',
        template: require('./add_child.html'),
        controller: CategoryController,
    })
    .state('category.edit_child', {
        url: '/category/edit_child',
        template: require('./add_child.html'),
        controller: CategoryController,
    })
  .state('category.manageAttrType',  {
      url: '/category/manageAttrType/:data',
      template: require('./manageAttrType.html'),
      controller: ManageAttrTypeController,
  })
};
