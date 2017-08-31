import localResourcesController from './localResourcesController';
import pagination from 'angular-ui-bootstrap/src/pagination';
localResourcesRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function localResourcesRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/localResources/list');
  $stateProvider
    .state('localResources', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('localResources.list', {
      url: '/localResources/list',
      template: require('./list.html'),
      controller: localResourcesController,
      permission: 'localResources'
    })
};
