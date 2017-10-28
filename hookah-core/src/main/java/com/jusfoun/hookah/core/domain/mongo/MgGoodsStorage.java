package com.jusfoun.hookah.core.domain.mongo;

import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

// FIXME generate failure  field _$FormatList191

/**
 * 货架管理mongo字段
 * Created by wangjl on 2017-10-28.
 */
@Document
public class MgGoodsStorage extends GenericModel {
    @Id
    private String storageId;

    private List<LabelsType> typeList;//标签类型list

    private String goodsIds;//商品id

    private String addUser;

    private String updateTime;


    public static class LabelsType implements Serializable {
        private int typeId;
        private String typeName;
        private String labels;//多个标签逗号分隔
        private String labelsName;//多个标签名称逗号分隔
        private String describle;//描述
        private String img;//图片

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getLabels() {
            return labels;
        }

        public void setLabels(String labels) {
            this.labels = labels;
        }

        public String getDescrible() {
            return describle;
        }

        public void setDescrible(String describle) {
            this.describle = describle;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getLabelsName() {
            return labelsName;
        }

        public void setLabelsName(String labelsName) {
            this.labelsName = labelsName;
        }
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public List<LabelsType> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<LabelsType> typeList) {
        this.typeList = typeList;
    }

    public String getGoodsIds() {
        return goodsIds;
    }

    public void setGoodsIds(String goodsIds) {
        this.goodsIds = goodsIds;
    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
