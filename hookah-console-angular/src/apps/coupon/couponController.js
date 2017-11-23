class couponController {
  constructor($scope, $rootScope, $http, $state,growl) {
      $scope.typeStatuss = [               //自定定义类型数据
          {id:"", name:"全部"},
          {id:0, name:"注册赠券"},
          {id:1, name:"购物赠券"},
          {id:2, name:"全场赠券"},
          {id:3, name:"会员赠券"}
      ];
      $scope.couponType="";  //设置默认值
      $scope.pageSizes = [               //自定定义类型数据
          {id:'20', name:"20"},
          {id:'50', name:"50"}
      ];
      $rootScope.pagination.pageSize="20";    //设置默认值
      $scope.controlScreen=function () { //控制搜索框展示函数
          $scope.controlScreenShow=true;
          $scope.screenTitle='收起筛选';
          $scope.controlScreenBtn=function () { //控制筛选盒子显隐的函数
              if ($scope.controlScreenShow){
                  $scope.controlScreenShow=false;
                  $scope.screenTitle='展开筛选';
              }else {
                  $scope.controlScreenShow=true;
                  $scope.screenTitle='收起筛选';
              }
          };
      }
      $scope.controlScreen()
    $scope.search = function (initCurrentPage) { //Render page function
      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/coupon/all",
        params: {
          couponName: $scope.couponName,//审核状态
          couponType: $scope.couponType,
          type: 0,
          currentPage: initCurrentPage == 'true' ? 1 :$rootScope.pagination.currentPage, //当前页码
          pageSize: $rootScope.pagination.pageSize,
        }
      });
      promise.then(function (res) {
        console.log('数据在这里');
        console.log(res);
           if (res.data.code == '1') {
              $scope.couponList = res.data.data.list;
              $scope.showNoneDataInfoTip = false;
              if (res.data.data.list.length > 0) {

                  if (res.data.data.totalPage > 1) {
                      $scope.showPageHelpInfo = true;
                  } else {
                      $scope.showPageHelpInfo = false;

                  }
              } else {
                  $rootScope.loadingState = false;
                  $scope.showNoneDataInfoTip = true;
              }


          } else {
              $scope.couponList = [];
              $scope.showNoneDataInfoTip = true;
           }
        $rootScope.loadingState = false;
        growl.addSuccessMessage("订单数据加载完毕。。。");
      });

    }; //列表搜索
    $scope.delete=function (id) {
        var promise = $http({
            method: 'POST',
            url: $rootScope.site.apiServer + "/api/coupon/delete",
            params: {
                couponId:id
            }
        });
        promise.then(function (res, status, config, headers) {
            console.log('数据在这里');
            console.log(res);

            if (res.data.code == '1') {
                var modalInstance =$rootScope.openConfirmDialogModal("删除成功！");
                modalInstance.result.then(function () {
                    $scope.search();
                }, function () {

                });
            } else {
                var modalInstance =$rootScope.openConfirmDialogModal("删除失败！");
                modalInstance.result.then(function () {

                }, function () {

                });
            }

            $rootScope.loadingState = false;
            growl.addSuccessMessage("订单数据加载完毕。。。");
        });
    }  //删除功能

    $scope.search();
    $scope.refresh = function () {
      $scope.search();
    };
    $scope.pageChanged = function () {
      $scope.search();
    };

    $scope.add = function () { //Go to add page
        $state.go('coupon.add')
    };
    $scope.edit = function (id) { //Go to add page
        $state.go('coupon.add',{id:id})
    };
    $scope.getDetail = function (id) {//Go to detail page
        $state.go('coupon.couponDetail',{id:id})
    };

  }
}

export default couponController;