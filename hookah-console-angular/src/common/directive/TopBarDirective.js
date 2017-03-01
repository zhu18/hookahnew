class TopBarDirective {
  constructor() {
    this.restrict = 'EA';
    this.template = require('./topbar.html');
    this.replace = true;
  }
  static directiveFactory(){
    TopBarDirective.instance =new TopBarDirective();
    return TopBarDirective.instance;
  }
}

export default TopBarDirective;