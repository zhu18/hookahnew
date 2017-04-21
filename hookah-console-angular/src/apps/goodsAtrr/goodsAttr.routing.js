import GoodsAttrController from "./GoodsAttrController";
goodsAttrRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function goodsAttrRouting($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/goodsAttr/search');
    $stateProvider
        .state('goodsAttr', {
            template: '<div ui-view></div>',
            showSubMenu: true
        })
        .state('goodsAttr.search',{
            url: '/goodsAttr/search',
            template: require('./list.html'),
            controller: GoodsAttrController,
        })
        .state('goodsAttr.add', {
            url: '/goodsAttr/add',
            template: require('./add.html'),
            controller: GoodsAttrController,
        })
        .state('goodsAttr.edit', {
            url: '/goodsAttr/edit',
            template: require('./add.html'),
            controller: GoodsAttrController,
        })
        .state('goodsAttr.add_child', {
            url: '/goodsAttr/add_child',
            template: require('./add_child.html'),
            controller: GoodsAttrController,
        })
        .state('goodsAttr.edit_child', {
            url: '/goodsAttr/edit_child',
            template: require('./add_child.html'),
            controller: GoodsAttrController,
        })
};
