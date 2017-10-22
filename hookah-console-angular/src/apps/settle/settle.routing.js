import settleController from "./SettleController";
import SettleDetailController from './SettleDetailController'
import transactionManageController from './transactionManageController'
import SettleAPILogsController from './SettleAPILogsController'

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
      permission: 'settleList'
    })
    .state('settle.settleDetails', {  //单条订单详情页
      url: '/settle/settleDetails/:id',
      template: require('./settleDetails.html'),
      controller: SettleDetailController,
      permission: 'settleDetails'

    })
    .state('settle.settleAPILogs', {  //单条订单详情页里API调用日志
      url: '/settle/settleAPILogs/:goodsSn/:orderSn',
      template: require('./settleAPILogs.html'),
      controller: SettleAPILogsController,
      permission: 'settleDetails'
    })
    .state('settle.transactionManage', {  //单条订单详情页
      url: '/settle/transactionManage/',
      template: require('./transactionManage.html'),
      controller: transactionManageController,
      permission: 'transactionManage'

    })
};
