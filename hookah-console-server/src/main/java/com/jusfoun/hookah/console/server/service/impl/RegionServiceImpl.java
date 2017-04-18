package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.RegionMapper;
import com.jusfoun.hookah.core.domain.Dict;
import com.jusfoun.hookah.core.domain.Region;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.RegionService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/4/5/0005.
 */
@Service
public class RegionServiceImpl extends GenericServiceImpl<Region, Long> implements RegionService {

    @Resource
    private RegionMapper regionMapper;

    @Resource
    public void setDao(RegionMapper regionMapper) {
        super.setDao(regionMapper);
    }

    private List<Region> nodes;

    /**
     * redis序列化的时候Long类型无法被转换，暂未找到解决方案
     * @param id
     * @return
     */
    @Cacheable(value = "regionInfo")
    @Override
    public Region selectById(String id) {
        return regionMapper.selectByPrimaryKey(Long.valueOf(id));
    }

    @Override
    public List<Region> selectTree() {
        nodes = super.selectList();
        List<Region> resultNodes = new ArrayList<>();
        buildTree(resultNodes);
        return resultNodes;
    }

    private List<Region> buildTree(List<Region> resultNodes) {

        for (Region node : nodes) {
            if (node.getPid() == 100000) {//通I过循环一级节点 就可以通过递归获取二级以下节点
                resultNodes.add(node);//添加一级节点
                build(node);//递归获取二级、三级、。。。节点
            }
        }
        return resultNodes;
    }

    private void build(Region node) {
        List<Region> children = getChildren(node);
        if (!children.isEmpty()) {//如果存在子节点
            node.setChildren(children);
            for (Region child : children) {//将子节点遍历加入返回值中
//                child.getChildren().add(child);
                build(child);
            }
        }
    }

    private List<Region> getChildren(Region node) {
        List<Region> children = new ArrayList<Region>();
        Long id = node.getId();
        for (Region child : nodes) {
            if (id.equals(child.getPid())) {//如果id等于父id
                children.add(child);//将该节点加入循环列表中
            }
        }
        return children;
    }

}
