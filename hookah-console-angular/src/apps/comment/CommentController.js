class CommentController {
  constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {

    $scope.search = function () {
   /*   var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/help/all"
      });
      promise.then(function (res, status, config, headers) {
        $rootScope.loadingState = false;
        $scope.tree_data = res.data;
        growl.addSuccessMessage("数据加载完毕。。。");
      });*/
    };


    $scope.refresh = function () {
      $scope.search();
    };

    if ($state.current.name == "comment.review") {
      $scope.search();
      $scope.expanding_property = "name";
      $scope.col_defs = [
        {
          field: "helpId",
          displayName: "属性"
        }
        , {
          field: "helpUrl",
          displayName: "连接",
          cellTemplate: "<a target='_blank' href='" + $rootScope.site.websiteServer + "{{ row.branch[col.field] }}'>{{ row.branch[col.field] }}</a>",
        },
        {
          field: "creatorName",
          displayName: "创建者"
        },
        {
          field: "addTime",
          displayName: "创建时间"
        }

        , {
          field: "aa",
          displayName: "操作",
          cellTemplate: ' <a href="javascript:;" ng-click="cellTemplateScope.delete(row.branch)" target="_blank">删除</a> <span class="text-explode">|</span> <i class="link-space"></i> <a href="javascript:;" ng-click="cellTemplateScope.addChild(row.branch)">增加子项</a>',
          cellTemplateScope: {
            edit: function (data) {
              $scope.edit(data);
            },
            delete: function (data) {
              if (data.children.length == 0) {
                var modalInstance = $rootScope.openConfirmDialogModal("确定要删除" + '<span style="font-weight: bold;color: #6b3100">' + data.name + '</span>' + "吗？");
                modalInstance.result.then(function () {
                  $scope.delete(data);
                }, function () {
                });
              } else {
                $rootScope.openErrorDialogModal('<span style="font-weight: bold;color: #6b3100">' + data.name + '</span>' + "下有子项，请先删除所有子项");
              }

            },
            addChild: function (data) {
              $scope.addChild(data);
            }
          }
        }
      ];
      $scope.dict_tree = {};
      $scope.tree_data = {};
    }

  }
}

export default CommentController;