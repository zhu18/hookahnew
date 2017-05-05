import SysDictController from "./SysDictController";
import RegionController from "./RegionController";
import IndustryController from "./IndustryController";
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
    .state('sys.region', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('sys.industry', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('sys.dict.search', {
      url: '/sys/dict/search',
      template: require('./dict_list.html'),
      controller: SysDictController,
    })
    .state('sys.region.search', {
      url: '/sys/region/search',
      template: require('./region_list.html'),
      controller: RegionController,
    })
    .state('sys.industry.search', {
      url: '/sys/industry/search',
      template: require('./industry_list.html'),
      controller: IndustryController,
    })
    .state('sys.dict.add', {
      url: '/sys/dict/add',
      template: require('./dict_add.html'),
      controller: SysDictController,
    })
    .state('sys.dict.edit', {
      url: '/sys/dict/edit',
      template: require('./dict_add.html'),
      controller: SysDictController,
    })
    .state('sys.dict.add_child', {
      url: '/sys/dict/add_child',
      template: require('./dict_add_child.html'),
      controller: SysDictController,
    })
    .state('sys.dict.edit_child', {
      url: '/sys/dict/edit_child',
      template: require('./dict_add_child.html'),
      controller: SysDictController,
    })
    .state('sys.region.add', {
        url: '/sys/region/add',
        template: require('./region_add.html'),
        controller: RegionController,
    })
};
