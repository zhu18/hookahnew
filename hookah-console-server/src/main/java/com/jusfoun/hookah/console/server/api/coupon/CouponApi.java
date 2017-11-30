package com.jusfoun.hookah.console.server.api.coupon;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Coupon;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.vo.CouponVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.*;
import com.jusfoun.hookah.rpc.api.CouponService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lt on 2017/11/8.
 */
@RestController
@RequestMapping("/api/coupon")
public class CouponApi extends BaseController {
    @Resource
    private CouponService couponService;

    /**
     * 添加优惠券
     * @param coupon
     * @param goodsList
     * @param categoriesList
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ReturnData addCoupon(String coupon, String goodsList, String categoriesList){
        try {
            CouponVo couponVo = JsonUtils.toObject(coupon,CouponVo.class);
            String userId = this.getCurrentUser().getUserId();
            if (StringUtils.isNotBlank(couponVo.getExpiryStartTime())){
                Date expiryStartTime = DateUtils.getDate(couponVo.getExpiryStartTime(),DateUtils.DATE_FORMAT);
                couponVo.setExpiryStartDate(expiryStartTime);
            }
            if (StringUtils.isNotBlank(couponVo.getExpiryEndTime())){
                Date expiryEndTime = DateUtils.getDate(couponVo.getExpiryEndTime(),DateUtils.DATE_FORMAT);
                couponVo.setExpiryEndDate(expiryEndTime);
            }
            return couponService.addCoupon(couponVo,goodsList,userId,categoriesList);
        }catch (HookahException e){
            e.printStackTrace();
            return ReturnData.error(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ReturnData.error("优惠券添加失败，请联系技术人员！");
        }
    }

    /**
     * 修改优惠券参数
     * @param coupon
     * @param goodsList
     * @param categoriesList
     * @return
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ReturnData modifyCoupon(String coupon, String goodsList, String categoriesList){
        try {
            CouponVo couponVo = JsonUtils.toObject(coupon,CouponVo.class);
            String userId = this.getCurrentUser().getUserId();
            if (StringUtils.isNotBlank(couponVo.getExpiryStartTime())){
                Date expiryStartTime = DateUtils.getDate(DateUtils.transferTime(couponVo.getExpiryStartTime()),DateUtils.DATE_FORMAT);
                couponVo.setExpiryStartDate(expiryStartTime);
            }
            if (StringUtils.isNotBlank(couponVo.getExpiryEndTime())){
                Date expiryEndTime = DateUtils.getDate(DateUtils.transferTime(couponVo.getExpiryEndTime()),DateUtils.DEFAULT_DATE_TIME_FORMAT);
                couponVo.setExpiryEndDate(expiryEndTime);
            }
            return couponService.modify(couponVo,goodsList,userId,categoriesList);
        }catch (HookahException e){
            e.printStackTrace();
            return ReturnData.error(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ReturnData.error("优惠券编辑失败，请联系技术人员！");
        }
    }

    /**
     * 逻辑删除
     * @param couponId
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ReturnData deleteCoupon(Long couponId){
        try {
            logger.info("逻辑删除优惠券：{}", couponId);
            couponService.logicDelete(couponId);
            return ReturnData.success();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ReturnData.error("优惠券删除失败，请联系技术人员！");
        }
    }

    /**
     * 后台优惠券管理列表
     * @param couponName
     * @param couponType
     * @param currentPage
     * @param pageSize
     * @param sort
     * @param type  0为所有优惠券  1为所有已激活未过期优惠券
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ReturnData getAllCoupon(String couponName, Byte couponType, String currentPage, String pageSize, String sort, Byte type){
        Pagination page = new Pagination<>();
        try {
            page = couponService.getCouponList(couponName,couponType,currentPage,pageSize,sort,type);
            return ReturnData.success(page);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ReturnData.error("");
        }
    }

    /**
     * 优惠券明细
     * @param couponId
     * @return
     */
    @RequestMapping(value = "/getCouponById", method = RequestMethod.GET)
    public ReturnData getCouponById(@RequestParam Long couponId){
        try {
            Coupon coupon = couponService.selectById(couponId);
            CouponVo couponVo = new CouponVo();
            BeanUtils.copyProperties(coupon, couponVo);
            //发行量有限制的
            if (coupon.getTotalCount()>0){
                couponVo.setUnReceivedCount(coupon.getTotalCount() - coupon.getReceivedCount());
            }
            couponVo.setUnUsedCount(coupon.getReceivedCount() - coupon.getUsedCount());
            return ReturnData.success(couponVo);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ReturnData.error("");
        }
    }

