package com.jusfoun.hookah.console.server.api.jf;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.jf.JfRule;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.JfRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

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

    /**
     * 根据类型修改积分规则
     * @param id
     * @param type
     * @param score
     * @param upperLimitScore
     * @param upperLimitTime
     * @param lowerLimitScore
     * @param lowerLimitTime
     * @return
     */
    @RequestMapping("/api/jr/update")
    public ReturnData Test2(String id, String type, String score,
                             String upperLimitScore, String upperLimitTime,
                             String lowerLimitScore, String lowerLimitTime,
                             String note) {

        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);

        try {

            if(!StringUtils.isNotBlank(id)){
                return ReturnData.error("参数不能为空!⊙﹏⊙‖∣°");
            }

            if(!StringUtils.isNotBlank(type)
                    && !StringUtils.isNotBlank(score)
                    && !StringUtils.isNotBlank(upperLimitScore)
                    && !StringUtils.isNotBlank(upperLimitTime)
                    && !StringUtils.isNotBlank(lowerLimitScore)
                    && !StringUtils.isNotBlank(lowerLimitTime)
                    && !StringUtils.isNotBlank(note)
                    ){
                return ReturnData.error("无有效参数!⊙﹏⊙‖∣°");
            }

            JfRule jfRule = jfRuleService.selectById(Integer.parseInt(id));

            if(jfRule == null){
                return ReturnData.error("未查询到该条积分规则!⊙﹏⊙‖∣°");
            }

            if(!StringUtils.isNotBlank(type)
                    && !StringUtils.isNotBlank(score)
                    && score != null
                    && Integer.parseInt(score) > 0){

                if(type.equals("11")){
                    jfRule.setScore(jfRule.getScore() + Integer.parseInt(score));
                } else if(type.equals("12")){
                    jfRule.setScore(jfRule.getScore() - Integer.parseInt(score));
                } else {
                    return ReturnData.error("请求参数有误[type]!⊙﹏⊙‖∣°");
                }
            }

            jfRule.setOperator(this.getCurrentUser().getUserId());
            if(StringUtils.isNotBlank(upperLimitScore)){
                jfRule.setUpperLimit(Integer.parseInt(upperLimitScore));
            }
            if(StringUtils.isNotBlank(lowerLimitScore)){
                jfRule.setLowerLimit(Integer.parseInt(lowerLimitScore));
            }
            if(StringUtils.isNotBlank(upperLimitTime)){
                jfRule.setUpperTimeLimit(Byte.parseByte(upperLimitTime));
            }
            if(StringUtils.isNotBlank(lowerLimitTime)){
                jfRule.setLowerTimeLimit(Byte.parseByte(lowerLimitTime));
            }
            if(StringUtils.isNotBlank(note)){
                jfRule.setNote(note);
            }
            jfRule.setUpdateTime(new Date());

            int n = jfRuleService.updateByIdSelective(jfRule);
            if(n == 1){
                returnData.setMessage("积分规则修改成功！^_^");
            } else {
                returnData.setMessage("积分规则修改失败！>_<|||");
            }
        }catch (HookahException ex) {
            logger.error("用户信息获取异常-{}", ex);
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage(ex.getMessage());
        }catch (Exception e) {
            logger.error("积分规则修改异常-{}", e);
            returnData.setCode(ExceptionConst.Error);
            returnData.setMessage("系统繁忙，请稍后再试[jr1]!⊙﹏⊙‖∣°");
        }
        return returnData;
    }

}
