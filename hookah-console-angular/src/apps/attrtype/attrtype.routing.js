import AttrTypeController from './AttrTypeController';
attrtypeRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function attrtypeRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/attrtype/search');
  $stateProvider
    .state('attrtype', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('attrtype.search', {
      url: '/attrtype/search',
      template: require('./list.html'),
      controller: AttrTypeController,
    })
    .state('attrtype.add', {
      url: '/attrtype/add',
      template: require('./add.html'),
      controller: AttrTypeController,
    })
    .state('attrtype.edit', {
        url: '/attrtype/edit',
        template: require('./add.html'),
        controller: AttrTypeController,
    })
    .state('attrtype.add_child', {
        url: '/attrtype/add_child',
        template: require('./add_child.html'),
        controller: AttrTypeController,
    })
    .state('attrtype.edit_child', {
        url: '/attrtype/edit_child',
        template: require('./add_child.html'),
        controller: AttrTypeController,
    })
};
