import invoiceListController from "./invoiceListController";
import invoiceListDetailsController from './invoiceListDetailsController';
import invoiceAuditingController from './invoiceAuditingController';
import invoiceAuditingDetailsController from './invoiceAuditingDetailsController';

invoiceRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function invoiceRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/invoice/list');
  $stateProvider
    .state('invoice', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('invoice.list', {//发票查询
      url: '/invoice/list',
      template: require('./list.html'),
      controller: invoiceListController,
      permission: 'invoice_list'
    })
    .state('invoice.listDetails', {// 发票详情
      url: '/invoice/listDetails/:id/:type',
      template: require('./invoiceListDetails.html'),
      controller: invoiceListDetailsController,
      permission: 'invoiceList_listDetails'
    })
    .state('invoice.auditing', {  //增票资质审核
      url: '/invoice/auditing',
      template: require('./auditing.html'),
      controller: invoiceAuditingController,
      permission: 'invoice_auditing'

    })
    .state('invoice.auditingDetails', {  //增票资质审核详情页面
      url: '/invoice/auditingDetails/:id/:type',
      template: require('./invoiceAuditingDetails.html'),
      controller: invoiceAuditingDetailsController,
      permission: 'invoice_auditingDetails'

    })
};
