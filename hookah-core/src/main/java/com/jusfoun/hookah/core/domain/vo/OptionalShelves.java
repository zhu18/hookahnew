package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.GoodsShelves;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjl on 2017-3-23.
 */
public class OptionalShelves implements Serializable {
    private List<GoodsShelves> sysShelvesList;
    private List<GoodsShelves> myselfShelvesList;
}
