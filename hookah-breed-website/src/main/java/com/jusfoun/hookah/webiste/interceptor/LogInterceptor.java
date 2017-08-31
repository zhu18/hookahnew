package com.jusfoun.hookah.webiste.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jusfoun.hookah.core.annotation.Log;
import com.jusfoun.hookah.core.domain.OperateInfo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.NetUtils;
import com.jusfoun.hookah.rpc.api.OperateInfoMongoService;
import com.jusfoun.hookah.webiste.controller.BaseController;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.jusfoun.hookah.core.constants.OperateConstants.key_content;

/**
 * Created by chenhf on 2017/7/18.
 */
@Component
@Aspect
public class LogInterceptor extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Autowired
    OperateInfoMongoService operateInfoMongoService;

    @Pointcut("@annotation(com.jusfoun.hookah.core.annotation.Log)")
    private void cut() {
    }

    @AfterReturning("cut()")
    public void advice(JoinPoint joinPoint) {
        logger.info("");
        OperateInfo operateInfo = new OperateInfo();
        //获取用户信息
        String userId = "";
        String userName = "";
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String,String[]> map = (Map<String,String[]>)request.getParameterMap();
        Map<String,String> jsonMap = new HashMap<>();
        String json = "";
        if (map.size() != 0){
            for(String name:map.keySet()){
                String[] values = map.get(name);
                for (String key:key_content){
                    if (key.equals(name)){
                        jsonMap.put(name,Arrays.toString(values));
                    }
                }
                if ("userName".equals(name))
                    userName = values[0];
            }
            ObjectMapper mapper = new ObjectMapper();
            try {
                json = mapper.writeValueAsString(jsonMap);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        try {
            userId= this.getCurrentUser().getUserId();
            userName = this.getCurrentUser().getUserName();
        } catch (HookahException e) {
            e.printStackTrace();
        }
        //获取请求IP
        String ip = NetUtils.getIpAddr(request);
        //获取请求uri
        String uri = request.getRequestURI();
        Date operateTime = DateUtils.now();
        HttpSession session = request.getSession();
        //获取注解的参数
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = null;
        try {
            targetClass = Class.forName(targetName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Method[] methods = targetClass.getMethods();
        String logType = "";
        String platform = "";
        String optType = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    logType = method.getAnnotation(Log.class).logType();
                    platform = method.getAnnotation(Log.class).platform();
                    optType = method.getAnnotation(Log.class).optType();
                    break;
                }
            }
        }
        operateInfo.setLogType(logType);
        operateInfo.setOptType(optType);
        operateInfo.setPlatform(platform);
        operateInfo.setUserId(userId);
        operateInfo.setUserName(userName);
        operateInfo.setOptContent(json);
        operateInfo.setIP(ip);
        operateInfo.setUrl(uri);
        operateInfo.setOperateTime(operateTime);
        operateInfoMongoService.insert(operateInfo);
    }
}
