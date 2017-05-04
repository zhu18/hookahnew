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
                cellTemplate: '<a ng-click="cellTemplateScope.remove(row.branch)">移除属性</a> <span class="text-explode">|</span> <i class="link-space"></i> ' ,
                cellTemplateScope: {
                    remove: function (data) {
                        $scope.delete(data);
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
                cellTemplate: '<a ng-click="cellTemplateScope.add(row.branch)">添加属性</a> <span class="text-explode">|</span> <i class="link-space"></i> ' ,
                cellTemplateScope: {
                    remove: function (data) {
                        $scope.delete(data);
                    }
                }
            }
        ];

        //分类内列表操作

        $scope.cate_attr_tree = {};

        $scope.treehandler = function (branch) {
            console.log(branch);
        };

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


        $scope.save = function () {
            if ($("#catId").val() != null && $("#catId").val() != '') {
                var promise = $http({
                    method: 'POST',
                    url: $rootScope.site.apiServer + "/api/category/edit",
                    data: $("#categoryForm").serialize()
                });
                promise.then(function (res, status, config, headers) {
                    $rootScope.loadingState = false;
                    if (res.data.code == 1) {
                        growl.addSuccessMessage("保存成功。。。");
                        $state.go("category.search");
                    } else {
                        growl.addErrorMessage("保存失败。。。");
                    }

                });
            }else {
                var promise = $http({
                    method: 'POST',
                    url: $rootScope.site.apiServer + "/api/category/add",
                    data: $("#categoryForm").serialize()
                });
                promise.then(function (res, status, config, headers) {
                    $rootScope.loadingState = false;
                    console.log(res.data);
                    if (res.data.code == 1) {
                        growl.addSuccessMessage("新增成功。。。");
                        $state.go("category.search");
                    } else {
                        growl.addErrorMessage("新增失败。。。");
                    }

                });
            }
        };



        $scope.refresh = function () {
            $scope.search();
        };


        $scope.search();

        $scope.searchNotContain();

    }
}

export default CategoryController;