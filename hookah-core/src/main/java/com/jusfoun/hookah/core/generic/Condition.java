package com.jusfoun.hookah.core.generic;

import java.io.Serializable;

/**
 * 筛选
 * 
 * @author jsshao1986@126.com
 * @version 3.0
 */
public class Condition<Type> implements Serializable {

	private static final long serialVersionUID = -8712382358441065075L;

	/**
	 * 运算符
	 */
	public enum Operator {
		/** 等于 */
		EqualTo,

		/** 不等于 */
		NotEqualTo,

		/** 大于 */
		GreaterThan,

		/** 小于 */
		LessThan,

		/** 大于等于 */
		GreaterThanOrEqualTo,

		/** 小于等于 */
		LessThanOrEqualTo,

		/** 相似 */
		Like,
		
		/** 不相似 */
		NotLike,

		/** 包含 */
		In,
		
		/** 不包含 */
		NotIn,
		
		/** 包含 */
		Between,
		
		/** 包含 */
		NotBetween,

		/** 为Null */
		IsNull,

		/** 不为Null */
		IsNotNull;
	}

	/** 默认是否忽略大小写 */
	private static final boolean DEFAULT_IGNORE_CASE = false;

	/** 属性 */
	private String property;

	/** 运算符 */
	private Operator operator;

	/** 值 */
	private Type value;

	/** 是否忽略大小写 */
	private Boolean ignoreCase = DEFAULT_IGNORE_CASE;

	/**
	 * 初始化一个新创建的Filter对象
	 */
	public Condition() {
	}

	/**
	 * 初始化一个新创建的Filter对象
	 * 
	 * @param property
	 *            属性
	 * @param operator
	 *            运算符
	 * @param value
	 *            值
	 */
	public Condition(String property, Operator operator, Type value) {
		this.property = property;
		this.operator = operator;
		this.value = value;
	}

	/**
	 * 初始化一个新创建的Filter对象
	 * 
	 * @param property
	 *            属性
	 * @param operator
	 *            运算符
	 * @param value
	 *            值
	 * @param ignoreCase
	 *            忽略大小写
	 */
	public Condition(String property, Operator operator, Type value, boolean ignoreCase) {
		this.property = property;
		this.operator = operator;
		this.value = value;
		this.ignoreCase = ignoreCase;
	}
	
	

	/**
	 * 返回等于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 等于筛选
	 */
	public static <T> Condition<T> eq(String property, T value) {
		return new Condition<T>(property, Operator.EqualTo, value);
	}

	/**
	 * 返回等于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @param ignoreCase
	 *            忽略大小写
	 * @return 等于筛选
	 */
	public static <T> Condition<T> eq(String property, T value, boolean ignoreCase) {
		return new Condition<T>(property, Operator.EqualTo, value, ignoreCase);
	}

	/**
	 * 返回不等于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 不等于筛选
	 */
	public static <T> Condition<T> ne(String property, T value) {
		return new Condition<T>(property, Operator.NotEqualTo, value);
	}

	/**
	 * 返回不等于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @param ignoreCase
	 *            忽略大小写
	 * @return 不等于筛选
	 */
	public static <T> Condition<T> ne(String property, T value, boolean ignoreCase) {
		return new Condition<T>(property, Operator.NotEqualTo, value, ignoreCase);
	}

	/**
	 * 返回大于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 大于筛选
	 */
	public static <T> Condition<T> gt(String property, T value) {
		return new Condition<T>(property, Operator.GreaterThan, value);
	}

	/**
	 * 返回小于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 小于筛选
	 */
	public static <T> Condition<T> lt(String property, T value) {
		return new Condition<T>(property, Operator.LessThan, value);
	}

	/**
	 * 返回大于等于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 大于等于筛选
	 */
	public static <T> Condition<T> ge(String property, T value) {
		return new Condition<T>(property, Operator.GreaterThanOrEqualTo, value);
	}

	/**
	 * 返回小于等于筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 小于等于筛选
	 */
	public static <T> Condition<T> le(String property, T value) {
		return new Condition<T>(property, Operator.LessThanOrEqualTo, value);
	}

	/**
	 * 返回相似筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 相似筛选
	 */
	public static <T> Condition<T> like(String property, T value) {
		return new Condition<T>(property, Operator.Like, value);
	}

	/**
	 * 返回包含筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 包含筛选
	 */
	public  static <T> Condition in(String property, T[] value) {
		return new Condition<T[]>(property, Operator.In, value);
	}
	
	/**
	 * 返回包含筛选
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @return 包含筛选
	 */
	public  static <T> Condition between(String property, T[] value) {
		return new Condition<T[]>(property, Operator.Between, value);
	}

	/**
	 * 返回为Null筛选
	 * 
	 * @param property
	 *            属性
	 * @return 为Null筛选
	 */
	public static <T> Condition<T> isNull(String property) {
		return new Condition<T>(property, Operator.IsNull, null);
	}

	/**
	 * 返回不为Null筛选
	 * 
	 * @param property
	 *            属性
	 * @return 不为Null筛选
	 */
	public static Condition<Object> isNotNull(String property) {
		return new Condition<Object>(property, Operator.IsNotNull, null);
	}

	/**
	 * 返回忽略大小写筛选
	 * 
	 * @return 忽略大小写筛选
	 */
	public Condition<Type> ignoreCase() {
		this.ignoreCase = true;
		return this;
	}

	/**
	 * 获取属性
	 * 
	 * @return 属性
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * 设置属性
	 * 
	 * @param property
	 *            属性
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * 获取运算符
	 * 
	 * @return 运算符
	 */
	public Operator getOperator() {
		return operator;
	}

	/**
	 * 设置运算符
	 * 
	 * @param operator
	 *            运算符
	 */
	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	/**
	 * 获取值
	 * 
	 * @return 值
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 设置值
	 * 
	 * @param value
	 *            值
	 */
	public void setValue(Type value) {
		this.value = value;
	}

	/**
	 * 获取是否忽略大小写
	 * 
	 * @return 是否忽略大小写
	 */
	public Boolean getIgnoreCase() {
		return ignoreCase;
	}

	/**
	 * 设置是否忽略大小写
	 * 
	 * @param ignoreCase
	 *            是否忽略大小写
	 */
	public void setIgnoreCase(Boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}
	
	@Override
	public String toString(){
		StringBuffer sbuf = new StringBuffer();
		sbuf.append("and").append(Character.toUpperCase(property.charAt(0))).append(property.substring(1));
		sbuf.append(operator.name());
		return sbuf.toString();
	}
}