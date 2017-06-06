import SysNoticController from "./SysNoticController";
sysnoticRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function sysnoticRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/sysnotic/search');
  $stateProvider
    .state('sysnotic', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('sysnotice.search', {
      url: '/sysnotice/search',
      template: require('./list.html'),
      controller: SysNoticController,
    })
    .state('sysnotic.add', {
      url: '/sysnotic/add',
      template: require('./add.html'),
      controller: SysNoticController,
    })
      .state('sysnotice.upd', {
          url: '/sysnotice/upd',
          template: require('./edit.html'),
          controller: SysNoticController,
      })

};
