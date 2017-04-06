import InformationController from './InformationController';
accountRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function accountRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/information/search');
  $stateProvider
    .state('information', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('information.search', {
      url: '/information/search',
      template: require('./list.html'),
      controller: InformationController,
    })
};
