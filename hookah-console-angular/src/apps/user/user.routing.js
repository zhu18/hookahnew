import UserController from "./UserController";
import UserVerifyController from "./UserVerifyController";
import UserListDetailController from "./UserListDetailController";
import rechargeController from "./rechargeController";
userRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function userRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/user/search');
  $stateProvider
    .state('user', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('user.search', {
      url: '/user/search',
      template: require('./list.html'),
      controller: UserController,
      permission: 'user_search'
    })
    .state('user.detail', {
      url: '/user/detail/:id',
      template: require('./detail.html'),
      controller: UserListDetailController,
      permission: 'user_detail'
    })
    .state('user.verify', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('user.verifyDetail', {
      url: '/user/verifyDetail',
      template: require('./verifyDetail.html'),
      controller: UserVerifyController,
      permission: 'user_verifyDetail'
    })
    .state('user.verify.all', {
      url: '/user/verify/all',
      template: require('./checkList.html'),
      controller: UserVerifyController,
      permission: 'user_verify_all'
    })
    .state('user.verify.person', {
      url: '/user/verify/person',
      template: require('./list.html'),
      controller: UserVerifyController,
      permission: 'user_verify_person'
    })
    .state('user.verify.company', {
      url: '/user/verify/company',
      template: require('./list.html'),
      controller: UserVerifyController,
      permission: 'user_verify_company'
    })
    .state('user.verify.checkUserDetail', {
      url: '/user/verify/checkUserDetail',
      template: require('./checkUserDetail.html'),
      controller: UserVerifyController,
      permission: 'user_verify_checkUserDetail'
    })
    .state('user.verify.resultAll', {
      url: '/user/verify/resultAll',
      template: require('./checkResultList.html'),
      controller: UserVerifyController,
      permission: 'user_verify_resultAll'
    })
    .state('user.recharge', {
      url: '/user/recharge',
      template: require('./recharge.html'),
      params: {'item': null},
      controller: rechargeController,
      permission: 'user_verify_recharge'
    })
};
