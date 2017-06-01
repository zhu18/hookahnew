import AdvertController from './AdvertController';
import AdvertCarouselController from './AdvertCarouselController';
import SysNoticController from '../sysnotic/SysNoticController';
import CooperationController from '../cooperation/CooperationController';
advertRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function advertRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/advert/search');
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
    })
    .state('advert.add', {
      url: '/advert/add',
      template: require('./add.html'),
      controller: AdvertController,
    })
    .state('advert.carousel', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('advert.carousel.search', {
      url: '/advert/carousel',
      template: require('./carousel_list.html'),
      controller: AdvertCarouselController,
    })
    .state('advert.carousel.edit', {
       url: '/advert/carousel',
       template: require('./carousel_edit.html'),
       controller: AdvertCarouselController,
    })
    .state('advert.carousel.add', {
       url: '/advert/carousel',
       template: require('./carousel_add.html'),
       controller: AdvertCarouselController,
    })
    .state('cooperation.search', {
      url: '/cooperation/search',
      template: require('../cooperation/list.html'),
      controller: CooperationController,
    })
    .state('cooperation.add', {
        url: '/cooperation/add',
        template: require('../cooperation/add.html'),
        controller: CooperationController,
    })
    .state('cooperation.edit', {
        url: '/cooperation/edit',
        template: require('../cooperation/edit.html'),
        controller: CooperationController,
    })
      .state('sysnotice.search', {
          url: '/sysnotice/search',
          template: require('../sysnotic/list.html'),
          controller: SysNoticController,
      })
      .state('sysnotice.add', {
          url: '/sysnotice/add',
          template: require('../sysnotic/add.html'),
          controller: SysNoticController,
      })
      .state('sysnotice.edit', {
          url: '/sysnotice/edit',
          template: require('../sysnotic/edit.html'),
          controller: SysNoticController,
      })
};
