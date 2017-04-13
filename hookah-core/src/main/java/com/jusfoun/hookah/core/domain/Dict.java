package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author huang lei
 * @date 2017/4/13 下午2:05
 * @desc
 */
public class Dict extends GenericModel {

    @Id
    private Long dictId;

    private Long parentId;

    private String name;

    //顺序
    private String seq;

    //默认0没有，1有
    private String hasChild;


    private String property;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    @Transient
    private List<Dict> children = new ArrayList<Dict>();


    public List<Dict> getChildren() {
        return children;
    }

    public void setChildren(List<Dict> children) {
        this.children = children;
    }

    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getHasChild() {
        return hasChild;
    }

    public void setHasChild(String hasChild) {
        this.hasChild = hasChild;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
