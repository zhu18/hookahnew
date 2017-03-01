class ProductNavbarDirective {
  constructor() {
    this.restrict = 'EA';
    this.template = require('./productNavbar.html');
    this.replace = true;
    this.controller = ProductNavbarDirectiveController;
    this.controllerAs = 'vm';
  }

  static directiveFactory() {
    ProductNavbarDirective.instance = new ProductNavbarDirective();
    return ProductNavbarDirective.instance;
  }
}
class ProductNavbarDirectiveController {
  constructor($rootScope) {
    this.$rootScope = $rootScope;
  }
  collapseProductNavbar() {
    console.log("navbar click");
    this.$rootScope.config.productNavBar = this.$rootScope.config.productNavBar == 'col-1' ? '' : 'col-1';
  }
}
ProductNavbarDirectiveController.$inject = ['$rootScope'];

export default ProductNavbarDirective;