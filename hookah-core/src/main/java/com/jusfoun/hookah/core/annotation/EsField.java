package com.jusfoun.hookah.core.annotation;

import com.jusfoun.hookah.core.constants.HookahConstants.Analyzer;
import com.jusfoun.hookah.core.constants.HookahConstants.AnalyzeOpt;
import com.jusfoun.hookah.core.constants.HookahConstants.TermVector;

import java.lang.annotation.*;

/**
 * Created by wangjl on 2017-3-28.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EsField {
    AnalyzeOpt analyzeOpt() default AnalyzeOpt.NOT_ANALYZED;
    Analyzer analyzer() default Analyzer.IK_MAX_WORD;
    TermVector termVector() default TermVector.NONE;
    boolean isStore() default false;
}
