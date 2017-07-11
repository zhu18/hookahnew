import HomeController from './HomeController';
homeRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function homeRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/home');
  $stateProvider
    .state('home', {
      url: '/home',
      // abstract: true,
      template: require('./home.html'),
      controller:HomeController,
      permission:'home'
    });
};
