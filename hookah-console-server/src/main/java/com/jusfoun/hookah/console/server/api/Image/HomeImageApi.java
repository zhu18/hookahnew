package com.jusfoun.hookah.console.server.api.Image;

import com.alibaba.fastjson.JSON;
import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.domain.vo.HomeImageVo;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.HomeImageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/4/7 上午10:50
 * @desc
 */
@RestController
@RequestMapping(value = "/api/image")
public class HomeImageApi extends BaseController{

    @Resource
    HomeImageService homeImageService;

    @RequestMapping("/add")
    @ResponseBody
    public ReturnData addHomeImageBack(String homeImageVo) {
        HomeImageVo obj = JSON.parseObject(homeImageVo, HomeImageVo.class);
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            homeImageService.addImage(obj, getCurrentUser());
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping("/del")
    @ResponseBody
    public ReturnData delHomeImageBack(String imgId, Byte imgType) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
//            HomeImage homeImage = homeImageService.selectById(imgId);
            // 根据该imgId，大于该ID下的imgSort值逐一减1更新数据表的imgSort的值，重新排序
            homeImageService.updateSortValByImgId(imgId, imgType);
            // 删除改imgId
            homeImageService.delete(imgId);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public ReturnData editHomeImageBack(String homeImageVo) {

        HomeImageVo obj = JSON.parseObject(homeImageVo, HomeImageVo.class);

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            homeImageService.updateByIdSelective(obj);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }

    /**
     * 根据imgId查询图片详情
     *
     * @param imgId
     * @return
     */
    @RequestMapping("/findById")
    @ResponseBody
    public ReturnData findHomeImageById(String imgId) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            returnData.setData(homeImageService.selectById(imgId));
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping(value = "/sort", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData sort(String imgId, String flg) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            homeImageService.sort(imgId, flg);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

}
