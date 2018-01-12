import invoiceListController from "./invoiceListController";
import invoiceListDetailsController from './invoiceListDetailsController';



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
    .state('invoiceList.details', {// 发票详情
      url: '/invoiceList/details/:id',
      template: require('./invoiceListDetails.html'),
      controller: invoiceListDetailsController,
      permission: 'invoiceList_details'
    })
    // .state('invoice.auditing', {  //增票资质审核
    //   url: '/invoice/auditing/:id',
    //   template: require('./auditing.html'),
    //   controller: invoiceAuditingController,
    //   permission: 'invoice_auditing'
    //
    // })
    // .state('invoiceAuditing.details', {  //增票资质审核详情页面
    //   url: '/invoiceAuditing/details/:id',
    //   template: require('./invoiceAuditingDetails.html'),
    //   controller: invoiceAuditingDetailsController,
    //   permission: 'invoiceAuditing_details'
    //
    // })
};
