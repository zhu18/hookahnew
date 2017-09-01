class localResourcesController {
    constructor($scope, $rootScope, $http, $state, $stateParams, $uibModal, usSpinnerService, growl) {


        $scope.goodsStatuss = [{id:-1,name:'全部'},{id:0,name:'未上架'},{id:1,name:'已上架'}];
        $scope.onSaleStatus = $scope.goodsStatuss[0].id;
        $scope.search = function () {

            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/pushGoods/all",
                params:{
                        currentPage: $rootScope.pagination.currentPage,
                        pageSize: $rootScope.pagination.pageSize,
                        goodsName: $scope.goodsName?$scope.goodsName:null,
                        goodsSn:$scope.goodsSn?$scope.goodsSn:null,
                        keywords:$scope.keywords?$scope.keywords:null,
                        onSaleStatus:$scope.onSaleStatus
                }
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        };

        $scope.search();


        $scope.pageChanged = function () {//翻页
        		$scope.search();
        };//翻页


        $scope.checkSwitch = function(item,status){

               var   isOnsale;
               if(status){
                      isOnsale =1;
               }else{
                       isOnsale=0;
               }
               var promise = $http({
                        method:'post',
                        headers: {'Content-type': 'application/x-www-form-urlencoded'},
                        url:$rootScope.site.apiServer + "/api/pushGoods/edit",
                        data:{
                            isOnsale:isOnsale,
                            goodsId:item.goodsId
                        },
                        transformRequest: function(obj) {
                                var str = [];
                                for (var p in obj) {
                                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                                }
                                return str.join("&");
                        }
               });
               promise.then(function(res, status, config, headers){

                        $rootScope.loadingState = false;
                        $scope.search();
                        growl.addSuccessMessage("数据加载完毕。。。");
               });
        }

    }
}

export default localResourcesController;