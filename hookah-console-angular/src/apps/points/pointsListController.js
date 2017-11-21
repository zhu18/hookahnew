class pointsListController {
  constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {
    var flag = '';//是否点击了全选，是为a
    $scope.x = false;//默认未选中

    $scope.commentList = [];
    $scope.choseArr = [];//多选数组

    $scope.search = function (initCurrentPage) {

      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/jf/uList",
        params: {
          userName: $scope.userName ? $scope.userName : '',//用户名
          userType: $scope.userType ? $scope.userType : '',//用户类型
          mobile: $scope.mobile ? $scope.mobile : '',//手机号
          currentPage: initCurrentPage == 'true' ? 1 :$rootScope.pagination.currentPage, //
          pageSize: $rootScope.pagination.pageSize
        }
      });

      promise.then(function (res, status, config, headers) {
        console.log('数据在这里');
        console.log(res);

        if (res.data.code == '1') {
          $scope.commentList = res.data.data.list;
          // $rootScope.pagination = res.data.data;
          $scope.showNoneDataInfoTip = false;
          if (res.data.data.totalPage > 1) {
            $scope.showPageHelpInfo = true;
          }
        } else {
          $scope.commentList = [];
          $scope.showNoneDataInfoTip = true;

        }
        $scope.choseArr = [];//重置多选数组
        $scope.master = false;//重置全选

        $rootScope.loadingState = false;
        growl.addSuccessMessage("订单数据加载完毕。。。");
      });

    };
    $scope.pageChanged = function () {
      $scope.search();
      console.log('Page changed to: ' + $rootScope.pagination.currentPage);
    };
        $scope.MultipleCheck = function (status) {
      if ($scope.choseArr.length > 0) {
        $scope.commentCheck($scope.choseArr.join(), status,0);
        console.log($scope.choseArr.join())
      } else {
        alert('请选择多个用户！');
      }
    };
     $scope.commentCheck = function (ids,status,currentPoints ) {
      var content='<div style="padding:0 30px;">';
      var singleDom='<div style="font-size:16px;">\
            当前积分：<span id="currentPoints">'+currentPoints+'</span>&nbsp;&nbsp;\
            变动后积分：<span id="lastPoints"></span>&nbsp;&nbsp;\
            本次变动分值：<span id="currentChangePoints" style="color:red"></span>\
          </div>';
      var multipleDom='<table style="width: 100%;">\
            <tr>\
              <th valign="top" style="width: 100px;padding-top: 10px; "><span style="color:red">*</span>调整积分：</th>\
              <td style="padding-top: 10px;">\
                  <label><input type="radio" name="pointsCon" value="11" checked> 增加</label> &nbsp;&nbsp;&nbsp;&nbsp;\
                  <label><input type="radio" name="pointsCon" value="12"> 减少</label><br>\
                  <input type="number" id="currentChangePointsInput" style="width: 200px;" placeholder="请输入积分"></td>\
            </tr>\
            <tr>\
              <th valign="top"  style="padding-top: 10px;">操作备注信息：</th><td style="padding-top: 10px;"><textarea id="note" maxlength="200" cols="30" rows="10"></textarea></td>\
            </tr>\
          </table>';
      if(status==1){
        content +=multipleDom ;
      }else{
        content +=(singleDom+multipleDom)
      }
      content+='</div> ';
      var title1 = '修改积分';
      var modalInstance = $rootScope.openConfirmDialogModel(title1,content);

     modalInstance.result.then(function () { //模态点提交
        console.log('点击确定');

         var promise = $http({
           method: 'get',
           url: $rootScope.site.apiServer +"/api/jf/optJf",
           params:{
             userId:ids,
             optType:$('input[name=pointsCon]:checked').val(),
             score:$('#currentChangePointsInput').val(),
             note:$('#currentChangePointsInput').val()
           }
         });
         promise.then(function (res, status, config, headers) {
           console.log('数据在这里');
           console.log(res);
           if (res.data.code == '1') {
            $scope.search();
           } else {
           }

           $rootScope.loadingState = false;
           growl.addSuccessMessage("订单数据加载完毕。。。");
         });

      },function(){
        console.log('点击取消')

      });
      $(document).on('change','#currentChangePointsInput',function () {//表单值改变
        chagnePointsFn()
      });
      $(document).on('keyup','#currentChangePointsInput',function () {//按下键盘
        chagnePointsFn()
      });
      $(document).on('click','input[name=pointsCon]',function () {//点击增加或者减少
        chagnePointsFn()
      });

      $(document).on('blur','#note',function () {//操作备注信息不能为空
        if($.trim($("#note").val()).length==0){
          alert('备注信息不能为空！')
        }
      });

      function chagnePointsFn() {
        console.log($('input[name=pointsCon]:checked').val());
        var tempVal= null;
        var tempValType= null;

        if($('input[name=pointsCon]:checked').val() == 11){
          tempVal=Number(currentPoints)+ Number($('#currentChangePointsInput').val());
          tempValType='+';
        }else if($('input[name=pointsCon]:checked').val() == 12){
          tempVal=currentPoints- $('#currentChangePointsInput').val();
          tempValType='-'
        }
        $('#currentChangePoints').html(tempValType+$('#currentChangePointsInput').val());
        $('#lastPoints').html(tempVal);
      }
    };

    //多选
    $scope.all = function (c) { //全选
      var commIdArr = [];

      angular.forEach($scope.commentList, function (value, key) {
        commIdArr.push(value.userId)
      });
      console.log(commIdArr);

      if (c == true) {
        $scope.x = true;
        $scope.choseArr = commIdArr;
        flag = 'a';
      } else {
        $scope.x = false;
        $scope.choseArr = [];
        flag = 'b';
      }
    };

    $scope.chk = function (z, x) { //单选或者多选
      if (x == true) { //选中
        $scope.choseArr.push(z);
        flag = 'c';
        if ($scope.choseArr.length == $scope.commentList.length) {
          $scope.master = true
        }
      } else {
        $scope.choseArr.splice($scope.choseArr.indexOf(z), 1);//取消选中
      }
      console.log($scope.choseArr);
    };
    //多选结束



    $scope.refresh = function () {
      $scope.search();
    };
    $scope.search('true');


  }
}

export default pointsListController;