package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.annotation.Log;
import com.jusfoun.hookah.core.domain.Organization;
import com.jusfoun.hookah.core.domain.Supplier;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.FormatCheckUtil;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.OrganizationService;
import com.jusfoun.hookah.rpc.api.SupplierService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lt on 2017/7/11.
 */
@RestController
@RequestMapping("/supplier")
public class SupplierController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(SupplierController.class);

    @Resource
    UserService userService;

    @Resource
    OrganizationService organizationService;

    @Resource
    SupplierService supplierService;
    @Log(platform = "front",logType = "f0008",optType = "insert")
    @RequestMapping(value = "/toBeSupplier", method = RequestMethod.POST)
    public ReturnData toBeSupplier(String contactName, String contactPhone, String contactAddress){
        try {
            Session session = SecurityUtils.getSubject().getSession();
            HashMap<String, String> userMap = (HashMap<String, String>) session.getAttribute("user");
            User user = userService.selectById(userMap.get("userId"));
            supplierService.toBeSupplier(contactName,contactPhone,contactAddress,user.getUserId());
        }catch (Exception e){
            return ReturnData.error(e.getMessage());
        }
        return ReturnData.success("申请已提交");
    }

    @RequestMapping(value = "/getBaseInfo", method = RequestMethod.GET)
    public ReturnData getBaseInfo(){
        Map<String, String> map = new HashMap<>(6);
        try {
            String userId = this.getCurrentUser().getUserId();
            User user = userService.selectById(userId);
            Organization org = organizationService.selectById(user.getOrgId());
            if (user!=null && org!=null){
                if (StringUtils.isNotBlank(user.getContactPhone())){
                    map.put("contactPhone",user.getContactPhone());
                }else {
                    map.put("contactPhone",user.getMobile());
                }
                map.put("contactName",user.getContactName());
                if (StringUtils.isNotBlank(user.getContactAddress())){
                    map.put("contactAddress",user.getContactAddress());
                }else {
                    map.put("contactAddress",org.getContactAddress());
                }
                map.put("lawPersonName",org.getLawPersonName());
                map.put("orgName",org.getOrgName());
                map.put("creditCode",org.getCreditCode());
            }
        }catch (Exception e){
            logger.error("查询用户信息错误", e);
            return ReturnData.error("查询用户信息错误");
        }
        return ReturnData.success(map);
    }

    @RequestMapping(value = "/updateContactInfo", method = RequestMethod.POST)
    public ReturnData updateContactInfo(String contactName, String contactPhone, String contactAddress, String postCode, String contactEmail){
        try {
            String userId = this.getCurrentUser().getUserId();
            User user = userService.selectById(userId);
            user.setContactName(contactName.replaceAll(" ",""));
            if (contactPhone!=null && !FormatCheckUtil.checkMobile(contactPhone)){
                return ReturnData.error("联系人手机号格式不正确");
            }else {
                user.setContactPhone(contactPhone);
            }
            if (contactEmail!=null && !FormatCheckUtil.checkEmail(contactEmail)){
                return ReturnData.error("联系人电子邮箱格式不正确");
            }else {
                user.setEmail(contactEmail);
            }
            user.setContactAddress(contactAddress);
            user.setPostCode(postCode);
            userService.updateByIdSelective(user);

            List<Condition> filter = new ArrayList<>();
            filter.add(Condition.eq("userId",userId));
            Supplier supplier = supplierService.selectOne(filter);
            if (supplier!=null){
                supplier.setContactName(contactName);
                supplier.setContactPhone(contactPhone);
                supplierService.updateByIdSelective(supplier);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return ReturnData.error(e.getMessage());
        }
        return ReturnData.success("联系人信息保存成功");
    }

    @RequestMapping(value = "/getContactInfo", method = RequestMethod.GET)
    public ReturnData getContactInfo(){
        Map<String, Object> map = new HashMap<>(4);
        try {
            String userId = this.getCurrentUser().getUserId();
            User user = userService.selectById(userId);
            map.put("contactPhone",user.getContactPhone());
            map.put("contactName",user.getContactName());
            map.put("contactAddress",user.getContactAddress());
            map.put("postCode",user.getPostCode());
            map.put("email",user.getEmail());
        }catch (Exception e){
            logger.error(e.getMessage());
            return ReturnData.error(e.getMessage());
        }
        return ReturnData.success(map);
    }

}
