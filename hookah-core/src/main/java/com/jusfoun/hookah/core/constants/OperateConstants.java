package com.jusfoun.hookah.core.constants;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenhf on 2017/7/12.
 */
public class OperateConstants {
    //关键数据定义
    public static String[] key_content = {"userName","userId","mobile","orgName"};

    public enum Platform{
        FRONT,BACK
    }
    //操作枚举
    public enum OPT{
        insert("insert","新增"),
        modify("modify","修改"),
        browse("browse","浏览"),
        delete("delete","删除");
        private String code;
        private String name;
        private OPT(String code,String name){
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static String getNameByCode(String code){
            String result = code;
            for (OPT opt:OPT.values()){
                if (code.equalsIgnoreCase(opt.getCode())){
                    result = opt.getName();
                }
            }
            return result;
        }

        public static Map<String, String> getJson(){
            Map<String,String> map = new HashMap<>();
            for (OPT opt:OPT.values()){
                map.put(opt.code,opt.getName());
            }
            return map;
        }
    }
    //前台操作事件
    public enum Front_OPT {
        REG("f0001", "用户注册"),
        LOGIN("f0002", "用户登录"),
        LOGIN_PWD_FIND("f0003", "登录密码找回"),
        MODIFY_PHONE("f0004", "修改关联手机号"),
        MODIFY_LOGIN_PWD("f0005", "修改登录密码"),
        MODIFY_PAY_PWD("f0006", "设置交易密码"),
        COM_AUTH_APPLY("f0007", "单位认证申请"),
        TOBE_SUPPLIER_APPLY("f0008", "成为供应商申请"),
        GOODS_UP("f0009", "商品上架"),
        GOODS_DOWN("f0010", "商品下架"),
        MODIFY_GOODS_INFO("f0011", "商品信息修改"),
        ORDER_CREATE("f0012", "订单生成"),
        ORDER_REFUND("f0013", "订单退款"),
        RECHARGE("f0014", "用户充值"),
        WITHDRAW_CASH("f0015", "用户提现"),
        ORDER_PAY("f0016", "订单支付");

        private String code;
        private String name;

        private Front_OPT(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static String getNameByCode(String code){
            String result = code;
            for (Front_OPT opt:Front_OPT.values()){
                if (code.equalsIgnoreCase(opt.getCode())){
                    result = opt.getName();
                }
            }
            return result;
        }
        public static Map<String, String> getJson(){
            Map<String,String> map = new HashMap<>();
            for (Front_OPT opt:Front_OPT.values()){
                map.put(opt.code,opt.getName());
            }
            return map;
        }
    }

    /**
     * 后台日志类型
     */
    public enum Back_OPT{

        USER_LOGIN("b001","用户登录"),
        USER_EXAMINE("b002","用户审核"),
        SUPPLIER_EXAMINE("b003","供应商审核"),
        GOODSUP_EXAMINE("b004","商品上架审核"),
        GOODSOff_EXAMINE("b005","商品下架审核"),
        GOOD_MODIFY("b006","商品信息修改"),
        ORDER_DELIVER("b007","订单交付"),
        EVALUATE_EXAMINE("b008","评价审核"),
        WITHDRAWALS("b009","提现记录"),
        SETTLEMENT("b010","结算记录"),
        ADD_MESSAGE("b011","消息模板增加"),
        ADD_USER("b012","新增用户"),
        MODIFY_USER("b013","修改用户"),
        ADD_ROLE("b014","新增角色"),
        MODIFY_ROLE("b015","修改角色");

        private String code;
        private String name;

        private Back_OPT(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static String getNameByCode(String code){
            String result = code;
            for (Back_OPT opt:Back_OPT.values()){
                if (code.equalsIgnoreCase(opt.getCode())){
                    result = opt.getName();
                }
            }
            return result;
        }

        public static Map<String, String> getJson(){
            Map<String,String> map = new HashMap<>();
            for (Back_OPT opt:Back_OPT.values()){
                map.put(opt.code,opt.getName());
            }
            return map;
        }
    }



}
