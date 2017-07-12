import CommentController from "./CommentController";

commentRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function commentRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/comment/review');
  $stateProvider
    .state('comment', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('comment.review', {
      url: '/comment/review',
      template: require('./review.html'),
      controller: CommentController,
      permission: 'comment_search'
    })
};
