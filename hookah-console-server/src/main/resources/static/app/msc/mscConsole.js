/**
 * Created by elvis on 16/6/22.
 */
'use strict';
app.config(function ($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/innerMsg/all/0');
  $stateProvider
    .state('innerMsg', {
      title: '工单列表',
      abstract: true,
      template: '<div ui-view></div>',
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
});
app.run(function ($rootScope) {
  $rootScope.config.title = '消息中心';
  $rootScope.config.mainNav =[
    {
      "title":"站内消息",
      "showChild":true,
      "childs":[
        {
          "title":"全部消息",
          "url":"innerMsg.all({categoryId:0})"
        },
        {
          "title":"未读消息",
          "url":"innerMsg.unread({categoryId:0})"
        },
        {
          "title":"已读消息",
          "url":"innerMsg.read({categoryId:0})"
        }
      ]
    }
  ];
});

angular.module('lomen').controller('WorkorderListCtrl', function ($rootScope) {
  console.log("work....."+$rootScope);
});