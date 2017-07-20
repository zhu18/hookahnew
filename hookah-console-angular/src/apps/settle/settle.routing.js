import settleController from "./SettleController";
import SettleDetailController from './SettleDetailController'
import transactionManageController from './transactionManageController'

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
    .state('settle.settleDetails', {  //单条订单详情页
      url: '/settle/settleDetails/:id',
      template: require('./settleDetails.html'),
      controller: SettleDetailController,
    })
    .state('settle.transactionManage', {  //单条订单详情页
      url: '/settle/transactionManage/',
      template: require('./transactionManage.html'),
      controller: transactionManageController,
    })
};
