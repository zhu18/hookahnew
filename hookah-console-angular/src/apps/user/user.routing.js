import UserController from './UserController';
import UserVerifyController from './UserVerifyController';
userRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function userRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/user/search');
  $stateProvider
    .state('user', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('user.search', {
      url: '/user/search',
      template: require('./list.html'),
      controller: UserController,
    })
    .state('user.detail', {
      url: '/user/detail',
      template: require('./detail.html'),
      controller: UserController,
    })
    .state('user.verify', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('user.verify.all', {
      url: '/user/verify/all',
      template: require('./list.html'),
      controller: UserVerifyController,
    })
    .state('user.verify.person', {
      url: '/user/verify/person',
      template: require('./list.html'),
      controller: UserVerifyController,
    })
    .state('user.verify.company', {
      url: '/user/verify/company',
      template: require('./list.html'),
      controller: UserVerifyController,
    })
};
