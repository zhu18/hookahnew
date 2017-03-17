package com.jusfoun.hookah.core.domain.mongo;

import com.jusfoun.hookah.core.generic.GenericModel;
import com.jusfoun.hookah.core.utils.DateUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjl on 2017-3-16.
 * 商品分类与属性分类关系表
 */
@Document
public class MgCategoryAttrType extends GenericModel implements Serializable {
    @Id
    private String catId; //商品分类id
    private List<AttrTypeBean> attrTypelist; //商品
    private String addTime;
    private String lastUpdateTime;
    private String userId;
    private String domainId;

    static class AttrTypeBean implements Serializable {
        private String typeId;
        private String typeName;
        private List<AttrBean> attrlist;

        static class AttrBean implements Serializable{
            private String attrId;
            private String attrName;
            private Integer level;
            private Integer isLeafe;

            public String getAttrId() {
                return attrId;
            }

            public void setAttrId(String attrId) {
                this.attrId = attrId;
            }

            public String getAttrName() {
                return attrName;
            }

            public void setAttrName(String attrName) {
                this.attrName = attrName;
            }

            public Integer getLevel() {
                return level;
            }

            public void setLevel(Integer level) {
                this.level = level;
            }

            public Integer getIsLeafe() {
                return isLeafe;
            }

            public void setIsLeafe(Integer isLeafe) {
                this.isLeafe = isLeafe;
            }
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public List<AttrBean> getAttrlist() {
            return attrlist;
        }

        public void setAttrlist(List<AttrBean> attrlist) {
            this.attrlist = attrlist;
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

    public void setAddTime() {
        this.addTime = DateUtils.toDefaultNowTime();
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime() {
        this.lastUpdateTime = DateUtils.toDefaultNowTime();
    }

    public List<AttrTypeBean> getAttrTypelist() {
        return attrTypelist;
    }

    public void setAttrTypelist(List<AttrTypeBean> attrTypelist) {
        this.attrTypelist = attrTypelist;
    }
}