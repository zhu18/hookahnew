package com.jusfoun.hookah.crowd.config.cache;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;
import static org.springframework.util.Assert.notNull;

/**
 * RedisCacheKey
 *
 * @author LinQ
 * @version 2015-12-17
 */
public class RedisCacheKey {
    private final Object keyElement;
    private String prefix;

    public RedisCacheKey(Object keyElement) {

        notNull(keyElement, "KeyElement must not be null!");
        this.keyElement = keyElement;
    }

    public String getKey() {
        return "cache:".concat(trimToEmpty(prefix).concat(":").concat(keyElement.toString()));
    }

    public boolean hasPrefix() {
        return (prefix != null && prefix.length() > 0);
    }

    public RedisCacheKey usePrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }
}
