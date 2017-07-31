package com.jusfoun.hookah.pay.service.impl;

/**
 * Created by ndf on 2017/7/5.
 */


import com.apex.etm.qss.client.IFixClient;
import com.apex.etm.qss.client.fixservice.FixConstants;
import com.apex.fix.AxSvcFunc;
import com.apex.fix.JFixComm;
import com.apex.fix.JFixSess;
import com.jusfoun.hookah.core.constants.PayConstants;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.pay.util.FixClientUtil;
import com.jusfoun.hookah.pay.util.PayUtil;
import com.jusfoun.hookah.rpc.api.GenerateNotificationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class GenerateNotificationsImpl implements GenerateNotificationsService{

    @Resource
    private FixClientUtil client;

    private IFixClient fixClient=client.createClient();

    protected final static Logger logger = LoggerFactory.getLogger(GenerateNotificationsImpl.class);

    /**
     * 清算中心推送文件生成通知 JYS.{FID_JYS}.201
     */
    public ReturnData addFileGenerateOnNotice(){
        ReturnData returnData = new ReturnData();

        String JYS = PayConstants.FID_JYS;
        fixClient.addFileGenerateOnNotice(JYS, "", new AxSvcFunc() {
            public boolean onSvc(JFixSess sess, JFixComm jFixComm) {

                String FID_CLJG = "";
                String FID_JGSM = "";
                System.err.println("------------------进来了---------------------");
                try {

                    /**
                     * 参考接口文档： 清算中心推送异步处理结果（JYS.市场代码.106）
                     */
                    String FID_YWRQ = sess.getItem(FixConstants.FID_YWRQ);
                    String FID_FILETYPE = sess.getItem(FixConstants.FID_FILETYPE);
                    String FID_JYS = sess.getItem(FixConstants.FID_JYS);

                    //Map<String, String> resultMap = PayUtil.downloadFile(FID_YWRQ, FID_FILETYPE, PayConstants.FILE_PATH);
                    Map<String, String> resultMap = new HashMap<>();
                    String status = resultMap.get("status");
                    System.out.println("++++++++++++++++++++++++++++++++++++状态："+status+"------------消息："+resultMap.get("message"));
                    if (status.equals("success")) {

                        //处理成功应答
                        FID_CLJG = "111";
                        FID_JGSM = "成功";
                        PayUtil.writeStart(sess);
                        PayUtil.write(sess, FixConstants.FID_CODE, FID_CLJG);
                        PayUtil.write(sess, FixConstants.FID_MESSAGE, FID_JGSM);
                        PayUtil.writeEnd(sess);
                        returnData.setCode(ExceptionConst.Success);
                        returnData.setMessage("生成通知成功！");
                        return true;
                    }
                    logger.error("数据转换异常");
                    returnData.setCode(ExceptionConst.Failed);
                    returnData.setMessage("文件下载异常！");
                }catch (Exception e){
                    e.printStackTrace();
                    returnData.setCode(ExceptionConst.Failed);
                    returnData.setMessage(e.getMessage());
                }
                return false;
            }
        });
        return returnData;
    }

}
