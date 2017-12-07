package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.FlowUser;

import java.io.Serializable;

/**
 * Created by admin on 2017/12/4.
 */
public class FlowUserVo extends FlowUser implements Serializable {

    private Long allNewUser;

    private Long allPersonUser;

    private Long allOrgUser;

    private String startTime;

    private String endTime;

    private String dataSource;

    @Override
    public String getDataSource() {
        return dataSource;
    }

    @Override
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public Long getAllNewUser() {
        return allNewUser;
    }

    public void setAllNewUser(Long allNewUser) {
        this.allNewUser = allNewUser;
    }

    public Long getAllPersonUser() {
        return allPersonUser;
    }

    public void setAllPersonUser(Long allPersonUser) {
        this.allPersonUser = allPersonUser;
    }

    public Long getAllOrgUser() {
        return allOrgUser;
    }

    public void setAllOrgUser(Long allOrgUser) {
        this.allOrgUser = allOrgUser;
    }


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
