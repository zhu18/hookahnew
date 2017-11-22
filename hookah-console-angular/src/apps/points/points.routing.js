import pointsListController from "./pointsListController";
import pointsManageController from './pointsManageController'
import UserListDetailController from "../user/UserListDetailController";


pointsRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function pointsRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/points/list');
  $stateProvider
    .state('points', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('points.pointsList', { //积分列表
      url: '/points/pointsList',
      template: require('./pointsList.html'),
      controller: pointsListController,
      permission: 'pointsList'
    })
    .state('points.pointsManage', { //积分设置
      url: '/points/pointsManage',
      template: require('./pointsManage.html'),
      controller: pointsManageController,
      permission: 'pointsManage'
    })
    .state('points.detail', {
      url: '/points/detail/:id',
      template: require('../user/detail.html'),
      controller: UserListDetailController,
      permission: 'point_user_detail'
    })

  };
