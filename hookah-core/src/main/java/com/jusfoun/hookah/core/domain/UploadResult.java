package com.jusfoun.hookah.core.domain;

import java.io.Serializable;

/**
 * Created by wangjl on 2017-3-17.
 */
public class UploadResult implements Serializable {
    private String fileName;
    private String filePath;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public UploadResult(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }
}
