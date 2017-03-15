///**
// * All rights Reserved, Designed By ZTE-ITS
// * Copyright:    Copyright(C) 2016-2020
// * Company       JUSFOUN GuoZhiFeng LTD.
// * @author:    郭志峰
// * @version    V1.0
// * Createdate:         2016年7月6日 上午2:23:38
// * Modification  History:
// * Date         Author        Version        Discription
// * -----------------------------------------------------------------------------------
// * 2016年7月6日       guozhifeng         1.0             1.0
// * Why & What is modified: <修改原因描述>
// */
//package com.jusfoun.hookah.console.server.upload;
//
//import com.jusfoun.hookah.console.server.util.SSHHelper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.UUID;
//
//
//@RestController
//@RequestMapping({ "upload" })
///**
// *
// * @ClassName: UploadfileController
// * @Description:文件上传公用类
// * @author Timothy guozhifengvip@163.com
// * @date 2016年7月11日 上午4:02:32
// *
// */
//public class UploadfileController {
//	private static Logger logger = LoggerFactory.getLogger(UploadfileController.class);
//	@Value("${picture.adminurl}")
//	private String adminurl;
//
//	@RequestMapping({ "fileUpload" })
//	@ResponseBody
//	public HashMap<String, String> fileUpload(@RequestParam MultipartFile[] myfiles, HttpServletResponse response)throws IOException {
//		DateFormat df = new SimpleDateFormat("yyyyMMdd");
//		String dirs = df.format(new Date());
//		response.setContentType("json; charset=UTF-8");
//		HashMap result = new HashMap();
//		String originalFilename = null;
//
//		String laterFilename = null;
//
//		for (MultipartFile myfile : myfiles) {
//			if (myfile.isEmpty()) {
//				result.put("res_code", "000099");
//				result.put("res_msg", "文件不能为空");
//			} else {
//				originalFilename = myfile.getOriginalFilename();
//				UUID uuid = UUID.randomUUID();
//				String orginstr="";
//				String uustr = uuid.toString();
//				if(originalFilename.substring(originalFilename.length() - 4, originalFilename.length()).toUpperCase().equals("JPEG")){
//					 orginstr = originalFilename.substring(originalFilename.length() - 5, originalFilename.length());
//
//				}else{
//					 orginstr = originalFilename.substring(originalFilename.length() -4, originalFilename.length());
//				}
//				laterFilename = uustr + orginstr;
//				HashMap qryTokenMap = new HashMap();
//				try {
//					String path1 = "/upload/image/"+dirs+ "/" + laterFilename;
//					SSHHelper sh = new SSHHelper();
//	                sh.file_upload(myfile.getInputStream(),path1 );
//					result.put("res_code", "000000");
//					result.put("res_msg", "文件上传成功");
//					result.put("path",adminurl+ path1);
//					return result;
//
//				} catch (IOException e) {
//					logger.info("文件[" + originalFilename + "]上传失败,堆栈轨迹如下");
//					e.printStackTrace();
//					result.put("res_code", "000097");
//					result.put("res_msg", "文件上传失败");
//				}
//			}
//
//		}
//
//		return result;
//	}
//}