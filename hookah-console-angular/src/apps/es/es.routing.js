import EsController from "./EsController";
esRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function esRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/elastic/search');
  $stateProvider
    .state('elastic', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('elastic.search', {
      url: '/elastic/search',
      template: require('./list.html'),
      controller: EsController,
      permission: 'elastic_search'
    })
    .state('elastic.add', {
      url: '/elastic/add',
      template: require('./add.html'),
      controller: EsController,
      permission: 'elastic_add'
    })
    .state('elastic.delGoods', {
      url: '/elastic/delGoods',
      template: require('./goodslist.html'),
      controller: EsController,
      permission:'elastic_delGoods'
    })

};
