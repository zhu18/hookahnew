package com.jusfoun.hookah.pay.util;

//import com.apex.etm.qss.client.IFixClient;
//import com.apex.etm.qss.client.fixservice.FixConstants;
//import com.apex.fix.JFixSess;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dengxu
 */
public class PayUtil {


    /**
     * 生成请求流水号，当天唯一
     * @return
     */
    public static String createChannelSerial(String channelType){

        StringBuilder sb = new StringBuilder();
        sb.append(channelType);
        sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        sb.append(String.format("%05d", (int)(Math.random()*1000)));
        return sb.toString();
    }

    public static String moneyConver(Long money){
        return String.format("%.2f", (double)money).toString();
    }

//    public static void writeStart(JFixSess sess){
//        //开始应答
//        sess.createAnswer();
//    }
//    public static void write(JFixSess sess, int fid, String value){
//        sess.setItem(fid, value);
//        //sess.write();
//        //sess.createNextAnswer();
//    }
//
//    public static void writeEnd(JFixSess sess){
//        //sess.CreateEndMark();
//        sess.write();
//    }

    /**
     * 文件下载公共方法
     * @param fid_ywrq：业务日期
     * @param fid_filetype：文件类型
     * @param filePath：下载文件存储路径
     * @return Map：
     *             status:下载是否成功的标识，"fail":下载失败，"success":下载成功
     *             message:描述信息
     */

//    public static Map<String,String> downloadFile(String fid_ywrq, String fid_filetype, String filePath){

//        Map<String,String> resultMap=new HashMap<>();
//        String status="";
//        String message="";
//
//        IFixClient  fixClient = new FixClientUtil().createClient();
//
//        JFixSess jFixSess = fixClient.getJFixComm().allocateSess();
//        //客户端请求信息MAC \ IP
//        //jFixSess.setNode("");
//        jFixSess.setWTLY("32");
//        jFixSess.createHead( 719025); //日终文件下载接口
//        jFixSess.setItem(FixConstants.FID_YWRQ, fid_ywrq);
//        jFixSess.setItem(FixConstants.FID_JYS, PayConstants.FID_JYS);//交易市场
//        jFixSess.setItem(FixConstants.FID_FILETYPE, fid_filetype);//文件类型现在 51是出入金对账文件  52 签约对账
//
//        //LOG.info("对账文件保存目录："+filePath);
//        File file = new File(filePath);
//        try {
//            if(jFixSess.more()) {
//                long filesize = jFixSess.getLong(FixConstants.FID_FILESIZE);
//                jFixSess.more();
//                if(jFixSess.getCode() > 0){
//                    byte[] buffer = jFixSess.getItemBuf();
//
//                    if(buffer == null){
//                        buffer = new byte[0];
//                        System.out.println("getItemBuf 为 null，创建一个空文件");
//                        status="fail";
//                        message="下载失败：getItemBuf 为 null，创建一个空文件！";
//                    }else{
//                        if(filesize != buffer.length){
//                            System.out.println("内容长度验证不正确！");
//                            //LOG.error("内容长度验证不正确！");
//                            status="fail";
//                            message="下载失败：内容长度验证不正确！";
//                        }else{
//                            FileOutputStream fs = new FileOutputStream(file);
//                            fs.write(buffer, 0, buffer.length);
//                            fs.close();
//                            System.out.println("下载成功!");
//                            //LOG.info("下载成功");
//                            status="success";
//                            message="下载成功!";
//                        }
//                    }
//                }else{
//                    System.out.println("下载失败："+jFixSess.getErrMsg());
//                    //LOG.error("下载失败：："+jFixSess.getErrMsg());
//                    status="fail";
//                    message="下载失败："+jFixSess.getErrMsg();
//                }
//            }else{
//                System.out.println("下载失败：["+jFixSess.getCode() + "]" +jFixSess.getErrMsg());
//                //LOG.error("失败："+jFixSess.getErrMsg());
//                status="fail";
//                message="下载失败：["+jFixSess.getCode() + "]" +jFixSess.getErrMsg();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            //把具体的错误信息打印到日志
//            StringWriter sw = new StringWriter();
//            e.printStackTrace( new PrintWriter(sw, true));
//            String str = sw.toString();
//            //LOG.error(str);
//            System.out.println("下载失败："+jFixSess.getErrMsg());
//            //LOG.error("下载失败：："+jFixSess.getErrMsg());
//            status="fail";
//            message="下载失败："+jFixSess.getErrMsg();
//        }finally {
//            resultMap.put("status",status);
//            resultMap.put("message",message);
//            return resultMap;
//        }

//    }

    public static void main(String[] args){
        System.out.println(createChannelSerial(ChannelType.QDABC));

        System.out.println(moneyConver(100l));

    }
}
