import EsController from './EsController';
import pagination from 'angular-ui-bootstrap/src/pagination';
esRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function esRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/elastic/search');
  $stateProvider
    .state('elastic', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('elastic.search', {
      url: '/elastic/search',
      template: require('./list.html'),
      controller: EsController,
    })

};
