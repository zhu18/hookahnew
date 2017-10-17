package com.jusfoun.hookah.crowd.controller;

import com.jusfoun.hookah.core.domain.zb.vo.ZbCommentShowVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.crowd.service.ZbCommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class ZbCommentController extends BaseController{

    @Resource
    ZbCommentService zbCommentService;

    @ResponseBody
    @RequestMapping("/api/levelCount")
    public ReturnData getLevelCountByUserId(){

        List<ZbCommentShowVo> list = null;

        try {
            list = zbCommentService.getLevelCountByUserId(getCurrentUser().getUserId());
        } catch (HookahException e) {
            e.printStackTrace();
        }

        return ReturnData.success(list);
    }
}
