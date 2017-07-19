package com.jusfoun.hookah.pay.service.impl;

/**
 * Created by ndf on 2017/7/5.
 */

//import com.apex.etm.qss.client.IFixClient;
//import com.apex.etm.qss.client.fixservice.FixConstants;
//import com.apex.fix.AxSvcFunc;
//import com.apex.fix.JFixComm;
//import com.apex.fix.JFixSess;
import com.jusfoun.hookah.pay.util.FixClientUtil;
import com.jusfoun.hookah.pay.util.PayConstants;
import com.jusfoun.hookah.pay.util.PayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class GenerateNotificationsImpl {

    @Resource
    private FixClientUtil client;

//    private IFixClient fixClient=client.createClient();
//
//    protected final static Logger logger = LoggerFactory.getLogger(GenerateNotificationsImpl.class);
//
//    /**
//     * 清算中心推送文件生成通知 JYS.{FID_JYS}.201
//     */
//    public void addFileGenerateOnNotice(){
//
//        String JYS = PayConstants.FID_JYS;
//        this.fixClient.addFileGenerateOnNotice(JYS, "", new AxSvcFunc() {
//            public boolean onSvc(JFixSess sess, JFixComm jFixComm) {
//
//                String FID_CLJG = "";
//                String FID_JGSM = "";
//
//                /**
//                 * 参考接口文档： 清算中心推送异步处理结果（JYS.市场代码.106）
//                 */
//                String FID_YWRQ = sess.getItem(FixConstants.FID_YWRQ);
//                String FID_FILETYPE = sess.getItem(FixConstants.FID_FILETYPE);
//                String FID_JYS = sess.getItem(FixConstants.FID_JYS);
//
//                Map<String, String> resultMap = PayUtil.downloadFile(FID_YWRQ, FID_FILETYPE, PayConstants.FILE_PATH);
//                String status=resultMap.get("status");
//                if (status.equals("success")){
//
//                    //处理成功应答
//                    FID_CLJG = "111";
//                    FID_JGSM = "成功";
//                    PayUtil.writeStart(sess);
//                    PayUtil.write(sess, FixConstants.FID_CODE, FID_CLJG);
//                    PayUtil.write(sess, FixConstants.FID_MESSAGE, FID_JGSM);
//                    PayUtil.writeEnd(sess);
//                    return true;
//                }
//                logger.error("数据转换异常");
//                return false;
//            }
//        });
//    }

}
