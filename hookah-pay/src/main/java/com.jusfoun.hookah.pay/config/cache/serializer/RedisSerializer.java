/*
 * Copyright 2011-2013 the original author or authors.
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
package com.jusfoun.hookah.pay.config.cache.serializer;

/**
 * Basic interface serialization and deserialization of Objects to byte arrays (binary data). It is recommended that
 * implementations are designed to handle null objects/empty arrays on serialization and deserialization side. Note that
 * Redis does not accept null keys or values but can return null replies (for non existing keys).
 * 
 * @author Mark Pollack
 * @author Costin Leau
 */
public interface RedisSerializer<T> {

	String serialize(T t) throws SerializationException;

	T deserialize(String value) throws SerializationException;
}
