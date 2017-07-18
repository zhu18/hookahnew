import settleController from "./SettleController";

settleRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function settleRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/settle/list');
  $stateProvider
    .state('settle', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('settle.list', {
      url: '/settle/list',
      template: require('./list.html'),
      controller: settleController,
      permission: 'settle_search'
    })
};
