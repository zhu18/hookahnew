package com.jusfoun.hookah.core.domain.mongo;

import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * dx
 * 出入金参数
 */
@Document
public class MgMoneyInOutLog extends GenericModel {

    @Id
    private Long PayAccountRecordId;//主键

    private String userId;

    private byte transferType;   // 操作类型 1入金 2出金

    private Date addTime;           // 添加时间

    private Date updateTime;        // 修改时间

    private SendArgs sendArgs;      // 入参
    
    private AcceptArgs acceptArgs;  // 应答

    public static class SendArgs implements Serializable {

         private String FID_JYS;        //交易市场private String 
         private String FID_BZ;         //币种private String 
         private String FID_ZZJE;       //转账金额private String 
         private String FID_SQRQ;       //申请日期
         private String FID_SQSJ;       //申请时间
         private String FID_SQH; 	    //申请号private String 
         private String FID_YHZH;       //银行账户private String 
         private String FID_YHDM;       //银行代码private String 
         private String FID_YHMM;       //银行密码
         private String FID_CZZD;       //操作站点
         private String FID_BZXX;       //备注信息
         private String FID_ZJZH;       //资金账户private String 
         private String FID_WDH;  	    //网点号
         private String FID_CS1; 	    //预留参数1 如果需要签名信息，对应签名信息
         private String FID_CS2; 	    //预留参数2 签名流水号，如果是农行，此处必填，主要用来验证加签信息
         private String FID_CS3; 	    //预留参数3 账单号，如果是农行，此处必填，主要用来验证加签信息
         private String FID_CS4; 	    //预留参数4

        public String getFID_JYS() {
            return FID_JYS;
        }

        public void setFID_JYS(String FID_JYS) {
            this.FID_JYS = FID_JYS;
        }

        public String getFID_BZ() {
            return FID_BZ;
        }

        public void setFID_BZ(String FID_BZ) {
            this.FID_BZ = FID_BZ;
        }

        public String getFID_ZZJE() {
            return FID_ZZJE;
        }

        public void setFID_ZZJE(String FID_ZZJE) {
            this.FID_ZZJE = FID_ZZJE;
        }

        public String getFID_SQRQ() {
            return FID_SQRQ;
        }

        public void setFID_SQRQ(String FID_SQRQ) {
            this.FID_SQRQ = FID_SQRQ;
        }

        public String getFID_SQSJ() {
            return FID_SQSJ;
        }

        public void setFID_SQSJ(String FID_SQSJ) {
            this.FID_SQSJ = FID_SQSJ;
        }

        public String getFID_SQH() {
            return FID_SQH;
        }

        public void setFID_SQH(String FID_SQH) {
            this.FID_SQH = FID_SQH;
        }

        public String getFID_YHZH() {
            return FID_YHZH;
        }

        public void setFID_YHZH(String FID_YHZH) {
            this.FID_YHZH = FID_YHZH;
        }

        public String getFID_YHDM() {
            return FID_YHDM;
        }

        public void setFID_YHDM(String FID_YHDM) {
            this.FID_YHDM = FID_YHDM;
        }

        public String getFID_YHMM() {
            return FID_YHMM;
        }

        public void setFID_YHMM(String FID_YHMM) {
            this.FID_YHMM = FID_YHMM;
        }

        public String getFID_CZZD() {
            return FID_CZZD;
        }

        public void setFID_CZZD(String FID_CZZD) {
            this.FID_CZZD = FID_CZZD;
        }

        public String getFID_BZXX() {
            return FID_BZXX;
        }

        public void setFID_BZXX(String FID_BZXX) {
            this.FID_BZXX = FID_BZXX;
        }

        public String getFID_ZJZH() {
            return FID_ZJZH;
        }

        public void setFID_ZJZH(String FID_ZJZH) {
            this.FID_ZJZH = FID_ZJZH;
        }

        public String getFID_WDH() {
            return FID_WDH;
        }

        public void setFID_WDH(String FID_WDH) {
            this.FID_WDH = FID_WDH;
        }

        public String getFID_CS1() {
            return FID_CS1;
        }

        public void setFID_CS1(String FID_CS1) {
            this.FID_CS1 = FID_CS1;
        }

        public String getFID_CS2() {
            return FID_CS2;
        }

        public void setFID_CS2(String FID_CS2) {
            this.FID_CS2 = FID_CS2;
        }

        public String getFID_CS3() {
            return FID_CS3;
        }

        public void setFID_CS3(String FID_CS3) {
            this.FID_CS3 = FID_CS3;
        }

        public String getFID_CS4() {
            return FID_CS4;
        }

        public void setFID_CS4(String FID_CS4) {
            this.FID_CS4 = FID_CS4;
        }
    }

    public static class AcceptArgs implements Serializable {

         private String FID_CODE;	    //返回码
         private String FID_MESSAGE;	//返回信息
         private String FID_SQBH;	    //申请编号
         private String FID_CLJG;	    //处理结果  -111失败  111成功  0 申请中(存疑)
         private String FID_JGSM;	    //结果说明

        public String getFID_CODE() {
            return FID_CODE;
        }

        public void setFID_CODE(String FID_CODE) {
            this.FID_CODE = FID_CODE;
        }

        public String getFID_MESSAGE() {
            return FID_MESSAGE;
        }

        public void setFID_MESSAGE(String FID_MESSAGE) {
            this.FID_MESSAGE = FID_MESSAGE;
        }

        public String getFID_SQBH() {
            return FID_SQBH;
        }

        public void setFID_SQBH(String FID_SQBH) {
            this.FID_SQBH = FID_SQBH;
        }

        public String getFID_CLJG() {
            return FID_CLJG;
        }

        public void setFID_CLJG(String FID_CLJG) {
            this.FID_CLJG = FID_CLJG;
        }

        public String getFID_JGSM() {
            return FID_JGSM;
        }

        public void setFID_JGSM(String FID_JGSM) {
            this.FID_JGSM = FID_JGSM;
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public SendArgs getSendArgs() {
        return sendArgs;
    }

    public void setSendArgs(SendArgs sendArgs) {
        this.sendArgs = sendArgs;
    }

    public AcceptArgs getAcceptArgs() {
        return acceptArgs;
    }

    public void setAcceptArgs(AcceptArgs acceptArgs) {
        this.acceptArgs = acceptArgs;
    }

    public byte getTransferType() {
        return transferType;
    }

    public void setTransferType(byte transferType) {
        this.transferType = transferType;
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

    public Long getPayAccountRecordId() {
        return PayAccountRecordId;
    }

    public void setPayAccountRecordId(Long payAccountRecordId) {
        PayAccountRecordId = payAccountRecordId;
    }
}
