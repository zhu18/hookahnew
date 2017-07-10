import AdvertController from "./AdvertController";
import AdvertCarouselController from "./AdvertCarouselController";
import SysNoticController from "../sysnotic/SysNoticController";
import CooperationController from "../cooperation/CooperationController";
advertRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function advertRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/advert/carousel');
  $stateProvider
    .state('advert', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('cooperation', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('sysnotice', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('advert.search', {
      url: '/advert/search',
      template: require('./list.html'),
      controller: AdvertController,
      permission: 'advert_search'
    })
    .state('advert.add', {
      url: '/advert/add',
      template: require('./add.html'),
      controller: AdvertController,
      permission: 'advert_add'
    })
    .state('advert.carousel', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('advert.carousel.search', {
      url: '/advert/carousel',
      template: require('./carousel_list.html'),
      controller: AdvertCarouselController,
      permission: 'advert_carousel_search'
    })
    .state('advert.carousel.edit', {
      url: '/advert/carousel',
      template: require('./carousel_edit.html'),
      controller: AdvertCarouselController,
      permission: 'advert_carousel_edit'
    })
    .state('advert.carousel.add', {
      url: '/advert/carousel',
      template: require('./carousel_add.html'),
      controller: AdvertCarouselController,
      permission: 'advert_carousel_add'
    })
    .state('cooperation.search', {
      url: '/cooperation/search',
      template: require('../cooperation/list.html'),
      controller: CooperationController,
      permission: 'cooperation_search'
    })
    .state('cooperation.add', {
      url: '/cooperation/add',
      template: require('../cooperation/add.html'),
      controller: CooperationController,
      permission: 'cooperation_add'
    })
    .state('cooperation.edit', {
      url: '/cooperation/edit',
      template: require('../cooperation/edit.html'),
      controller: CooperationController,
      permission: 'cooperation_edit'
    })
    .state('sysnotice.search', {
      url: '/sysnotice/search',
      template: require('../sysnotic/list.html'),
      controller: SysNoticController,
      permission: 'sysnotice_search'
    })
    .state('sysnotice.add', {
      url: '/sysnotice/add',
      template: require('../sysnotic/add.html'),
      controller: SysNoticController,
      permission: 'sysnotice_add'
    })
    .state('sysnotice.edit', {
      url: '/sysnotice/edit',
      template: require('../sysnotic/edit.html'),
      controller: SysNoticController,
      permission: 'sysnotice_edit'
    })
};
