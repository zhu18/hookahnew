package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.IndustryCodeMapper;
import com.jusfoun.hookah.core.domain.IndustryCode;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.IndustryCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/4/7/0007.
 */
@Service
public class IndustryCodeServiceImpl extends GenericServiceImpl<IndustryCode, Long> implements IndustryCodeService {

    @Resource
    IndustryCodeMapper industryCodeMapper;

    @Resource
    public void setDao(IndustryCodeMapper industryCodeMapper) {
        super.setDao(industryCodeMapper);
    }

    private List<IndustryCode> nodes;

    @Override
    public List<IndustryCode> getIndustryCodeList(String parentCode) {
        IndustryCode industryCode = new IndustryCode();
        industryCode.setParentIndustryCode(parentCode);
        return industryCodeMapper.select(industryCode);
    }

    @Override
    public List<IndustryCode> selectTree() {
        nodes = super.selectList();
        List<IndustryCode> resultNodes = new ArrayList<>();
        buildTree(resultNodes);
        return resultNodes;
    }

    private List<IndustryCode> buildTree(List<IndustryCode> resultNodes) {

        for (IndustryCode node : nodes) {
            if ("0".equals(node.getParentIndustryId())) {//通I过循环一级节点 就可以通过递归获取二级以下节点
                resultNodes.add(node);//添加一级节点
                build(node);//递归获取二级、三级、。。。节点
            }
        }
        return resultNodes;
    }

    private void build(IndustryCode node) {
        List<IndustryCode> children = getChildren(node);
        if (!children.isEmpty()) {//如果存在子节点
            node.setChildren(children);
            for (IndustryCode child : children) {//将子节点遍历加入返回值中
//                child.getChildren().add(child);
                build(child);
            }
        }
    }

    private List<IndustryCode> getChildren(IndustryCode node) {
        List<IndustryCode> children = new ArrayList<>();
        Long id = node.getId();
        for (IndustryCode child : nodes) {
            if (child.getParentIndustryId().equals(id.toString())) {//如果id等于父id
                children.add(child);//将该节点加入循环列表中
            }
        }
        return children;
    }

}

