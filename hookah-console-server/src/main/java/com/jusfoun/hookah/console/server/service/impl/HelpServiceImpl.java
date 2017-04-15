package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.HelpMapper;
import com.jusfoun.hookah.core.domain.Help;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.HelpService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huang lei
 * @date 2017/4/10 下午4:30
 * @desc
 */
@Service
public class HelpServiceImpl extends GenericServiceImpl<Help, String> implements HelpService {

    @Resource
    HelpMapper helpMapper;

    @Resource
    public void setDao(HelpMapper helpMapper) {
        super.setDao(helpMapper);
    }

    private List<Help> nodes;

    public List<Help> selectTree() {
        nodes = super.selectList();
        List<Help> resultNodes = new ArrayList<Help>();
        buildTree(resultNodes);
        return resultNodes;
    }

    /**
     * 构建树形结构list
     *
     * @return 返回树形结构List列表
     */
    private List<Help> buildTree(List<Help> resultNodes) {

        for (Help node : nodes) {
            if (node.getParentId() == null) {//通I过循环一级节点 就可以通过递归获取二级以下节点
                resultNodes.add(node);//添加一级节点
                build(node);//递归获取二级、三级、。。。节点
            }
        }
        return resultNodes;
    }

    /**
     * 递归循环子节点
     *
     * @param node 当前节点
     */
    private void build(Help node) {
        List<Help> children = getChildren(node);
        if (!children.isEmpty()) {//如果存在子节点
            node.setChildren(children);
            for (Help child : children) {//将子节点遍历加入返回值中
//                child.getChildren().add(child);
                build(child);
            }
        }
    }

    /**
     * @param node
     * @return 返回
     */
    private List<Help> getChildren(Help node) {
        List<Help> children = new ArrayList<Help>();
        String id = node.getHelpId();
        for (Help child : nodes) {
            if (id.equals(child.getParentId())) {//如果id等于父id
                children.add(child);//将该节点加入循环列表中
            }
        }
        return children;
    }

}
