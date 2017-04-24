package com.jusfoun.hookah.core.domain.es;

import com.jusfoun.hookah.core.annotation.EsField;
import com.jusfoun.hookah.core.constants.HookahConstants;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangjl on 2017-4-24.
 */
public class EsCategory implements Serializable {
    @Id
    @EsField
    private String catId;
    @EsField
    private String catName;
    @EsField
    private String domainId;
    @EsField
    private String parentId;
    @EsField(type = HookahConstants.Type.BYTE)
    private Byte level;
    @EsField
    private Date lastUpdateTime;
    @EsField(type = HookahConstants.Type.BYTE)
    private String addUser;
    @EsField(analyzeOpt= HookahConstants.AnalyzeOpt.ANALYZED, analyzer= HookahConstants.Analyzer.LC_INDEX,
            termVector= HookahConstants.TermVector.OFFSETS, isStore = true, searchAnalyzer = HookahConstants.Analyzer.LC_SEARCH)
    private String fullName;
    @EsField
    private String fullIds;

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullIds() {
        return fullIds;
    }

    public void setFullIds(String fullIds) {
        this.fullIds = fullIds;
    }
}
