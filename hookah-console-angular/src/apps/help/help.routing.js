import HelpController from './HelpController';
import pagination from 'angular-ui-bootstrap/src/pagination';
helpRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function helpRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/help/search');
  $stateProvider
    .state('help', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('help.search', {
      url: '/help/search',
      template: require('./list.html'),
      controller: HelpController,
    })
    .state('help.category', {
      url: '/help/category',
      template: require('./list.html'),
      controller: HelpController,
    })
    .state('help.detail', {
      url: '/innerMsg/read/{categoryId:[0-9]{1,4}}',
      templateUrl: '/msc/list',
      controller: HelpController,
    })
};
