class HomeController {
  constructor($scope, $rootScope, $uibModal, usSpinnerService) {
    $rootScope.config.productNavBar = 'disabled';
    $rootScope.config.sidebar = 'full';
    console.log($rootScope.config);
    $.ajax({
      type: "GET",
      url: $rootScope.site.apiServer+"/api/home/init",
      success: function (data) {
        console.log(data);
        $scope.owner = data.data;
        console.log(data.data);
      },
      error: function (XMLHttpRequest, textStatus, errorThrown) {
        console.log(XMLHttpRequest);
        // var currUrl = window.location;
        // console.log(currUrl);
        // if (401 === XMLHttpRequest.status) {
        //   window.location.href = "http://auth.pybdex.com/oauth/authorize?client_id=admin&response_type=code&redirect_uri=" + currUrl;
        // }
      }
    });
  }
}

export default HomeController;