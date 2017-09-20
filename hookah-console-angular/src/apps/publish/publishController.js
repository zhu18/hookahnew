class publishController {
  constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {

    $scope.search = function (initCurrentPage) {

      var promise = $http({
        method: 'GET',
        url: $rootScope.site.crowdServer + "/require/allRequirement",
        params: {
          requireSn: $scope.requireSn ? $scope.requireSn : null,
          status: $scope.status,//审核状态
          title: $scope.title ? $scope.title : null,
          currentPage: initCurrentPage == 'true' ? 1 :$rootScope.pagination.currentPage, //当前页码
          pageSize: $rootScope.pagination.pageSize
        }

      });
      promise.then(function (res, status, config, headers) {
        console.log('数据在这里');
        console.log(res);
        if (res.data.code == '1') {
          var publishList = res.data.data.list;
          $scope.publishList=[];
          angular.forEach(publishList,function (item) {
              if(item.status==1 || item.status==4||item.status==5||item.status==9||item.status==11||item.status==16||item.status==14||item.status==15){
                $scope.publishList.push(item);
              }
          })
            $scope.showNoneDataInfoTip = true;
          if (res.data.data.totalPage > 0) {
            $scope.showNoneDataInfoTip = false;
            $scope.showPageHelpInfo = true;
          }else {
              $scope.showPageHelpInfo = true;
          }
        } else {
          $scope.settleList = [];
          $scope.showNoneDataInfoTip = true;
        }
        $rootScope.loadingState = false;
        growl.addSuccessMessage("订单数据加载完毕。。。");
      });
    };

      $scope.search();

    // 去发布
    $scope.public = function (id) {
      $state.go('publish.public', {id: id});
    };
    // 去审核
    $scope.auditing = function (id) {
      $state.go('publish.auditing', {id: id});
    };
    // 去查看
    $scope.getDetails = function (id) {
      $state.go('publish.details', {id: id});
    };

    $scope.pageChanged = function () {
      $scope.search();
      console.log('Page changed to: ' + $rootScope.pagination.currentPage);
    };


  }
}

export default publishController;