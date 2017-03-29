package com.jusfoun.hookah.console.server.util;

import com.jusfoun.hookah.core.annotation.EsField;
import com.jusfoun.hookah.core.domain.es.EsFieldMapping;
import com.jusfoun.hookah.core.domain.es.EsGmFieldMapping;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by wangjl on 2017-3-28.
 */
public class AnnotationUtil {
    public static Map<String, EsFieldMapping> getEsField(Class clazz) {
        Map<String, EsFieldMapping> properties = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            EsField esField = field.getAnnotation(EsField.class);
            if (esField != null) {
                EsGmFieldMapping esGmFieldMapping = new EsGmFieldMapping(esField.analyzeOpt().val,
                        field.getGenericType().getTypeName().replace("java.lang.", "").toLowerCase(),
                        esField.analyzer().val, esField.termVector().val, esField.isStore());
                properties.put(field.getName(), esGmFieldMapping);
            }
        }
        return properties;
    }
}
