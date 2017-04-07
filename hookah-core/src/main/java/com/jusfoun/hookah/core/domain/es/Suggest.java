package com.jusfoun.hookah.core.domain.es;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjl on 2017-4-6.
 */
public class Suggest implements Serializable {
    private List<String> input;

    public Suggest(List<String> input) {
        this.input = input;
    }

    public List<String> getInput() {
        return input;
    }

    public void setInput(List<String> input) {
        this.input = input;
    }
}
