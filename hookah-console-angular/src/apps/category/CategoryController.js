class CategoryController {
    constructor($scope, $rootScope, $http, $state, $stateParams, $uibModal, usSpinnerService, growl) {
        console.log("1111111111111111111:" + $stateParams.data);
        if ($state.$current.name == "category.edit") {
            $scope.title = "分类修改";
            console.log($rootScope.editData);
        } else if ($state.$current.name == "category.add_child") {
            $rootScope.editData ={};
            console.log($rootScope.parentDict);
            $scope.title = "新增分类子项";
        } else if ($state.$current.name == "category.edit_child") {
            $scope.title = "修改分类子项";
        }else if($state.$current.name == "category.add"){
            $scope.title = "新增分类";
        };
        $scope.expanding_property = {
            field: "catName",
            displayName: "分类名称"
        };
        $scope.col_defs = [
            {
                field: "domainId",
                displayName: "编码"
            },{
                field: "catDesc",
                displayName: "描述"
            }, {
                field: "isShow",
                displayName: "是否启用",
                cellTemplate:'<span>{{ row.branch[col.field] | Status }}</span>'
            }
            , {
                field: "aa",
                displayName: "操作",
                cellTemplate: '<a ng-click="cellTemplateScope.edit(row.branch)">修改</a> <span class="text-explode">|</span> <i class="link-space"></i> ' +
                               '<a href="javascript:;" ng-click="cellTemplateScope.delete(row.branch)" target="_blank">删除</a> <span class="text-explode">|</span> <i class="link-space"></i> ' +
                               '<a href="javascript:;" ng-click="cellTemplateScope.addChild(row.branch)" target="_blank">增加子分类</a><span class="text-explode">|</span> <i class="link-space"></i>' +
                               '<a href="javascript:;" ng-show="row.branch[\'level\'] == 1" ng-click="cellTemplateScope.manageAttrType(row.branch)" target="_blank">管理属性</a>',
                cellTemplateScope: {
                    edit: function (data) {
                        $scope.edit(data);
                    },
                    delete: function (data) {
                        $scope.delete(data);
                    },
                    addChild: function (data) {
                        $scope.addChild(data);
                    },
                    manageAttrType:function (data) {
                        $scope.addChild(data);
                    }
                }
            }
        ];
         $scope.cate_tree = {};

        $scope.treehandler = function (branch) {
            console.log(branch);
        };
        $scope.search = function () {
            $rootScope.tree_data = [];
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/category/allTree"
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                $scope.tree_data = res.data.data;
                // console.log("11111111111112222------" + res.data.data);
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        };
        $scope.add = function () {
            // $scope.title = "新增分类";
            // $state.go("category.add");
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

        };
        $scope.addChild = function (data) {
            $rootScope.parentCategory = data;
            $state.go("category.add_child");
        };
        $scope.edit = function (data) {
            $rootScope.editData = data;
            $state.go('category.edit', {data: data});
        };
        $scope.delete = function (data) {
            if (data.children.length == 0) {
                var modalInstance = $rootScope.openConfirmDialogModal("确定要删除" + '<span style="font-weight: bold;color: #6b3100">' + data.catName + '</span>' + "分类项吗？");
                modalInstance.result.then(function () {
                    var promise = $http({
                        method: 'POST',
                        url: $rootScope.site.apiServer + "/api/category/delete/" + data.catId
                    });
                    promise.then(function (res, status, config, headers) {
                        $rootScope.loadingState = false;
                        console.log(res);
                        if (res.data.code == 1) {
                            growl.addSuccessMessage("删除成功。。。");
                            $scope.search();
                        } else {
                            growl.addErrorMessage("删除失败。。。");
                        }

                    });
                }, function () {
                    // $log.info('Modal dismissed at: ' + new Date());
                });
            } else {
                $rootScope.openErrorDialogModal('<span style="font-weight: bold;color: #6b3100">' + data.catName + '</span>' + "分类项下有子项，请先删除所有子项");
            }

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


        if ($state.$current.name == "category.search") {
            $scope.search();
        }

    }
}

export default CategoryController;