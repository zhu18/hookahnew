/**
 * Created by elvis on 16/6/22.
 */
'use strict';
app.config(function ($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/user/all');
  $stateProvider
    // .state('account', {
    //   title: '工单列表',
    //   abstract: true,
    //   template: '<div ui-view></div>',
    //   showSubMenu: true
    // })
    .state('user', {
      url: '/user/all',
      title: '用户管理',
      templateUrl: '/static/console/1.0.0/pages/account/user_all.html',
      controller: 'WorkorderListCtrl',
    })
    .state('role', {
      url: '/role/all',
      title: '角色管理',
      templateUrl: '/msc/list',
      controller: 'WorkorderListCtrl',
    })
    .state('permission', {
      url: 'permission/all',
      title: '权限管理',
      templateUrl: '/msc/list',
      controller: 'WorkorderListCtrl',
    })
});
app.run(function ($rootScope) {
  $rootScope.config.title = '账号中心';
  $rootScope.config.mainNav =[
    {
      "title":"用户管理",
      "url":"user/all",

    },
    {
      "title":"角色管理",
      "url":"role/all"
    },
    {
      "title":"权限管理",
      "url":"permission/all"
    }
  ];
});

angular.module('lomen').controller('WorkorderListCtrl', function ($rootScope) {
  console.log("work....."+$rootScope);
});