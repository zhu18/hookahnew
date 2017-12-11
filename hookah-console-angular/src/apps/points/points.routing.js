import pointsListController from "./pointsListController";
import pointsManageController from './pointsManageController'
import pointsDetailController from "./pointsDetailController";
import editPointsRulelController from "./editPointsRuleController";
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
    .state('points.pointsDetail', { //积分明细
      url: '/points/pointsDetail/:userBasePointsInfo',
      template: require('./pointsDetail.html'),
      controller: pointsDetailController,
      permission: 'pointsDetail'
    })
    .state('points.userDetail', {
      url: '/points/userDetail/:id',
      template: require('../user/detail.html'),
      controller: UserListDetailController,
      permission: 'points_user_detail'
    })
    .state('points.pointsManage', { //积分设置
      url: '/points/pointsManage',
      template: require('./pointsManage.html'),
      controller: pointsManageController,
      permission: 'pointsManage'
    })

  };
