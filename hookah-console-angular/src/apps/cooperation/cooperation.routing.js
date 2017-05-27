import CooperationController from "./CooperationController";
cooperationRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function cooperationRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/cooperation/search');
  $stateProvider
    .state('cooperation', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('cooperation.search', {
      url: '/cooperation/search',
      template: require('./list.html'),
      controller: CooperationController,
    })
    .state('cooperation.add', {
      url: '/cooperation/add',
      template: require('./add.html'),
      controller: CooperationController,
    })

};