    /**
     *用户领取优惠券明细
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getUserCouponById", method = RequestMethod.GET)
    public ReturnData getUserCouponById(@RequestParam(value = "userId") String userId){
        try {
            String[] ids = {userId};
            List list = couponService.getUserCouponById(ids);
            return ReturnData.success(list.get(0));
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ReturnData.error("");
        }
    }

    /**
     * 优惠券 用户 明细列表
     * @param userId
     * @param couponId
     * @param userCouponStatus
     * @param orderSn
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getCouponDetail", method = RequestMethod.GET)
    public ReturnData getCouponReceivedDetail(String userId, Long couponId, Byte userCouponStatus, String orderSn,
                                              String currentPage, String pageSize){
        try {
            Byte couponTag = null;
            Pagination page = couponService.getCouponReceivedDetail(userId,couponId,userCouponStatus,orderSn,currentPage,pageSize,couponTag);
            return ReturnData.success(page);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ReturnData.error("");
        }
    }

    /**
     * 后台 用户优惠券数据列表
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getUserCouponList", method = RequestMethod.GET)
    public ReturnData getUserCouponList(String userName, Integer userType, String userSn, String currentPage, String pageSize){
        try {
            User user = new User();
            user.setUserName(userName);
            user.setUserType(userType);
            user.setUserSn(userSn);
            Pagination page = couponService.getUserCouponList(user,currentPage,pageSize);
            return ReturnData.success(page);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ReturnData.error("");
        }
    }

    @RequestMapping(value = "/getUserCouponDetail", method = RequestMethod.GET)
    public ReturnData getUserCouponDetail(String userId, Long userCouponId){
        try {
            return couponService.getUserCouponDetail(userId,userCouponId);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ReturnData.error("");
        }
    }

    /**
     * 后台赠送优惠券给用户
     * @param coupon
     * @return
     */
    @RequestMapping(value = "/sendCouponToUser", method = RequestMethod.POST)
    public ReturnData sendCoupon2User(String coupon){
        try {
            Map<String,Object> map = JsonUtils.toObject(coupon,Map.class);
            String userId = (String) map.get("userId");
            List<Integer> couponIds = (List<Integer>) map.get("couponIds");
            List<Long> couponList = new ArrayList<>();
            for (Integer couponId:couponIds){
                couponList.add((long) couponId);
            }
            return couponService.sendCoupon2User(userId, couponList, HookahConstants.ReceivedMode.BACKSTAGE_GIFT.getCode());
        }catch (HookahException e){
            return ReturnData.error(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ReturnData.error("优惠券发送失败");
        }
    }

    @RequestMapping("/exportExcel")
    public void export(HttpServletResponse response, HttpServletRequest request, String[] userIds) {
        try {
            if (userIds.length==0){
                userIds = null;
            }
            List userCouponVos = couponService.getUserCouponById(userIds);
            //json字符串
            String jsonStr = JsonUtils.toJson(userCouponVos);
            //表格列标题
            String sheaders = "userId,userSn,userName,used,unused,expired,";
            String[] head = {"用户ID","用户账号","用户昵称","已使用","未使用","已过期"};
            //表格名称
            String title = "Sheet1";
            //表格文件名
            String fileName = "用户领取优惠券列表"+".csv";
            // 将json字符串转换为json对象
            JSONArray jsonArray = new JSONArray(jsonStr);
            String[] headers= sheaders.substring(0,sheaders.length()-1).split(",");
            System.out.println(sheaders.substring(0,sheaders.length()-1));
            int iSize = jsonArray.length();
            List<List> list = new ArrayList<List>();
            for (int i = 0; i < iSize; i++) {
                List<Object> line = new ArrayList<Object>();
                JSONObject jsonObject =  jsonArray.getJSONObject(i);
                System.out.println(jsonObject.toString()+"-----");
                Object value = null;
                int j=0;
                while (j<headers.length) {
                    value = jsonObject.get(headers[j]);
                    String t = value.toString();
                    if (t.equals("null")){
                        value=0;
                    }
                    //表格内容
                    line.add(value);
                    j++;
                    System.out.println(value);
                }
                list.add(line);
            }

            HSSFWorkbook workbook = ExcelUtil.exportExcel(title,head, list);
            ExcelUtil.download(response,workbook,fileName);
            logger.info("excel导出成功！");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
