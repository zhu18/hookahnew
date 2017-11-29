package com.jusfoun.hookah.console.server.api.invoice;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.annotation.Log;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.mongo.MgOrderGoods;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.JsonUtils;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.InvoiceService;
import com.jusfoun.hookah.rpc.api.OrderInfoService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by dengxu on 2017/4/24/0024.
 */

@RestController
@RequestMapping(value = "/api/invoice")
public class InvoiceApi extends BaseController{

    @Resource
    InvoiceService invoiceService;
    @Resource
    UserService userService;

}
