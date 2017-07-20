import MessageController from './MessageController';
import pagination from 'angular-ui-bootstrap/src/pagination';
categoryRouting.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider'];
export default function categoryRouting($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/message/system/search');
  $stateProvider
    .state('message', {
        template: '<div ui-view></div>',
        showSubMenu: true
    })
    .state('message.system', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('message.sms', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('message.email', {
      template: '<div ui-view></div>',
      showSubMenu: true
    })
    .state('message.template', {
        template: '<div ui-view></div>',
        showSubMenu: true
    })
    .state('message.system.search', {
      url: '/message/system/search',
      template: require('./list_system.html'),
      controller: MessageController,
    })
    .state('message.sms.search', {
      url: '/message/sms/search',
      template: require('./list_sms.html'),
      controller: MessageController,
    })
    .state('message.email.search', {
      url: '/message/email/search',
        template: require('./list_email.html'),
      controller: MessageController,
    })
    .state('message.template.add', {
        url: '/message/template/add',
        template: require('./add_template.html'),
        controller: MessageController,
    })
    .state('message.template.search', {
      url: '/message/template/search',
      template: require('./list_template.html'),
      controller: MessageController,
    })
    .state('message.template.edit', {
        url: '/message/template/edit',
        params: {'data': null},
        template: require('./edit_template.html'),
        controller: MessageController,
    })
};
