class CategoryController {
    constructor($scope, $rootScope, $http, $state, $stateParams, $uibModal, usSpinnerService, growl) {
        if ($state.$current.name == "sys.dict.edit") {
            $scope.title = "分类修改";
            console.log($rootScope.editData);
        } else if ($state.$current.name == "sys.dict.add_child") {
            $rootScope.editData ={};
            console.log($rootScope.parentDict);
            $scope.title = "新增分类子项";
        } else if ($state.$current.name == "sys.dict.edit_child") {
            $scope.title = "修改分类子项";
        };
        $scope.expanding_property = "catName";
        $scope.col_defs = [
            {
                field: "domainId",
                displayName: "编码"
            },{
                field: "catDesc",
                displayName: "描述"
            }, {
                field: "isShow",
                displayName: "是否显示"
            }
            , {
                field: "aa",
                displayName: "操作",
                cellTemplate: '<a ng-click="cellTemplateScope.edit(row.branch)">修改</a> <span class="text-explode">|</span> <i class="link-space"></i> <a href="javascript:;" ng-click="cellTemplateScope.delete(row.branch)" target="_blank">删除</a> <span class="text-explode">|</span> <i class="link-space"></i> <a href="javascript:;" ng-click="cellTemplateScope.addChild(row.branch)" target="_blank">增加子分类</a>',
                cellTemplateScope: {
                    edit: function (data) {
                        $scope.edit(data);
                    },
                    delete: function (data) {
                        $scope.delete(data);
                    },
                    addChild: function (data) {
                        $scope.addChild(data);
                    }
                }
            }
        ];
        $scope.dict_tree = {};

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
            $scope.title = "新增分类";
        };
        $scope.addChild = function (data) {
            $rootScope.parentDict = data;
            $state.go("sys.dict.add_child");
        };
        $scope.edit = function (data) {
            $rootScope.editData = data;
            $state.go('sys.dict.edit', {data: data});
        };
        $scope.delete = function (data) {
            if (data.children.length == 0) {
                var modalInstance = $rootScope.openConfirmDialogModal("确定要删除" + '<span style="font-weight: bold;color: #6b3100">' + data.name + '</span>' + "分类项吗？");
                modalInstance.result.then(function () {
                    var promise = $http({
                        method: 'POST',
                        url: $rootScope.site.apiServer + "/api/sys/dict/delete/" + data.dictId
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
                $rootScope.openErrorDialogModal('<span style="font-weight: bold;color: #6b3100">' + data.name + '</span>' + "分类项下有子项，请先删除所有子项");
            }

        };
        $scope.save = function () {
            if ($("#dictId").val() != null && $("#dictId").val() != '') {
                var promise = $http({
                    method: 'POST',
                    url: $rootScope.site.apiServer + "/api/sys/dict/edit",
                    data: $("#dictForm").serialize()
                });
                promise.then(function (res, status, config, headers) {
                    $rootScope.loadingState = false;
                    if (res.data.code == 1) {
                        growl.addSuccessMessage("保存成功。。。");
                    } else {
                        growl.addErrorMessage("保存失败。。。");
                    }

                });
            } else {
                //TODO...验证
                var promise = $http({
                    method: 'POST',
                    url: $rootScope.site.apiServer + "/api/sys/dict/save",
                    data: $("#dictForm").serialize()
                });
                promise.then(function (res, status, config, headers) {
                    $rootScope.loadingState = false;
                    console.log(res.data);
                    if (res.data.code == 1) {
                        growl.addSuccessMessage("保存成功。。。");
                    } else {
                        growl.addErrorMessage("保存失败。。。");
                    }

                });
            }
        };
        $scope.refresh = function () {
            $scope.search();
        };


        $scope.search();


    }
}

export default CategoryController;