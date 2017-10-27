package com.jusfoun.hookah.core.domain.zb.vo;

import com.jusfoun.hookah.core.domain.zb.ZbAnnex;
import com.jusfoun.hookah.core.domain.zb.ZbProgram;

import java.util.List;

/**
 * Created by ctp on 2017/9/26.
 */
public class ZbProgramVo extends ZbProgram {

    private List<ZbAnnex> annex;

    public List<ZbAnnex> getAnnex() {
        return annex;
    }

    public void setAnnex(List<ZbAnnex> annex) {
        this.annex = annex;
    }
}
