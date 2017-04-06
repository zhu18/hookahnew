import UserController from './UserController';
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
    .state('user.add', {
      url: '/user/add',
      template: require('./add.html'),
      controller: UserController,
    })
    .state('user.edit', {
      url: '/user/edit',
      template: require('./edit.html'),
      controller: UserController,
    })
};
