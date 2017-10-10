package com.jusfoun.hookah.core.domain.zb.vo;

import com.jusfoun.hookah.core.domain.zb.mongo.MgZbProvider;

public class MgZbProviderVo extends MgZbProvider {

    private String optArrAySn;      //  认证信息编号
    private String optAuthType;     //  认证类别-1教育2工作3项目4应用案例5软件著作权6发明专利
    private Integer optType;        //  插入1删除2修改3

    public String getOptArrAySn() {
        return optArrAySn;
    }

    public void setOptArrAySn(String optArrAySn) {
        this.optArrAySn = optArrAySn;
    }

    public String getOptAuthType() {
        return optAuthType;
    }

    public void setOptAuthType(String optAuthType) {
        this.optAuthType = optAuthType;
    }

    public Integer getOptType() {
        return optType;
    }

    public void setOptType(Integer optType) {
        this.optType = optType;
    }
}
