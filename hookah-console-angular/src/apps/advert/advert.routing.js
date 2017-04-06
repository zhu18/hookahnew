import AdvertController from './AdvertController';
advertRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function advertRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/advert/search');
  $stateProvider
    .state('advert', {
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
    .state('advert.edit', {
      url: '/advert/edit',
      template: require('./edit.html'),
      controller: AdvertController,
    })
};
