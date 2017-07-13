import SupplierController from "./SupplierController";

supplierRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function supplierRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/supplier/list');
  $stateProvider
    .state('supplier', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('supplier.list', {
      url: '/supplier/list',
      template: require('./list.html'),
      controller: SupplierController,
      permission: 'supplier_search'
    })
};
