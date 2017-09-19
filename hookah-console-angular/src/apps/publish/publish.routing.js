import publishController from "./publishController";
import publicController from './publicController';
import auditingController from './auditingController';
import detailsController from './detailsController'
// import transactionManageController from './transactionManageController'

publishRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function publishRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/publish/list');
  $stateProvider
    .state('publish', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('publish.list', {//发布大厅
      url: '/publish/list',
      template: require('./list.html'),
      controller: publishController,
      permission: 'publishList'
    })
    .state('publish.details', {//发布大厅
      url: '/publish/details/:id',
      template: require('./details.html'),
      controller: detailsController,
      permission: 'publishDetails'
    })
    .state('publish.public', {  //发布页面
      url: '/publish/public/:id',
      template: require('./public.html'),
      controller: publicController,
      permission: 'publishPublic'

    })
    .state('publish.auditing', {  //审核页面
      url: '/publish/auditing/:id',
      template: require('./auditing.html'),
      controller: auditingController,
      permission: 'publishAuditing'

    })
};
