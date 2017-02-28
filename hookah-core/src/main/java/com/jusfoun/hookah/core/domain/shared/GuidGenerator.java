package com.jusfoun.hookah.core.domain.shared;


import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author Shengzhao Li
 */
public abstract class GuidGenerator {


    private GuidGenerator() {
    }

    public static String generate() {
        return RandomStringUtils.random(32, true, true);
    }


    public static String generateClientId() {
        return RandomStringUtils.random(20, true, true);
    }

    public static String generateClientSecret() {
        return RandomStringUtils.random(20, true, true);
    }

}