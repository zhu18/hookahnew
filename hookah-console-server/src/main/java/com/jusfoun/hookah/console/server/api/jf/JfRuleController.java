package com.jusfoun.hookah.console.server.api.jf;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.jf.JfRule;
import com.jusfoun.hookah.core.domain.vo.JfUserVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.JfRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 积分规则
 */

@RestController
public class JfRuleController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(JfRuleController.class);

    @Resource
    JfRuleService jfRuleService;

    /**
     * 获取积分设置规则
     * @return
     */
    @RequestMapping("/api/jr/list")
    public ReturnData Test1(String currentPage, String pageSize) {

        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        int pageNumberNew = HookahConstants.PAGE_NUM;
        if (StringUtils.isNotBlank(currentPage)) {
            pageNumberNew = Integer.parseInt(currentPage);
        }

        int pageSizeNew = HookahConstants.PAGE_SIZE;
        if (StringUtils.isNotBlank(pageSize)) {
            pageSizeNew = Integer.parseInt(pageSize);
        }

        try {
            returnData.setData(jfRuleService.getListInPage(pageNumberNew, pageSizeNew, null, null));
        }catch (Exception e) {
            logger.error("获取积分规则异常-{}", e);
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage("系统繁忙，请稍后再试[jr1]!⊙﹏⊙‖∣°");
        }
        return returnData;
    }

}
