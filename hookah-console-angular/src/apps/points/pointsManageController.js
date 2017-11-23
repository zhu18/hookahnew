class pointsManageController {
  constructor($scope, $rootScope, $http, $state, $uibModal, usSpinnerService, growl) {
    var currentPointData=null;
    $rootScope.closeModelPara=false;

    $scope.settleList = [];
    $scope.choseArr = [];//多选数组

    $scope.search = function (initCurrentPage) {
      // console.log($scope.levelStar);

      var promise = $http({
        method: 'GET',
        url: $rootScope.site.apiServer + "/api/jr/list",
      });
      promise.then(function (res, status, config, headers) {
        console.log('数据在这里');
        console.log(res);
        if (res.data.code == '1') {
          $scope.pointsList = res.data.data.list;
        } else {

        }
        $rootScope.loadingState = false;
        growl.addSuccessMessage("订单数据加载完毕。。。");
      });
    };
    function editRuleRequire(callback) {
      var promise = $http({
        method: 'get',
        url: $rootScope.site.apiServer + "/api/jr/update",
        params: {
          id: $('#currentChangePointsInput').attr('currentId'),
          type: $('input[name=pointsCon]:checked').val(),
          score: $('#currentChangePointsInput').val(),
          upperLimitScore: $('#upper').val(),
          upperLimitTime: $('#upperTimeSelect').val(),
          lowerLimitScore: $('#lower').val(),
          lowerLimitTime: $('#lowerTimeSelect').val()
        }
      });
      promise.then(function (res, status, config, headers) {
        console.log('数据在这里');
        console.log(res);
        if (res.data.code == '1') {
          callback();//关闭模态框
          $scope.search();
        } else {
        }

        $rootScope.loadingState = false;
        growl.addSuccessMessage("订单数据加载完毕。。。");
      });
    }
    $scope.editRule = function (data) {
      currentPointData=data;
      var actionDOM=(currentPointData.action == 1)?'获取':((currentPointData.action == 2)?'兑换':'admin');
      var scoreType=(currentPointData.action == 1)?'+':((currentPointData.action == 3)?(currentPointData.sourceId == 1)?'+':'':'');
      var upperTimeLimitText=(currentPointData.upperTimeLimit == null)?'永久':(currentPointData.upperTimeLimit+'h');
      var lowerTimeLimitText=(currentPointData.lowerTimeLimit == null)?'永久':(currentPointData.lowerTimeLimit+'h');
      var content = '<div style="padding:0 30px;">\
      <table class="table table-hover">\
        <thead>\
          <tr>\
            <th>积分变化类型</th>\
            <th>变化来源</th>\
            <th>当前规则</th>\
            <th>变动后规则</th>\
            <th>变动积分值</th>\
            <th>规则规定上线</th>\
            <th>规则规定下线</th>\
          </tr>\
        </thead>\
        <tbody>\
          <tr>\
            <td>'+actionDOM+'</td><!--积分变化类型-->\
            <td>'+currentPointData.actionDesc+'</td><!--变化来源-->\
            <td>'+scoreType+currentPointData.score+'</td><!--当前规则-->\
            <td style="color:red"><!--变动后规则-->\
              <span id="lastScore">'+scoreType+currentPointData.score+'</span>\
            </td>\
            <td style="color:red"><!--变动积分值-->\
              <span id="changeValue">0</span>\
            </td>\
            <td style="color:red">\
              <span id="upperLimit">'+currentPointData.upperLimit+'</span> /\
              <span id="upperTimeLimit">'+upperTimeLimitText+'</span>\
            </td>\
            <td style="color:red">\
              <span id="lowerLimit">'+currentPointData.lowerLimit+'</span> /\
              <span id="lowerTimeLimit">'+lowerTimeLimitText+'</span>\
            </td>\
          </tr>\
        </tbody>\
      </table>\
     <table style="width: 100%;" class="editTable">\
            <tr>\
              <th valign="top" style="width: 100px;padding-top: 10px; "><span style="color:red">*</span>积分值变动：</th>\
              <td style="padding-top: 10px;">\
                  <label style="margin-right: 20px;"><input type="number" class="form-control" currentId="'+currentPointData.id+'" id="currentChangePointsInput" style="width: 182px;" placeholder="请输入积分"></label>\
                  <label><input type="radio" name="pointsCon" value="11" checked> 增加</label> &nbsp;&nbsp;&nbsp;&nbsp;\
                  <label><input type="radio" name="pointsCon" value="12"> 减少</label>\
              </td>\
            </tr>\
            <tr>\
              <th>规则规定上限：</th>\
              <td style="padding-top: 10px;"> \
                <label style="margin-right: 15px"><input type="number" id="upper" class="form-control" placeholder="请输入规则规定上限"></label>\
                上线循环时效：\
                <select name="upperTimeSelect" id="upperTimeSelect">\
                  <option value="null" selected>永久</option>\
                  <option value="12">12h</option>\
                  <option value="24">24h</option>\
                </select>\
              </td>\
            </tr>\
            <tr>\
              <th>规则规定下限：</th>\
              <td style="padding-top: 10px;">\
                <label style="margin-right: 15px"><input type="number" id="lower" class="form-control" placeholder="请输入规则规定下限"></label>\
                下限循环时效：\
                <select name="lowerTimeSelect" id="lowerTimeSelect">\
                  <option value="null" selected>永久</option>\
                  <option value="12">12h</option>\
                  <option value="24">24h</option>\
                </select>\
              </td>\
            </tr>\
            <tr>\
              <th valign="top"  style="padding-top: 10px;">备注信息：</th><td style="padding-top: 10px;"><textarea  style="width:100%;" class="form-control" id="note" maxlength="200" cols="30" rows="10"></textarea></td>\
            </tr>\
          </table></div> ';
      var title1 = '修改积分';

      // var modalInstance = $rootScope.openConfirmDialogModelCanVerify(title1, content,editRuleRequire);
      var modalInstance = $rootScope.openConfirmDialogModelCanVerify(title1, content,editRuleRequire);
      function testFn(callback) {
        console.log('在testFn里')

      }
      modalInstance.result.then(function () { //模态点提交
        console.log('点击确定');
      },function(){
        console.log('点击取消')

      });
      $(document).on('change', '#currentChangePointsInput,#upper,select', function () {//表单值改变
        chagnePointsFn()
      });
      $(document).on('keyup', 'input', function () {//按下键盘
        chagnePointsFn()
      });
      $(document).on('click', 'input[type=number],input[name=pointsCon]', function () {//点击增加或者减少
        chagnePointsFn()
      });

      $(document).on('blur', '#note', function () {//操作备注信息不能为空
        if ($.trim($("#note").val()).length == 0) {
          // alert('备注信息不能为空！')
        }
      });

      function chagnePointsFn() {

        console.log($('input[name=pointsCon]:checked').val());
        var tempVal = null;
        var tempValType = null;

        if ($('input[name=pointsCon]:checked').val() == 11) {//选择增加
          tempVal = Number(currentPointData.score) + Number($('#currentChangePointsInput').val());
          tempValType = '+';
        } else if ($('input[name=pointsCon]:checked').val() == 12) {//选择减少
          tempVal = currentPointData.score - $('#currentChangePointsInput').val();
          tempValType = '-'
        }
        if($.trim($('#currentChangePointsInput').val()).length>0){ //输入积分需要改变的值才修改最上面的table里的值
          $('#changeValue').html(tempValType + $('#currentChangePointsInput').val());
          $('#lastScore').html(tempValType +tempVal);
        }
        if($.trim($('#upper').val()).length>0){ //输入规则规定上限需要改变的值才修改最上面的table里的值
          $('#upperLimit').html($('#upper').val());
          $('#upperTimeLimit').html(($('#upperTimeSelect').val()=='null')?'永久':(($('#upperTimeSelect').val()=='12')?"12h":"24h"))
        }
        if($.trim($('#lower').val()).length>0){//输入规则规定下限需要改变的值才修改最上面的table里的值
          $('#lowerLimit').html($('#lower').val());
          $('#lowerTimeLimit').html(($('#lowerTimeSelect').val()=='null')?'永久':(($('#lowerTimeSelect').val()=='12')?"12h":"24h"))

        }


      }
    };

    $scope.refresh = function () {
      $scope.search();
    };
    $scope.search('true');

  }
}

export default pointsManageController;