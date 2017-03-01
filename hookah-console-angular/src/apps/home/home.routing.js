homeRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function homeRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/home');
  $stateProvider
    .state('home', {
      url: '/home',
      // abstract: true,
      template: require('./home.html'),
      controller: function ($scope, $rootScope) {
        $rootScope.config.productNavBar = 'disabled';
        $rootScope.config.sidebar = 'full';
        console.log("home page...");
      }
    });
};
