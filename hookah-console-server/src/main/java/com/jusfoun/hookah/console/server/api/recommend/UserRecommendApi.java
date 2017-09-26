package com.jusfoun.hookah.console.server.api.recommend;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.vo.WXUserRecommendVo;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.WXUserRecommendService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * Created by ndf on 2017/9/25.
 */

@Controller
@RequestMapping("/api/recommend")
public class UserRecommendApi extends BaseController {

    @Resource
    private WXUserRecommendService wxUserRecommendService;


    /**
     * 奖励推荐，后台邀请好友查询接口
     * @param userName
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping("/findRecommendList")
    @ResponseBody
    public ReturnData findRecommendList(String userName,String currentPage, String pageSize){
        ReturnData returnData=new ReturnData();
        try{
            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }

            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            HashMap<String,Object> paramMap=new HashMap<>();
            paramMap.put("userName",userName);
            paramMap.put("isdeal",1);
            paramMap.put("pageNum",pageNumberNew);
            paramMap.put("pageSize",pageSizeNew);
            paramMap.put("startIndex",(pageNumberNew-1)*pageSizeNew);
            Pagination<WXUserRecommendVo> page=wxUserRecommendService.findRecommendListByCondition(paramMap);
            returnData.setData(page);
            returnData.setCode(ExceptionConst.Success);
        }catch (Exception e){
            e.printStackTrace();
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage("系统错误："+e.getMessage());
        }
        return  returnData;
    }
}
