package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.User;

import java.util.List;

/**
 * @author huang lei
 * @date 2017/7/18 上午11:19
 * @desc
 */
public class UserRoleVo extends User {

    private List roles;

    public List getRoles() {
        return roles;
    }

    public void setRoles(List roles) {
        this.roles = roles;
    }
}
