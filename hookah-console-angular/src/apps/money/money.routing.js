import MoneyController from "./MoneyController";
import getMoneyDetailController from "./getMoneyDetailController";

moneyRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function moneyRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/money/getMoneyList');
  $stateProvider
    .state('money', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('money.getMoneyList', {
      url: '/money/getMoneyList',
      template: require('./getMoneyList.html'),
      controller: MoneyController,
      permission: 'money_search'
    })
    .state('money.getMoneyDetail', {  //单条订单详情页
      url: '/money/getMoneyDetail/:id',
      template: require('./getMoneyDetail.html'),
      controller: getMoneyDetailController,
    })
};
