import NewsController from './NewsController';
accountRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function accountRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/news/search');
  $stateProvider
    .state('news', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('news.search', {
      url: '/news/search',
      template: require('./list.html'),
      controller: NewsController,
    })
};
