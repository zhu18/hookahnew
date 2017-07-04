package com.jusfoun.hookah.staticserver.controller;
/**
 * All rights Reserved, Designed By ZTE-ITS
 * Copyright:    Copyright(C) 2016-2020
 * Company       JUSFOUN GuoZhiFeng LTD.
 * @author:    郭志峰
 * @version    V1.0
 * Createdate:         2016年7月6日 上午2:23:38
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2016年7月6日       guozhifeng         1.0             1.0
 * Why & What is modified: <修改原因描述>
 */

import com.jusfoun.hookah.core.domain.UploadResult;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.staticserver.util.PropertiesManager;
import com.jusfoun.hookah.staticserver.util.UploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @ClassName: UploadfileController
 * @Description:文件上传公用类
 * @author Timothy guozhifengvip@163.com
 * @date 2016年7月11日 上午4:02:32
 *
 */

@RestController
@RequestMapping("/upload")
public class UploadfileController {
	private static Logger logger = LoggerFactory.getLogger(UploadfileController.class);
    private static String PRE_FILE = PropertiesManager.getInstance().getProperty("upload.other.prefix");
    private static String PRE_IMG = PropertiesManager.getInstance().getProperty("upload.img.prefix");

    @RequestMapping("test")
    public String test() {
        return "success";
    }

	@RequestMapping(value="fileUpload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ReturnData fileUpload(HttpServletRequest request, @RequestParam("filename") MultipartFile[] myfiles) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);
        try {
            returnData.setData(UploadUtil.uploadFile(request, myfiles));
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
	}

    @RequestMapping(value="wangeditor", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public String upload(HttpServletRequest request, @RequestParam("filename") MultipartFile[] myfiles) {
        String url = "";
        try {
            List<UploadResult> results = UploadUtil.uploadFile(request, myfiles);
            url = results.get(0).getAbsPath();
        } catch (HookahException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    @RequestMapping(value="download")
    public void download(HttpServletResponse response, String filePath) {
        try {
            UploadUtil.downLoad(filePath, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @RequestMapping(value="fileUpload/zip", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @ResponseBody
//    public ReturnData fileUploadZip(HttpServletRequest request, @RequestParam("filename") MultipartFile[] myfiles) {
//        ReturnData returnData = new ReturnData();
//        returnData.setCode(ExceptionConst.Success);
//        try {
//            returnData.setData(UploadUtil.uploadFileZip(request, myfiles, "other/"));
//        } catch (Exception e) {
//            returnData.setCode(ExceptionConst.Failed);
//            returnData.setMessage(e.toString());
//            e.printStackTrace();
//        }
//        return returnData;
//    }


    /**
     * 上传文件
     * @param request
     * @param userId
     * @param typeId
     * @param myfiles
     * @return
     */
    @RequestMapping(value="other", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ReturnData fileUpload(HttpServletRequest request, String userId, String typeId, @RequestParam("filename") MultipartFile[] myfiles) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);
        try {
            returnData.setData(UploadUtil.uploadFile(request, PRE_FILE, typeId + "/" + userId, myfiles));
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping(value="img", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ReturnData fileUpload(HttpServletRequest request, String userId, @RequestParam("filename") MultipartFile[] myfiles) {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);
        try {
            returnData.setData(UploadUtil.uploadFile(request, PRE_IMG, userId, myfiles));
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }
}