package com.jusfoun.hookah.console.server.api.sys;
import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.OperateConstants;
import com.jusfoun.hookah.core.domain.OperateInfo;
import com.jusfoun.hookah.core.domain.vo.OperateVO;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.OperateInfoMongoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenhf on 2017/7/12.
 */
@RestController
@RequestMapping(value = "/api/optlog")
public class OperateInfoApi extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(OperateInfoApi.class);
    @Autowired
    OperateInfoMongoService perateInfoMongoService;

    //获取操作类型
    @RequestMapping(value = "/optType",method = RequestMethod.GET)
    public ReturnData optTypeList(){
        return ReturnData.success(OperateConstants.OPT.getJson());
    }

    //获取前台日志类型
    @RequestMapping(value = "/logType/{platform}")
    public ReturnData logTypeList(@PathVariable(name = "platform") String platform){
        Map<String,String> map = new HashMap<>();
        if (StringUtils.isNotBlank(platform)&&platform.equalsIgnoreCase(OperateConstants.Platform.FRONT.toString()))
            map = OperateConstants.Front_OPT.getJson();
        if (StringUtils.isNotBlank(platform)&&platform.equalsIgnoreCase(OperateConstants.Platform.BACK.toString()))
            map = OperateConstants.Back_OPT.getJson();
            return ReturnData.success(map);
    }

   //获取操作数据
    @RequestMapping(value = "/logs",method = RequestMethod.GET)
    public ReturnData optlist(OperateVO operateVO){
        Pagination<OperateInfo> page = new Pagination<>();
        try {
            page = perateInfoMongoService.getSoldOrderList(operateVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("查询用户日志失败");
        }
        return ReturnData.success(page);
    }

}
