1、前台奖励邀请页面接口
URL：http://www.pybdex.com/userRecommend/recommendInfo
请求方式：post/get
返回页面：/usercenter/userInfo/rewardRecommend
返回参数：
{
  recommendUrl：我的专属推荐链接，
  inviteeInfo：[
    inviteeNum：推荐人数，
    rewardMoney：推荐获得奖金
  ]
}

2、后台邀请好友查询接口
URL：http://console.pybdex.com/api/recommend/findRecommendList
请求方式：post/get
请求参数：
{
  userName：用户ID/账号,
  currentPage:当前页码，
  pageSize:每页显示多少条
}
返回参数：
{
    "data": {
        "list": [
            {
              "userId": "1",  //用户ID
              "userName": "admin",   //用户账号
              "mobile": "1234567890",  //手机号
              "userType": 0, //认证状态，2：个人，4：企业，其它：未认证
              "inviteeNum": 5,   // 邀请好友个数
              "rewardMoney": 20,   // 累计奖励
              "isdealNum": 1  //成功交易好友个数
            }
        ],
        "totalItems": 2,    //总记录数
        "totalPage": 2,    //总页数
        "pageSize": 1,    //每页显示多少条
        "currentPage": 1  //当前页码
    },
    "data2": null,
    "code": "1",    //状态码,1:成功，9：错误，0：失败
    "message": "调用成功"
}

3、后台邀请好友列表查询接口
（1）、推荐用户信息接口
URL：http://console.pybdex.com/api/recommend/findOneRecommend?recommenderId=1
请求方式：post/get
请求参数：
{
  recommenderId：推荐用户的用户ID/账号,
}
返回参数：
{
    "data": {
        "userId": "1",  //用户ID
        "userName": "admin",	//用户账号
        "mobile": "1234567890",	//手机号
        "userType": 0,	//认证状态，2：个人，4：企业，其它：未认证
        "inviteeNum": 4,	// 邀请好友个数
        "rewardMoney": 15,	 // 累计奖励
        "isdealNum": 1,		//成功交易好友个数
        "regTime": null,
        "isdeal": null,
        "isauthenticate": null
    },
    "data2": null,
    "code": "1",	//状态码,1:成功，9：错误，0：失败
    "message": "调用成功"
}
（2）邀请好友列表信息接口
URL：http://console.pybdex.com//api/recommend/findRecommendDetails?recommenderId=1&currentPage=2&pageSize=1
请求方式：post/get
请求参数：
{
  recommenderId：推荐用户的用户ID/账号,
  order:排序字段（regTime：注册时间，userId：用户ID，userName：用户名,默认为：regTime）
  sort:倒序或者正序（asc:正序，desc:倒序，默认desc）
  currentPage:当前页码，
  pageSize:每页显示多少条
}
返回参数：
{
    "data": {
        "list": [
            {
              "userId": "1",  //用户ID
              "userName": "admin",   //用户账号
              "mobile": "1234567890",  //手机号
              "userType": 0, //认证状态，2：个人，4：企业，其它：未认证
              "inviteeNum": 5,   // 邀请好友个数
              "rewardMoney": 20,   // 累计奖励
              "isdealNum": 1  //成功交易好友个数
			  "regTime": 1504708261000,	//注册时间
			  "isdeal": 0,			// '是否成功交易，0：否，1：是，
			  "isauthenticate": 0 	//'是否认证，0：否，1：是，
            }
        ],
        "totalItems": 2,    //总记录数
        "totalPage": 2,    //总页数
        "pageSize": 1,    //每页显示多少条
        "currentPage": 1  //当前页码
    },
    "data2": null,
    "code": "1",    //状态码,1:成功，9：错误，0：失败
    "message": "调用成功"
}