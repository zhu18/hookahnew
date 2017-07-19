import MoneyController from "./MoneyController";

moneyRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function moneyRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/money/list');
  $stateProvider
    .state('money', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('money.list', {
      url: '/money/list',
      template: require('./list.html'),
      controller: MoneyController,
      permission: 'money_search'
    })
};
