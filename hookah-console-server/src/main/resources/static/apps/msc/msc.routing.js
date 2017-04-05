mscRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function mscRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/innerMsg/all/0');
  $stateProvider
    .state('innerMsg', {
      title: '工单列表',
      // abstract: true,
      // template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('innerMsg.all', {
      url: '/innerMsg/all/{categoryId:[0-9]{1,4}}',
      title: '工单列表',
      templateUrl: '/msc/list',
      controller: 'WorkorderListCtrl',
    })
    .state('innerMsg.unread', {
      url: '/innerMsg/unread/{categoryId:[0-9]{1,4}}',
      title: '工单列表',
      templateUrl: '/msc/list',
      controller: 'WorkorderListCtrl',
    })
    .state('innerMsg.read', {
      url: '/innerMsg/read/{categoryId:[0-9]{1,4}}',
      title: '工单列表',
      templateUrl: '/msc/list',
      controller: 'WorkorderListCtrl',
    })
};
