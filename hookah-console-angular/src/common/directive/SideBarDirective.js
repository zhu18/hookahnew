class SideBarDirective {
  constructor() {
    this.restrict = 'EA';
    this.template = require('./sidebar.html');
    this.replace = true;
    this.controller = SideBarDirectiveController;
    this.controllerAs = 'sideBar';
  }
  static directiveFactory() {
    SideBarDirective.instance = new SideBarDirective();
    return SideBarDirective.instance;
  }
}
// SideBar.directiveFactory.$inject = ['$rootScope'];

class SideBarDirectiveController {
  constructor($rootScope) {
    this.$rootScope = $rootScope;
  }

  toggleSidebarStatus() {
    this.$rootScope.config.isSidebarFold = this.$rootScope.config.isSidebarFold ? false : true;
    this.$rootScope.config.sidebar = this.$rootScope.config.sidebar == 'mini' ? 'full' : 'mini';
  };
  toggleFoldStatus(event, obj) {
    obj.folded = obj.folded == true ? false : true;
  };
}
SideBarDirectiveController.$inject = ['$rootScope'];

export default SideBarDirective;