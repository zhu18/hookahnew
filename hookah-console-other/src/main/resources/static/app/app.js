var app = angular
  .module('Hookah', [
    'ui.bootstrap',
    'ui.router',
    'angularSpinner',
    'angular-growl'
  ]);
app.config(function ($stateProvider, $urlRouterProvider, growlProvider, usSpinnerConfigProvider) {
  growlProvider.globalTimeToLive(3000);
  var opts = {
    lines: 13 // The number of lines to draw
    , length: 16 // The length of each line
    , width: 4 // The line thickness
    , radius: 15 // The radius of the inner circle
    , scale: 1 // Scales overall size of the spinner
    , corners: 1 // Corner roundness (0..1)
    , color: '#000' // #rgb or #rrggbb or array of colors
    , opacity: 0.25 // Opacity of the lines
    , rotate: 0 // The rotation offset
    , direction: 1 // 1: clockwise, -1: counterclockwise
    , speed: 1 // Rounds per second
    , trail: 60 // Afterglow percentage
    , fps: 20 // Frames per second when using setTimeout() as a fallback for CSS
    , zIndex: 2e9 // The z-index (defaults to 2000000000)
    , className: 'spinner' // The CSS class to assign to the spinner
    , top: '50%' // Top position relative to parent
    , left: '50%' // Left position relative to parent
    , shadow: true // Whether to render a shadow
    , hwaccel: false // Whether to use hardware acceleration
    , position: 'relative' // Element positioning
  };
  usSpinnerConfigProvider.setDefaults(opts);

  $urlRouterProvider.otherwise('/');
  $stateProvider
    .state('home', {
      url: '/',
      controller: function ($scope, $rootScope) {
        $rootScope.config.productNavBar = 'disable';
        $rootScope.config.sidebar = 'mini';
        console.log("home page...");
      }
    });
});
app.run(function ($rootScope, $state) {
  $rootScope.config = {
    "disableNavigation": false,
    "hideSidebar": false,
    "sidebar": "full",
    "productNavBar": "col-1"
  };
  $rootScope.navConfig = {
    "product": {
      "show": true,
      "folded": false
    },
    "service": {
      "show": true,
      "folded": false
    },
    "system": {
      "show": true,
      "folded": false
    }
  };
  $rootScope.navScene = 'main';
  $rootScope.isSidebarFold = false;
  $rootScope.toggleSidebarStatus = function () {
    $rootScope.isSidebarFold = $rootScope.isSidebarFold ? false : true;
    $rootScope.config.sidebar = $rootScope.config.sidebar == 'mini' ? 'full' : 'mini';
  };
  $rootScope.collapseProductNavbar = function () {
    $rootScope.config.productNavBar = $rootScope.config.productNavBar == 'col-1' ? '' : 'col-1';
  };
  $rootScope.toggleFoldStatus = function (event, obj) {
    obj.folded = obj.folded == true ? false : true;
  };
});

app.controller('HomeController', function ($scope, $rootScope) {
  $rootScope.config.productNavBar = 'mini';
  $rootScope.isSidebarFold = true;
});

