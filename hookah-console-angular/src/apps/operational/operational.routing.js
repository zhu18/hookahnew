import websiteController from "./websiteController";
import transactionController from "./transactionController";
import operateController from "./operateController";
import transactionOperateController from "./transactionOperateController";

operationalRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function operationalRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/operational/website');
  $stateProvider
    .state('operational', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('operational.website', {//网站流量统计分析
      url: '/operational/website',
      template: require('./website.html'),
      controller: websiteController,
      permission: 'operational_website'
    })
    .state('operational.transaction', { //交易数据统计分析
      url: '/operational/transaction',
      template: require('./transaction.html'),
      controller: transactionController,
      permission: 'operationalTransaction'
    })
    .state('operational.transactionOperate', { //交易运营统计分析
      url: '/operational/operate',
      template: require('./transactionOperate.html'),
      controller: transactionOperateController,
      permission: 'transactionOperate'
    })

    .state('operational.operate', {//运营流量统计分析
      url: '/operational/operate',
      template: require('./operate.html'),
      controller: operateController,
      permission: 'operateTransaction'
    })
};
