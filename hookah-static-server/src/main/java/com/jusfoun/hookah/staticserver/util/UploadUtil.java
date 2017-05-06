package com.jusfoun.hookah.staticserver.util;

import com.jusfoun.hookah.core.domain.UploadResult;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.GeneratorUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangjl on 2017-3-17.
 */
public class UploadUtil {
    private static String FILE_PATH = PropertiesManager.getInstance().getProperty("upload.filepath");
    private static String url = PropertiesManager.getInstance().getProperty("upload.url");

    public static List<UploadResult> uploadFile(HttpServletRequest request, MultipartFile[] files) throws HookahException, IOException {
        if (files.length <= 0)
            throw new HookahException("未找到上传文件！");

        List<UploadResult> list = new ArrayList<>();

        for (MultipartFile file : files) {
            //文件后缀
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            //取得当前上传文件的文件名称
            String fileName = DateUtils.toDateText(new Date(), "HHmmss") + GeneratorUtil.getUUID() + "." + suffix;
            String tmpPath = "";
            String datePath = DateUtils.toDateText(new Date(), "yyyyMMdd");
            //如果名称不为“”,说明该文件存在，否则说明该文件不存在
            if (fileName.trim() != "") {
                tmpPath = FILE_PATH + datePath + "/";
                File pathFile = new File(tmpPath);
                if (!pathFile.exists() && !pathFile.isDirectory()) {
                    pathFile.mkdirs();
                }
                File localFile = new File(tmpPath + fileName);
                file.transferTo(localFile);
            }
            String filePath1 = datePath + "/" + fileName;
            list.add(new UploadResult(fileName, filePath1, url + filePath1));
        }
        return list;
    }

    public static void downLoad(String filePath, HttpServletResponse response)
            throws Exception {
        filePath = FILE_PATH + filePath;
        File f = new File(filePath);
        if (!f.exists()) {
            return;
        }
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
        byte[] buf = new byte[1024];
        int len = 0;

        response.reset(); // 非常重要
        URL u = new URL("file:///" + filePath + filePath);
        response.setContentType(u.openConnection().getContentType());
        response.setHeader("Content-Disposition", "attachment; filename="
                + URLEncoder.encode(f.getName(), "UTF-8"));
        // 文件名应该编码成UTF-8
        OutputStream out = response.getOutputStream();
        while ((len = br.read(buf)) > 0)
            out.write(buf, 0, len);
        br.close();
        out.close();
    }

    public static List<UploadResult> uploadFileZip(HttpServletRequest request, MultipartFile[] files, String midPath) throws IOException {
        List<UploadResult> list = new ArrayList<>();
        for (MultipartFile file : files) {
            //文件后缀
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            //取得当前上传文件的文件名称
            String fileName = DateUtils.toDateText(new Date(), "HHmmss") + GeneratorUtil.getUUID() + "." + suffix;
            String tmpPath = "";
            String datePath = DateUtils.toDateText(new Date(), "yyyyMMdd");
            //如果名称不为“”,说明该文件存在，否则说明该文件不存在
            if (fileName.trim() != "") {
                tmpPath = FILE_PATH + midPath + datePath + "/";
                File pathFile = new File(tmpPath);
                if (!pathFile.exists() && !pathFile.isDirectory()) {
                    pathFile.mkdirs();
                }
                //by sjs ，没有FileIo
                //FileIo.zipOneFile(tmpPath + File.separator + fileName, file.getOriginalFilename(), file.getInputStream());
            }
            String filePath1 = midPath + datePath + "/" + File.separator + fileName;
            list.add(new UploadResult(fileName, filePath1, url + filePath1));
        }
        return list;
    }
}
