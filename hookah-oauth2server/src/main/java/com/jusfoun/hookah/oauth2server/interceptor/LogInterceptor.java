package com.jusfoun.hookah.oauth2server.interceptor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jusfoun.hookah.core.annotation.Log;
import com.jusfoun.hookah.core.domain.OperateInfo;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.NetUtils;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.OperateInfoMongoService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

import static com.jusfoun.hookah.core.constants.OperateConstants.key_content;

/**
 * Created by chenhf on 2017/7/18.
 */
@Component
@Aspect
public class LogInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Autowired
    OperateInfoMongoService operateInfoMongoService;
    @Autowired
    UserService userService;

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
        try {
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
            Session session = SecurityUtils.getSubject().getSession();
            HashMap<String,String> userMap = (HashMap<String,String>)session.getAttribute("user");
            if (userMap != null){
                User user = userService.selectById(userMap.get("userId"));
                userId = userMap.get("userId");
                userName = user.getUserName();
            }

            //获取请求IP
            String ip = NetUtils.getIpAddr(request);
            //获取请求uri
            String uri = request.getRequestURI();
            Date operateTime = DateUtils.now();
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
        } catch (InvalidSessionException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}
