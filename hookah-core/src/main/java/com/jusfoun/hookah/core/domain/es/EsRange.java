package com.jusfoun.hookah.core.domain.es;

import java.io.Serializable;

/**
 * Created by wangjl on 2017-4-26.
 */
public class EsRange implements Serializable {
    private Long priceFrom;
    private Long priceTo;

    public Long getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(Long priceFrom) {
        this.priceFrom = priceFrom;
    }

    public Long getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(Long priceTo) {
        this.priceTo = priceTo;
    }
}
