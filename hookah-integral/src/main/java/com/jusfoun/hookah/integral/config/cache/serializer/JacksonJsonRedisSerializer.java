/*
 * Copyright 2011-2014 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jusfoun.hookah.integral.config.cache.serializer;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;
import org.springframework.util.Assert;

import java.nio.charset.Charset;

/**
 * {@link RedisSerializer} that can read and write JSON using <a href="http://jackson.codehaus.org/">Jackson's</a>
 * {@link ObjectMapper}.
 * <p>
 * This converter can be used to bind to typed beans, or untyped {@link java.util.HashMap HashMap} instances.
 * <b>Note:</b>Null objects are serialized as empty arrays and vice versa.
 * 
 * @author Costin Leau
 * @author Thomas Darimont
 */
public class JacksonJsonRedisSerializer<T> implements RedisSerializer<T> {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private final JavaType javaType;

	private ObjectMapper objectMapper = new ObjectMapper();

    {
        objectMapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    }


	/**
	 * Creates a new {@link JacksonJsonRedisSerializer} for the given target {@link Class}.
	 * 
	 * @param type
	 */
	public JacksonJsonRedisSerializer(Class<T> type) {
		this.javaType = getJavaType(type);
	}

	/**
	 * Creates a new {@link JacksonJsonRedisSerializer} for the given target {@link JavaType}.
	 * 
	 * @param javaType
	 */
	public JacksonJsonRedisSerializer(JavaType javaType) {
		this.javaType = javaType;
	}

	@SuppressWarnings("unchecked")
	public T deserialize(String value) throws SerializationException {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		try {
			return (T) this.objectMapper.readValue(value, javaType);
		} catch (Exception ex) {
			throw new SerializationException("Could not read JSON: " + ex.getMessage(), ex);
		}
	}

	public String serialize(Object t) throws SerializationException {
		if (t == null) {
			return StringUtils.EMPTY;
		}
		try {
			return this.objectMapper.writeValueAsString(t);
		} catch (Exception ex) {
			throw new SerializationException("Could not write JSON: " + ex.getMessage(), ex);
		}
	}

	/**
	 * Sets the {@code ObjectMapper} for this view. If not set, a default {@link ObjectMapper#ObjectMapper() ObjectMapper}
	 * is used.
	 * <p>
	 * Setting a custom-configured {@code ObjectMapper} is one way to take further control of the JSON serialization
	 * process. For example, an extended {@link org.codehaus.jackson.map.SerializerFactory} can be configured that
	 * provides custom serializers for specific types. The other option for refining the serialization process is to use
	 * Jackson's provided annotations on the types to be serialized, in which case a custom-configured ObjectMapper is
	 * unnecessary.
	 */
	public void setObjectMapper(ObjectMapper objectMapper) {
		Assert.notNull(objectMapper, "'objectMapper' must not be null");
		this.objectMapper = objectMapper;
	}

	/**
	 * Returns the Jackson {@link JavaType} for the specific class.
	 * <p>
	 * Default implementation returns {@link TypeFactory#type(java.lang.reflect.Type)}, but this can be overridden in
	 * subclasses, to allow for custom generic collection handling. For instance:
	 * 
	 * <pre class="code">
	 * protected JavaType getJavaType(Class&lt;?&gt; clazz) {
	 * 	if (List.class.isAssignableFrom(clazz)) {
	 * 		return TypeFactory.collectionType(ArrayList.class, MyBean.class);
	 * 	} else {
	 * 		return super.getJavaType(clazz);
	 * 	}
	 * }
	 * </pre>
	 * 
	 * @param clazz the class to return the java type for
	 * @return the java type
	 */
	protected JavaType getJavaType(Class<?> clazz) {
		return TypeFactory.defaultInstance().constructType(clazz);
	}
}
