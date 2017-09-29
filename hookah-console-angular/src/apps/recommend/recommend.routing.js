import RecommendController from "./RecommendController";
import RecommendDetailController from "./RecommendDetailController";
recommendRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function recommendRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/recommend/list');
  $stateProvider
    .state('recommend', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('recommend.list', {
      url: '/recommend/list',
      template: require('./list.html'),
      controller: RecommendController,
      permission: 'recommend_list'
    })
    .state('recommend.detail', {
      url: '/recommend/detail/:id',
      template: require('./detail.html'),
      controller: RecommendDetailController,
      permission: 'recommend_detail'
    })
};
