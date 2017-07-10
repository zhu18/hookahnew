package com.jusfoun.hookah.core.domain.vo;

/**
 * @author huang lei
 * @date 2017/7/7 下午5:31
 * @desc
 */
public class RolePermissionVo {

    private String roleId;
    private String roleName;
    private String roleExplain;
    private String permissions;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleExplain() {
        return roleExplain;
    }

    public void setRoleExplain(String roleExplain) {
        this.roleExplain = roleExplain;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
