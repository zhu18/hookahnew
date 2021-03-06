package com.jusfoun.hookah.webiste.util;

import com.jusfoun.hookah.core.domain.UploadResult;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.GeneratorUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by wangjl on 2017-3-17.
 */
public class UploadUtil {
    public static List<UploadResult> uploadFile(HttpServletRequest request, MultipartFile[] files) throws HookahException, IOException {
        if (files.length <= 0)
            throw new HookahException("未找到上传文件！");

        List<UploadResult> list = new ArrayList<>();

        for (MultipartFile file : files) {
            String pathReal = "";
            //文件后缀
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            //取得当前上传文件的文件名称
            String fileName = DateUtils.toDateText(new Date(), "HHmmss") + GeneratorUtil.getUUID() + "." + suffix;
            String tmpPath = "";
            //如果名称不为“”,说明该文件存在，否则说明该文件不存在
            if (fileName.trim() != "") {
                //TODO 这里文件路径有问题，需要更正判断文件夹是否存在，如果不存在新建文件夹
//                pathReal = Class.class.getClass().getResource("/").getPath().replace("/target/classes", "/resources")
                tmpPath = PropertiesManager.getInstance().getProperty("upload.filepath")
                        + DateUtils.toDateText(new Date(), "yyyyMMdd") + "/";
                pathReal = Class.class.getClass().getResource("/").getPath() + tmpPath;
                File pathFile = new File(pathReal);
                if (!pathFile.exists() && !pathFile.isDirectory()) {
                    pathFile.mkdirs();
                }
                File localFile = new File(pathReal + fileName);
                file.transferTo(localFile);
            }
            String filePath = tmpPath + fileName;
            list.add(new UploadResult(fileName, filePath,
                    PropertiesManager.getInstance().getProperty("upload.url") + filePath));
        }
        return list;
    }
}
