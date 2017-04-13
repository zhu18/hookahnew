import SysController from "./SysController";
goodsRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function goodsRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/sys/dict/search');
  $stateProvider
    .state('sys', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('sys.dict', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('sys.dict.search', {
      url: '/sys/dict/search',
      template: require('./dict_list.html'),
      controller: SysController,
    })
    .state('sys.dict.add', {
      url: '/sys/dict/add',
      template: require('./dict_add.html'),
      controller: SysController,
    })
    .state('sys.dict.edit', {
      url: '/sys/dict/edit',
      templateUrl: '/msc/dict_add.html',
      controller: SysController,
    })
};
