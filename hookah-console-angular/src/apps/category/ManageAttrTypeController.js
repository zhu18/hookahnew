class CategoryController {
    constructor($scope, $rootScope, $http, $state, $stateParams, $uibModal, usSpinnerService, growl) {
       //分类内属性树
        $scope.expanding_property = {
            field: "typeName",
            displayName: "属性名"
        };
        $scope.col_defs = [
            {
                field: "aa",
                displayName: "操作",
                cellTemplate: '<a ng-show="row.branch[\'isAttr\'] == 0" ng-click="cellTemplateScope.removeGoodsAttr(row.branch)">移除属性</a> <span class="text-explode">|</span> <i class="link-space"></i> ' ,
                cellTemplateScope: {
                    removeGoodsAttr: function (data) {
                        $scope.removeGoodsAttr(data);
                    }
                }
            }
        ];

        //当前分类不包含属性
        $scope.cate_notContain_expanding_property = {
            field: "typeName",
            displayName: "属性名"
        };
        $scope.cate_notContain_col_defs = [
            {
                field: "aa",
                displayName: "操作",
                cellTemplate: '<a ng-show="row.branch[\'level\'] != 1" ng-click="cellTemplateScope.addGoodsAttr(row.branch)">添加属性</a> <span class="text-explode">|</span> <i class="link-space"></i> ' ,
                cellTemplateScope: {
                    addGoodsAttr: function (data) {
                        $scope.addGoodsAttr(data);
                    }
                }
            }
        ];


        $scope.treehandler = function (branch) {
            console.log(branch);
        };

        //分类内列表操作
        $scope.cate_attr_tree = {};

        $scope.search = function () {
            $rootScope.cateName = $stateParams.cateName;
            console.log('cateId:'+$stateParams.cateId);
            console.log($stateParams.cateName);
            $rootScope.tree_data = [];
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/mgCateAttrType/findGoodsAttrByCateId/" + $stateParams.cateId
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                $scope.tree_data = res.data.data;
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        };


        //待添加列表操作
        $scope.cate_noContain_attr_tree = {};

        $scope.searchNotContain = function () {
            $rootScope.noContain_tree_data = [];
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/attrType/allTree"
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                $scope.noContain_tree_data = res.data.data;
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        };

        //添加属性
        $scope.addGoodsAttr = function (data) {
            var promise = $http({
                method: 'POST',
                url: $rootScope.site.apiServer + "/api/mgCateAttrType/addMgGoodsAttr",
                params:{cateId:$stateParams.cateId,attrTypeId:data.typeId}
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                if (res.data.code == 1) {
                    growl.addSuccessMessage("数据加载完毕。。。");
                    $scope.search();
                    $scope.searchNotContain();
                } else {
                    growl.addErrorMessage("。。。");
                }

            });
        };

        //移除属性
        $scope.removeGoodsAttr = function (data) {
            var promise = $http({
                method: 'POST',
                url: $rootScope.site.apiServer + "/api/mgCateAttrType/removeMgGoodsAttr",
                params:{cateId:$stateParams.cateId,attrTypeId:data.typeId}
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                if (res.data.code == 1) {
                    growl.addSuccessMessage("数据加载完毕。。。");
                    $scope.search();
                    $scope.searchNotContain();
                } else {
                    growl.addErrorMessage("。。。");
                }

            });
        };

        $scope.search();

        $scope.searchNotContain();

    }
}

export default CategoryController;