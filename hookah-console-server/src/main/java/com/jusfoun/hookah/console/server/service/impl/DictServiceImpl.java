package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.DictMapper;
import com.jusfoun.hookah.core.domain.Dict;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.DictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huang lei
 * @date 2017/4/13 下午2:10
 * @desc
 */
@Service
public class DictServiceImpl extends GenericServiceImpl<Dict, Long> implements DictService {

    @Resource
    private DictMapper dictMapper;

    @Resource
    public void setDao(DictMapper dictMapper) {
        super.setDao(dictMapper);
    }

    private List<Dict> resultNodes = new ArrayList<Dict>();
    private List<Dict> nodes;

    public List<Dict> selectTree(){
        nodes = super.selectList();
        List<Dict> dictTreeList = buildTree();
        return dictTreeList;
    }

    /**
     * 构建树形结构list
     * @return 返回树形结构List列表
     */
    private List<Dict> buildTree() {

        for (Dict node : nodes) {
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
    private void build(Dict node) {
        List<Dict> children = getChildren(node);
        if (!children.isEmpty()) {//如果存在子节点
            for (Dict child : children) {//将子节点遍历加入返回值中
                resultNodes.add(child);
                build(child);
            }
        }
    }
    /**
     * @param node
     * @return 返回
     */
    private List<Dict> getChildren(Dict node) {
        List<Dict> children = new ArrayList<Dict>();
        Long id = node.getDictId();
        for (Dict child : nodes) {
            if (id.equals(child.getParentId())) {//如果id等于父id
                children.add(child);//将该节点加入循环列表中
            }
        }
        return children;
    }
}
