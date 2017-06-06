class ShelfController {
  constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {

    $scope.search = function () {
      console.log("货架查询。。。。");
        var promise = $http({
            method: 'GET',
            url: $rootScope.site.apiServer + "/api/shelf/all",
            params: {currentPage: $rootScope.pagination.currentPage,
                        pageSize: $rootScope.pagination.pageSize,
                        shelfName: $scope.shelfName
            }
        });
        promise.then(function (res, status, config, headers) {
            $rootScope.loadingState = false;
            growl.addSuccessMessage("数据加载完毕。。。");
        });
    };

    $scope.add = function () {
        var promise = $http({
            method: 'POST',
            url: $rootScope.site.apiServer + "/api/shelf/add",
            data: $("#shelfForm").serialize()
        });
        promise.then(function (res, status, config, headers) {
            console.log(res.data)
            if(res.data.code == "1"){
                growl.addSuccessMessage("数据添加完毕。。。");
                $state.go('shelf.search');
            }
        });
    };

    $scope.update = function () {
        var promise = $http({
            method: 'POST',
            url: $rootScope.site.apiServer + "/api/shelf/update",
            data: $("#shelfForm").serialize()
        });
        promise.then(function (res, status, config, headers) {
            console.log(res.data)
            if(res.data.code == "1"){
                growl.addSuccessMessage("数据修改完毕。。。");
                $state.go('shelf.search');
            }
        });
    };

    $scope.pageChanged = function () {
          $scope.search();
          console.log('Page changed to: ' + $rootScope.pagination.currentPage);
    };

    $scope.search();

    $scope.updateStatus = function (item, flag) {
          console.log(item.shelvesId, item.shelvesStatus);
          var promise = $http({
              method: 'POST',
              url: $rootScope.site.apiServer + "/api/shelf/updateStatus",
              params: {shelvesId: item.shelvesId, shelvesStatus: flag}
          });
          promise.then(function (res, status, config, headers) {
              if(res.data.code == 1){
                  $scope.search();
              }
          });
    };

    $scope.manageGoods = function (event, item) {
        console.log(item);
        $state.go('shelf.manageGoods', {data: item});
        console.log("即将进货架管理……");
    };

    $scope.updateShelf = function (event, item) {
        console.log(item);
        $rootScope.editData = item;
        $state.go('shelf.update', {data: item});
    };

    $scope.backImgClick = function (object) {
       alert($('input[name="isBackImg"]:checked').val());
        if($('input[name="isBackImg"]:checked').val() == 0){
            $('#imgUploadDiv').attr('display','none');
            $('#imgPathDiv').attr('display','none');
        }else {
            $('#imgUploadDiv').remove('display');
            $('#imgPathDiv').remove('display');
        }
    }

   if ($state.current.name == "shelf.add") {
       var editor = new wangEditor('content');
       //上传图片（举例）
       editor.config.uploadImgUrl = $rootScope.url.uploadUrl;
       editor.config.uploadImgFileName = 'filename';
       //关闭菜单栏fixed
       editor.config.menuFixed = false;
       editor.config.menus = $.map(wangEditor.config.menus, function (item, key) {
          if (item !== 'img') {
              return null;
          }
          console.log("----out image path ---:" + item);
          return item;
       });
       editor.config.uploadImgFns.onload = function (resultText, xhr) {
           console.log("----out image path ---:" + resultText);
           console.log("----out image path ---:" + JSON.parse(resultText).data[0].absPath);
           // 上传图片时，已经将图片的名字存在 editor.uploadImgOriginalName
           var originalName = editor.uploadImgOriginalName || '';

           var filePath = JSON.parse(resultText).data[0].filePath;

           console.log("----out image path ---:" + filePath);

           $scope.backImg = filePath;
           $scope.$apply();

           // 如果 resultText 是图片的url地址，可以这样插入图片：
           editor.command(null, 'insertHtml', '<img src="' + JSON.parse(resultText).data[0].absPath + '" alt="' + originalName + '" style="max-width:100%;"/>');
           // console.log("----out image path ---:" + angular.toJson(resultText).data[0].absPath);
       }
       editor.create();
    }

      if ($state.current.name == "shelf.update") {
          var editor = new wangEditor('content');
          //上传图片（举例）
          editor.config.uploadImgUrl = $rootScope.url.uploadUrl;
          editor.config.uploadImgFileName = 'filename';
          //关闭菜单栏fixed
          editor.config.menuFixed = false;
          editor.config.menus = $.map(wangEditor.config.menus, function (item, key) {
              if (item !== 'img') {
                  return null;
              }
              console.log("----out image path ---:" + item);
              return item;
          });
          editor.config.uploadImgFns.onload = function (resultText, xhr) {
              console.log("----out image path ---:" + resultText);
              console.log("----out image path ---:" + JSON.parse(resultText).data[0].absPath);
              // 上传图片时，已经将图片的名字存在 editor.uploadImgOriginalName
              var originalName = editor.uploadImgOriginalName || '';
              var filePath = JSON.parse(resultText).data[0].filePath;
              console.log("----out image path ---:" + filePath);

              $scope.editData.backImgPath = filePath;
              $scope.$apply();

              // 如果 resultText 是图片的url地址，可以这样插入图片：
              editor.command(null, 'insertHtml', '<img src="' + JSON.parse(resultText).data[0].absPath + '" alt="' + originalName + '" style="max-width:100%;"/>');
          }
          editor.create();

          if($state.current.name == "shelf.update"){
              var html = '<p>';
              html += '<img src="' + $rootScope.site.staticServer + '/' +$rootScope.editData.backImgPath + '" style="max-width:100%;">';
              html += '</p>';
              $(".wangEditor-txt").append(html);
          }
      }

  }
}

export default ShelfController;