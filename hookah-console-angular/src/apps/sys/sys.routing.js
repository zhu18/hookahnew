import SysDictController from "./SysDictController";
import RegionController from "./RegionController";
import IndustryController from "./IndustryController";
goodsRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function goodsRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/sys/region/search');
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
      permission: 'sys_dict_search'
    })
    .state('sys.region.search', {
      url: '/sys/region/search',
      template: require('./region_list.html'),
      controller: RegionController,
      permission: 'sys_region_search'
    })
    .state('sys.industry.search', {
      url: '/sys/industry/search',
      template: require('./industry_list.html'),
      controller: IndustryController,
      permission: 'sys_industry_search'
    })
    .state('sys.dict.add', {
      url: '/sys/dict/add',
      template: require('./dict_add.html'),
      controller: SysDictController,
      permission: 'sys_dict_add'
    })
    .state('sys.dict.edit', {
      url: '/sys/dict/edit',
      template: require('./dict_add.html'),
      controller: SysDictController,
      permission: 'sys_dict_edit'
    })
    .state('sys.dict.add_child', {
      url: '/sys/dict/add_child',
      template: require('./dict_add_child.html'),
      controller: SysDictController,
      permission: 'sys_dict_add_child'
    })
    .state('sys.dict.edit_child', {
      url: '/sys/dict/edit_child',
      template: require('./dict_add_child.html'),
      controller: SysDictController,
      permission: 'sys_dict_edit_child'
    })
    .state('sys.region.add', {
      url: '/sys/region/add',
      template: require('./region_add.html'),
      controller: RegionController,
      permission: 'sys_region_add'
    })
};
