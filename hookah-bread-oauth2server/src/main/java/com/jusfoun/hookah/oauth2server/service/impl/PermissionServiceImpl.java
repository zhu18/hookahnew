package com.jusfoun.hookah.oauth2server.service.impl;

import com.jusfoun.hookah.core.dao.PermissionMapper;
import com.jusfoun.hookah.core.domain.Permission;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author huang lei
 * @date 2017/4/5 上午9:35
 * @desc
 */
@Service
public class PermissionServiceImpl extends GenericServiceImpl<Permission, String> implements PermissionService {

    private List<Permission> nodes;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    public void setDao(PermissionMapper permissionMapper){
        super.setDao(permissionMapper);
    }


    @Override
    public Set<String> selectPermissionsByUserId(String userId) {
        return permissionMapper.selectPermissionsByUserId(userId);
    }

    @Override
    public Set<String> selectPermissionsByRoleId(String roleId) {
        return permissionMapper.selectPermissionsByRoleId(roleId);
    }

    @Override
    public Set<String> selectPermissionsByRoleName(String roleName) {
        return permissionMapper.selectPermissionsByRoleName(roleName);
    }


    @Override
    public List<Permission> selectTree() {
        nodes = super.selectList();
        List<Permission> resultNodes = new ArrayList<>();
        buildTree(resultNodes);
        return resultNodes;
    }

    private List<Permission> buildTree(List<Permission> resultNodes) {

        for (Permission node : nodes) {
            if (node.getPermissionParentId().equals("0")) {//通I过循环一级节点 就可以通过递归获取二级以下节点
                resultNodes.add(node);//添加一级节点
                build(node);//递归获取二级、三级、。。。节点
            }
        }
        return resultNodes;
    }

    private void build(Permission node) {
        List<Permission> children = getChildren(node);
        if (!children.isEmpty()) {//如果存在子节点
            node.setChildren(children);
            for (Permission child : children) {//将子节点遍历加入返回值中
//                child.getChildren().add(child);
                build(child);
            }
        }
    }

    private List<Permission> getChildren(Permission node) {
        List<Permission> children = new ArrayList<Permission>();
        String id = node.getPermissionId();
        for (Permission child : nodes) {
            if (id.equals(child.getPermissionParentId())) {//如果id等于父id
                children.add(child);//将该节点加入循环列表中
            }
        }
        return children;
    }
}
