
import facilitatorController from './facilitatorController';
// import detailsController from './detailsController'
// import transactionManageController from './transactionManageController'

publishRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function publishRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/facilitator/list');
  $stateProvider
    .state('facilitator', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('facilitator.list', {//发布大厅
      url: '/facilitator/list',
      template: require('./list.html'),
      controller: facilitatorController,
      permission: 'list'
    })

};