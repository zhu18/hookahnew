package com.jusfoun.hookah.core.utils;


import java.io.Serializable;

public class ReturnData<T> implements Serializable {

    private static final long serialVersionUID = -5163980836046019923L;

    private static String Success = ExceptionConst.Success;
    
    private static String Error = ExceptionConst.Error;
    
    private static String Fail = ExceptionConst.Failed;

    private static String InvalidParameters = ExceptionConst.InvalidParameters;

    /**
     * @Fields data : 返回数据主体内容
     */
    private T data;

    /**
     * @Fields code : 返回结果代码
     */
    private String code;

    /**
     * @Fields message : 返回结果信息
     */
    private String message;

	/* Constructor */

    public ReturnData() {
        super();
        this.setCode(Success);
        this.setMessage(ExceptionConst.get(Success));
    }
    
    public ReturnData(String code) {
        super();
        this.setCode(code);
        this.setMessage(ExceptionConst.get(code));
    }

    public ReturnData(String code,String message,T data) {
        super();
        this.setCode(code);
        this.setMessage(message);
        this.setData(data);
    }

    @Override
    public String toString() {
        return "ReturnData [data=" + data
                + ", code=" + code + ", message=" + message + "]";
    }

	/* Getter & Setter */

    public T getData() {
        return data;
    }

    public ReturnData<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ReturnData<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ReturnData<T> setMessage(String message) {
        this.message = message;
        return this;
    }
    
    public static ReturnData success(){
    	return new ReturnData(Success,ExceptionConst.get(Success),null);
    }
    
    public static ReturnData empty(){
    	return new ReturnData(ExceptionConst.empty,ExceptionConst.get(ExceptionConst.empty),null);
    }
    
    
    public static <T> ReturnData success(T data){
    	return new ReturnData(Success,ExceptionConst.get(Success),data);
    }
    
    public static ReturnData error(){
    	return new ReturnData(Error,ExceptionConst.get(Fail),null);
    }
    
    public static ReturnData error(String message){
    	return new ReturnData(Error,message,null);
    }
    
    public static ReturnData fail(){
    	return new ReturnData(Fail,ExceptionConst.get(Fail),null);
    }

    public static ReturnData fail(String message){
        return new ReturnData(Fail,message,null);
    }
    
    public static ReturnData invalidParameters(){
    	return new ReturnData(InvalidParameters,ExceptionConst.get(InvalidParameters),null);
    }
    public static ReturnData invalidParameters(String paramName){
    	return new ReturnData(InvalidParameters,paramName+"is null or illegal format!",null);
    }
}
