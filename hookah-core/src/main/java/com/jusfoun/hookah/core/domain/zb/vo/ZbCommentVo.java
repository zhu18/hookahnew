package com.jusfoun.hookah.core.domain.zb.vo;

import com.jusfoun.hookah.core.domain.zb.ZbComment;
import com.jusfoun.hookah.core.generic.GenericModel;

/**
 * 评价VO
 * Created by ctp on 2017/10/13.
 */
public class ZbCommentVo extends GenericModel {

    private ZbComment requireZbComment;//需求方评价

    private ZbComment servicerComment;//服务商评价

    public ZbComment getRequireZbComment() {
        return requireZbComment;
    }

    public void setRequireZbComment(ZbComment requireZbComment) {
        this.requireZbComment = requireZbComment;
    }

    public ZbComment getServicerComment() {
        return servicerComment;
    }

    public void setServicerComment(ZbComment servicerComment) {
        this.servicerComment = servicerComment;
    }
}
