package com.jusfoun.hookah.core.domain.zb.vo;

import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementFiles;

import java.util.List;

/**
 * Created by computer on 2017/9/19.
 */
public class ZbRequirementVo {

    private ZbRequirement zbRequirement;

    private List<ZbRequirementFiles> files;

    public List<ZbRequirementFiles> getFiles() {
        return files;
    }

    public void setFiles(List<ZbRequirementFiles> files) {
        this.files = files;
    }

    public ZbRequirement getZbRequirement() {
        return zbRequirement;
    }

    public void setZbRequirement(ZbRequirement zbRequirement) {
        this.zbRequirement = zbRequirement;
    }
}
