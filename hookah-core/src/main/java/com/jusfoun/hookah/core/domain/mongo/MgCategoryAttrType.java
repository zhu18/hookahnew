package com.jusfoun.hookah.core.domain.mongo;

import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjl on 2017-3-16.
 * 商品分类与属性分类关系表
 */
@Document
public class MgCategoryAttrType extends GenericModel {
    @Id
    private String catId; //商品分类id
    private List<AttrTypeBean> attrTypeList; //商品
    private String addTime;
    private String lastUpdateTime;
    private String userId;
    private String domainId;

//    @JsonSerializableSchema
    public static class AttrTypeBean implements Serializable {
        private String typeId;
        private String typeName;
        private List<AttrBean> attrList;

        public static class AttrBean implements Serializable{
            private String attrId;
            private String attrName;
            private Integer level;

            public String getAttrId() {
                return attrId;
            }

            public void setAttrId(String attrId) {
                this.attrId = attrId;
            }

            public Integer getLevel() {
                return level;
            }

            public void setLevel(Integer level) {
                this.level = level;
            }

            public String getAttrName() {
                return attrName;
            }

            public void setAttrName(String attrName) {
                this.attrName = attrName;
            }
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public List<AttrBean> getAttrList() {
            return attrList;
        }

        public void setAttrList(List<AttrBean> attrList) {
            this.attrList = attrList;
        }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getAddTime() {
        return addTime;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public List<AttrTypeBean> getAttrTypeList() {
        return attrTypeList;
    }

    public void setAttrTypeList(List<AttrTypeBean> attrTypeList) {
        this.attrTypeList = attrTypeList;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}