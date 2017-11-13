class couponController {
  constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {
      $scope.typeStatuss = [               //自定定义类型数据
          {id:-1, name:"全部"},
          {id:1, name:"注册赠券"},
          {id:2, name:"购物赠券"},
          {id:3, name:"全场赠券"},
          {id:4, name:"会员赠券"}
      ];
      $scope.pageSizes = [               //自定定义类型数据
          {id:-1, name:"显示条数"},
          {id:1, name:"20"},
          {id:2, name:"50"}
      ];
      $scope.controlScreenShow=true;
      $scope.controlScreenBtn=function () { //控制筛选盒子显隐的函数
          if ($scope.controlScreenShow){
              $scope.controlScreenShow=false;
          }else {
              $scope.controlScreenShow=true;

          }
      }
    $scope.search = function (initCurrentPage) { //Render page function
      //
      // var promise = $http({
      //   method: 'GET',
      //   url: $rootScope.site.apiServer + "/api/supplier/all",
      //   params: {
      //     checkStatus: $scope.checkStatus == 0 ? '0' : ($scope.checkStatus ? $scope.checkStatus : null),//审核状态
      //     contactPhone: $scope.contactPhone ? $scope.contactPhone : null,
      //     orgName: $scope.orgName ? $scope.orgName : null,
      //     startDate: $scope.startDate ? format($scope.startDate, 'yyyy-MM-dd HH:mm:ss') : null,
      //     endDate: $scope.endDate ? format($scope.endDate, 'yyyy-MM-dd HH:mm:ss') : null,
      //     currentPage: initCurrentPage == 'true' ? 1 :$rootScope.pagination.currentPage, //当前页码
      //     pageSize: $rootScope.pagination.pageSize,
      //   }
      // });
      // promise.then(function (res, status, config, headers) {
      //   console.log('数据在这里');
      //   console.log(res);
      //
      //   if (res.data.code == '1') {
      //     $scope.couponList = res.data.data.list;
      //     // $rootScope.pagination = res.data.data;
      //     $scope.showNoneDataInfoTip = false;
      //     if (res.data.data.list.length > 0) {
      //       if (res.data.data.totalPage > 1) {
      //         $scope.showPageHelpInfo = true;
      //       } else {
      //
      //         $scope.showPageHelpInfo = false;
      //
      //       }
      //     } else {
      //       $rootScope.loadingState = false;
      //       $scope.showNoneDataInfoTip = true;
      //     }
      //
      //
      //   } else {
      //     $scope.supplierList = [];
      //     $scope.showNoneDataInfoTip = true;
      //
      //   }
      //
      //   $rootScope.loadingState = false;
      //   growl.addSuccessMessage("订单数据加载完毕。。。");
      // });

    };
    $scope.refresh = function () {
      $scope.search();
    };
    $scope.pageChanged = function () {
      $scope.search();
    };

    $scope.add = function (id) { //Go to add page
        $state.go('coupon.add',{id:id})
    };
    $scope.getDetail = function (id) {//Go to detail page
        $state.go('coupon.detail',{id:id})
    };

  }
}

export default couponController;