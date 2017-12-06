import websiteController from "./websiteController";
import transactionController from "./transactionController";
import operateController from "./operateController";

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
      permission: 'operational_website'
    })
    .state('operational.transaction', {
      url: '/operational/transaction',
      template: require('./transaction.html'),
      controller: transactionController,
      permission: 'operationalTransaction'
    })
    .state('operational.operate', {
      url: '/operational/operate',
      template: require('./operate.html'),
      controller: operateController,
      permission: 'operateTransaction'
    })
};
