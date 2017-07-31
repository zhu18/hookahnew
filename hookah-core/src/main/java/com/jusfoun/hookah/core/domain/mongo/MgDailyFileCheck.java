package com.jusfoun.hookah.core.domain.mongo;

import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * 日终文件上传通知检查
 */
@Document
public class MgDailyFileCheck extends GenericModel {

    @Id
    private String id;//主键

    private String FID_WJLX;

    private Date addTime;           // 添加时间

    private Date updateTime;        // 修改时间

    private SendArgs sendArgs;      // 入参

    private AcceptArgs acceptArgs;  // 应答

    public static class SendArgs implements Serializable {

        private String FID_YWRQ;        //业务日期
        private String FID_JYS;         //交易市场
        private String FID_WJLX;       //文件类型

        public String getFID_YWRQ() {
            return FID_YWRQ;
        }

        public void setFID_YWRQ(String FID_YWRQ) {
            this.FID_YWRQ = FID_YWRQ;
        }

        public String getFID_JYS() {
            return FID_JYS;
        }

        public void setFID_JYS(String FID_JYS) {
            this.FID_JYS = FID_JYS;
        }

        public String getFID_WJLX() {
            return FID_WJLX;
        }

        public void setFID_WJLX(String FID_WJLX) {
            this.FID_WJLX = FID_WJLX;
        }
    }

    public static class AcceptArgs implements Serializable {

        private boolean success;            //resultBean成功状态
        private String msg;                 //resultBean信息
        private String code;                //resultBean——code
        private String FID_MESSAGE;         //返回信息
        private String FID_CODE;            //返回码

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getFID_MESSAGE() {
            return FID_MESSAGE;
        }

        public void setFID_MESSAGE(String FID_MESSAGE) {
            this.FID_MESSAGE = FID_MESSAGE;
        }

        public String getFID_CODE() {
            return FID_CODE;
        }

        public void setFID_CODE(String FID_CODE) {
            this.FID_CODE = FID_CODE;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFID_WJLX() {
        return FID_WJLX;
    }

    public void setFID_WJLX(String FID_WJLX) {
        this.FID_WJLX = FID_WJLX;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}