package com.jusfoun.hookah.core.domain.vo;

import java.io.Serializable;
import java.util.List;


/**
 * Created by wangjl on 2017-4-13.
 */
public class EsTreeVo<T> implements Serializable{
    private String nodeId;
    private String nodeName;
    private Byte level;
    private String parentId;
    private Long cnt;
    private List<EsTreeVo<T>> children;

    public EsTreeVo() {}

    public EsTreeVo(String nodeId, String nodeName, Byte level, String parentId, Long cnt) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.level = level;
        this.parentId = parentId;
        this.cnt = cnt;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Long getCnt() {
        return cnt;
    }

    public void setCnt(Long cnt) {
        this.cnt = cnt;
    }

    public List<EsTreeVo<T>> getChildren() {
        return children;
    }

    public void setChildren(List<EsTreeVo<T>> children) {
        this.children = children;
    }
}
