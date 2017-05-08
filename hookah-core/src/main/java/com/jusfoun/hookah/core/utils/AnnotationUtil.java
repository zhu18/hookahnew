package com.jusfoun.hookah.core.utils;

import com.jusfoun.hookah.core.annotation.EsField;
import com.jusfoun.hookah.core.domain.es.EsFieldMapping;
import com.jusfoun.hookah.core.domain.es.EsGmFieldMapping;
import org.springframework.data.annotation.Id;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


/**
 * Created by wangjl on 2017-3-28.
 */
public class AnnotationUtil {
    private static Map<Class, Field[]> fieldsCache = new Hashtable<>();

    public static String getEsField(Class clazz, Map<String, EsFieldMapping> properties) {
        String keyIdFieldName = "";
        for (Field field : clazz.getDeclaredFields()) {
            EsField esField = field.getAnnotation(EsField.class);
            Id id = field.getAnnotation(Id.class);
            if (esField != null) {
                EsGmFieldMapping esGmFieldMapping;
                if(field.getName().indexOf("suggest") >= 0) {
                    esGmFieldMapping = new EsGmFieldMapping();
                    esGmFieldMapping.setType(esField.type().val);
                    esGmFieldMapping.setAnalyzer(esField.analyzer().val);
                    esGmFieldMapping.setSearch_analyzer(esField.searchAnalyzer().val);
                }else {
                     esGmFieldMapping = new EsGmFieldMapping(esField.analyzeOpt().val, esField.type().val,
                            esField.analyzer().val, esField.termVector().val, esField.isStore(), esField.searchAnalyzer().val);
                    if(esField.fielddata()) {
                        esGmFieldMapping.setFielddata(esField.fielddata());
                    }
                    if(esField.copyTo() != null && esField.copyTo().length > 0) {
                        esGmFieldMapping.setCopy_to(esField.copyTo());
                    }
                    if (StringUtils.isNotBlank(esField.format())) {
                        esGmFieldMapping.setFormat(esField.format());
                    }
                }
                properties.put(field.getName(), esGmFieldMapping);
            }
            if(id != null) {
                keyIdFieldName = field.getName();
            }
        }
        return keyIdFieldName;
    }

    public static <T>Map<String, Object> convert2Map(T t) throws IllegalAccessException{
        Map<String, Object> maps = new HashMap<>();
        Class clazz = t.getClass();
        Field[] fields = fieldsCache.get(clazz);
        if(fields == null) {
            fields = t.getClass().getDeclaredFields();
            fieldsCache.put(clazz, fields);
        }
        for (Field field : fields) {
            field.setAccessible(true);
            if(field.get(t) != null && !"".equals(field.get(t))) {
                maps.put(field.getName(), field.get(t));
                field.setAccessible(false);
            }
        }
        return maps;
    }
}
