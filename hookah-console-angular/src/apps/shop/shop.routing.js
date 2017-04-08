import ShopController from './ShopController';
shopRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function shopRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/shop/search');
  $stateProvider
    .state('shop', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('shop.search', {
      url: '/shop/search',
      template: require('./list.html'),
      controller: ShopController,
    })
    .state('shop.add', {
      url: '/shop/add',
      template: require('./add.html'),
      controller: ShopController,
    })
    .state('shop.edit', {
      url: '/shop/edit',
      template: require('./edit.html'),
      controller: ShopController,
    })
};
