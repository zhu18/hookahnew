package com.jusfoun.hookah.core.domain;

import java.io.Serializable;

/**
 * Created by wangjl on 2017-3-17.
 */
public class UploadResult implements Serializable {
    private String fileName;
    private String filePath;
    private String absPath;

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

    public String getAbsPath() {
        return absPath;
    }

    public void setAbsPath(String absPath) {
        this.absPath = absPath;
    }

    public UploadResult(String fileName, String filePath, String absPath) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.absPath = absPath;
    }
}
