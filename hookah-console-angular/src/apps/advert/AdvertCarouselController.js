class AccountCarouselController {
    constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {
      console.log($rootScope.config);

    $scope.search = function(){
        var promise = $http({
            method: 'GET',
            url: $rootScope.site.apiServer + "/api/advert/all",
            params: {currentPage: $rootScope.pagination.currentPage,
                pageSize: $rootScope.pagination.pageSize,
                advertOrder: $scope.advertOrder,
                addTime: $scope.addTime,
                url: $scope.url,
                href: $scope.href,
                creatorName: $scope.creatorName,
                advertGroup: $scope.advertGroup
            }
        });
        promise.then(function (res, status, config, headers) {
            $rootScope.loadingState = false;
            console.log($rootScope.pagination);
            growl.addSuccessMessage("数据加载完毕。。。");
        });
    };

    $scope.add = function(){
        var promise = $http({
            method: 'POST',
            url: $rootScope.site.apiServer + "/api/advert/add",
            data: $("#carouselForm").serialize()
        });
        promise.then(function (res, status, config, headers) {
            console.log(res.data)
            if(res.data.code == "1"){
                growl.addSuccessMessage("数据添加完毕。。。");
                $state.go('advert.carousel.search');
            }
        });
    };

     $scope.delete = function (event, item) {
         var promise = $http({
             method: 'POST',
             url: $rootScope.site.apiServer + "/api/advert/delete",
             params: {
                 advertId: item.advertId,
             }
         });

         promise.then(function (res, status, config, headers) {
             $rootScope.loadingState = false;
             //alert(res.data.message);
             $scope.search();
             growl.addSuccessMessage("数据加载完毕。。。");
         });
     };

    $scope.load = function (event, item) {
        var promise = $http({
            method: 'GET',
            url: $rootScope.site.apiServer + "/api/advert/" + item.advertId,
        });
        promise.then(function (res, status, config, headers) {
            $rootScope.loadingState = false;
            $rootScope.cuserd=res.data.data;
            growl.addSuccessMessage("数据加载完毕。。。");
        });
    };

    $scope.edit = function(){
        var promise = $http({
            method: 'POST',
            url: $rootScope.site.apiServer + "/api/advert/update",
            params: {
                advertId:$("#advertId").val(),
                url: $("#url").val(),
                href: $("#href").val(),
                advertOrder: $("#advertOrder").val(),
                advertGroup: $("#advertGroup").val()
            }
            /*data: $("#carouselForm").serialize()*/
        });
        promise.then(function (res, status, config, headers) {
            console.log(res.data)
            if(res.data.code == "1"){
                growl.addSuccessMessage("数据修改完毕。。。");
                $state.go('advert.carousel.search');
            }
        });
    };

    $scope.pageChanged = function () {
       $scope.search();
       console.log('Page changed to: ' + $rootScope.pagination.currentPage);
    };

      $scope.search();
  }
}

export default AccountCarouselController;