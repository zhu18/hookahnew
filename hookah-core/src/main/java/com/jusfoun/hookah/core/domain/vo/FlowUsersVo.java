package com.jusfoun.hookah.core.domain.vo;

import java.io.Serializable;

/**
 * Created by admin on 2017/12/5.
 */
public class FlowUsersVo  implements Serializable {
    private Long allNewUser;
    private String dataSource;
    private Long allPersonUser;
    private Long allOrgUser;

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

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
}
