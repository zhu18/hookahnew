class AttrTypeController {
    constructor($scope, $rootScope, $http, $state, $stateParams, $uibModal, usSpinnerService, growl) {

        if ($state.$current.name == "attrtype.edit") {
            $scope.title = "属性修改";
            console.log($rootScope.editData);
        } else if ($state.$current.name == "attrtype.add_child") {
            $rootScope.editData ={};
            console.log($rootScope.parentDict);
            $scope.title = "新增属性子项";
        } else if ($state.$current.name == "attrtype.edit_child") {
            $scope.title = "修改属性子项";
        } else if($state.$current.name == "attrtype.add"){
            $scope.title = "新增属性";
        };

        $scope.expanding_property = {
            field: "typeName",
            displayName: "属性名"
        };
        $scope.col_defs = [
            {
                field: "domainId",
                displayName: "编码"
            }, {
                field: "typeStatus",
                displayName: "状态",
                cellTemplate:'<span>{{ row.branch[col.field] | Status }}</span>'
            },{
                field: "isAttr",
                displayName: "节点",
                cellTemplate:'<span>{{ row.branch[col.field] | isAttr}}</span>'
            }
            , {
                field: "aa",
                displayName: "操作",
                cellTemplate: '<a ng-click="cellTemplateScope.edit(row.branch)">修改</a> <span class="text-explode">|</span> <i class="link-space"></i> <a href="javascript:;" ng-click="cellTemplateScope.delete(row.branch)" target="_blank">删除</a> <span class="text-explode">|</span> <i class="link-space"></i> <a href="javascript:;" ng-click="cellTemplateScope.addChild(row.branch)" target="_blank">增加子属性</a>',
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
        $scope.type_tree = {};

        $scope.treehandler = function (branch) {
            console.log(branch);
        };
        $scope.search = function () {
            $rootScope.tree_data = [];
            var promise = $http({
                method: 'GET',
                url: $rootScope.site.apiServer + "/api/attrType/allTree"
            });
            promise.then(function (res, status, config, headers) {
                $rootScope.loadingState = false;
                $scope.tree_data = res.data.data;
                // console.log("11111111111112222------" + res.data.data);
                growl.addSuccessMessage("数据加载完毕。。。");
            });
        };
        $scope.add = function () {
            $scope.title = "新增属性";
        };
        $scope.addChild = function (data) {
            $rootScope.parentAttrType = data;
            console.log(data);
            $state.go("attrtype.add_child");
        };
        $scope.edit = function (data) {
            $rootScope.editData = data;
            $state.go('attrtype.edit', {data: data});
        };
        $scope.delete = function (data) {
            if (data.children.length == 0) {
                var modalInstance = $rootScope.openConfirmDialogModal("确定要删除" + '<span style="font-weight: bold;color: #6b3100">' + data.typeName + '</span>' + "属性项吗？");
                modalInstance.result.then(function () {
                    var promise = $http({
                        method: 'POST',
                        url: $rootScope.site.apiServer + "/api/attrType/delete/",
                        params: {typeId:data.typeId,parentId:data.parentId}
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
                $rootScope.openErrorDialogModal('<span style="font-weight: bold;color: #6b3100">' + data.name + '</span>' + "属性项下有子项，请先删除所有子项");
            }

        };
        $scope.save = function () {
            if ($("#typeId").val() != null && $("#typeId").val() != '') {
                var promise = $http({
                    method: 'POST',
                    url: $rootScope.site.apiServer + "/api/attrType/edit",
                    data: $("#attrTypeForm").serialize()
                });
                promise.then(function (res, status, config, headers) {
                    $rootScope.loadingState = false;
                    if (res.data.code == 1) {
                        growl.addSuccessMessage("保存成功。。。");
                        $state.go('attrtype.search');
                    } else {
                        growl.addErrorMessage("保存失败。。。");
                    }

                });
            } else {
                //TODO...验证
                var promise = $http({
                    method: 'POST',
                    url: $rootScope.site.apiServer + "/api/attrType/add",
                    data: $("#attrTypeForm").serialize()
                });
                promise.then(function (res, status, config, headers) {
                    $rootScope.loadingState = false;
                    console.log(res.data);
                    if (res.data.code == 1) {
                        growl.addSuccessMessage("保存成功。。。");
                        $state.go('attrtype.search');
                    } else {
                        growl.addErrorMessage("保存失败。。。");
                    }

                });
            }
        };
        $scope.refresh = function () {
            $scope.search();
        };

        if ($state.$current.name == "attrtype.search") {
            $scope.search();
        }



    }
}

export default AttrTypeController;