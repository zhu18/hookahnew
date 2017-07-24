import websiteController from "./websiteController";
import transactionController from "./transactionController";

operationalRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function operationalRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/operational/website');
  $stateProvider
    .state('operational', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('operational.website', {
      url: '/operational/website',
      template: require('./website.html'),
      controller: websiteController,
      permission: 'operational_search'
    })
    .state('operational.transaction', {
      url: '/operational/transaction',
      template: require('./transaction.html'),
      controller: transactionController,
      permission: 'operational_search'
    })
};
