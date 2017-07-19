import getMoneyListController from "./getMoneyListController";
import getMoneyDetailController from "./getMoneyDetailController";
import showMoneyListController from "./showMoneyListController";
import userMoneyManageController from "./userMoneyManageController";
import showUserMoneyDetailController from "./showUserMoneyDetailController";
import platformFundManageController from "./platformFundManageController";

moneyRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function moneyRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/money/getMoneyList');
  $stateProvider
    .state('money', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('money.getMoneyList', {//提现列表
      url: '/money/getMoneyList',
      template: require('./getMoneyList.html'),
      controller: getMoneyListController,
      permission: 'money_search'
    })
    .state('money.getMoneyDetail', {  //提现详情
      url: '/money/getMoneyDetail/:id',
      template: require('./getMoneyDetail.html'),
      controller: getMoneyDetailController,
    })
    .state('money.showMoneyList', {//资金流水
      url: '/money/showMoneyList',
      template: require('./showMoneyList.html'),
      controller: showMoneyListController,
      permission: 'showMoneyList'
    })
    .state('money.userMoneyManage', { //用户资金管理
      url: '/money/userMoneyManage',
      template: require('./userMoneyManage.html'),
      controller: userMoneyManageController,
      permission: 'userMoneyManage'
    })
    .state('money.showUserMoneyDetail', { //用户资金记录详情
      url: '/money/showUserMoneyDetail',
      template: require('./showUserMoneyDetail.html'),
      params: {'item': null},
      controller: showUserMoneyDetailController,
      permission: 'showUserMoneyDetail'
    })
    .state('money.platformFundManage', { //平台资金管理
      url: '/money/platformFundManage',
      template: require('./platformFundManage.html'),
      controller: platformFundManageController,
      permission: 'platformFundManage'
    })
};
