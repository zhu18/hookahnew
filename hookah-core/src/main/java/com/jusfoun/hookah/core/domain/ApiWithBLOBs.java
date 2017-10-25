package com.jusfoun.hookah.core.domain;

public class ApiWithBLOBs extends Api {
    private String desc;

    private String requestSample;

    private String responseSample;

    private String sampleData;

    private String docDesc;

    private String urlDataSample;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public String getRequestSample() {
        return requestSample;
    }

    public void setRequestSample(String requestSample) {
        this.requestSample = requestSample == null ? null : requestSample.trim();
    }

    public String getResponseSample() {
        return responseSample;
    }

    public void setResponseSample(String responseSample) {
        this.responseSample = responseSample == null ? null : responseSample.trim();
    }

    public String getSampleData() {
        return sampleData;
    }

    public void setSampleData(String sampleData) {
        this.sampleData = sampleData == null ? null : sampleData.trim();
    }

    public String getDocDesc() {
        return docDesc;
    }

    public void setDocDesc(String docDesc) {
        this.docDesc = docDesc == null ? null : docDesc.trim();
    }

    public String getUrlDataSample() {
        return urlDataSample;
    }

    public void setUrlDataSample(String urlDataSample) {
        this.urlDataSample = urlDataSample == null ? null : urlDataSample.trim();
    }
}