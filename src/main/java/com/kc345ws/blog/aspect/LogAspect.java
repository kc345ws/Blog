package com.kc345ws.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect//切面类
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());//日志记录器
    //任何访问类型的在com.kc345ws.blog.controller下任何类的任何方法使用切入点
    // (对连接点进行了过滤，在部分连接点切入)
    @Pointcut("execution(* com.kc345ws.blog.controller.*.*(..))")//切点
    public void log(){}

    @Before("log()")//通知，来增强功能
    public void logbefore(JoinPoint joinPoint){//通过连接点获取连接点对象的一些信息
        //使用了ThreadLocal，可以直接获取到HttpServletRequest
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        final HttpServletRequest request = requestAttributes.getRequest();
        final String url = request.getRequestURL().toString();
        final String ip = request.getRemoteAddr();
        String classname = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url,ip,classname,args);
        logger.info("-------------切点执行前---------------");
        logger.info("RequestLog : {}",requestLog);
    }

    @After("log()")
    public void logafter(){
        logger.info("-------------切点执行后---------------");
    }

    @AfterReturning(returning = "res" , pointcut = "log()")
    public void logreturn(Object res){
        //res为切点执行后返回的结果
        logger.info("切点执行返回结果Request URL: {}",res);
    }

    //请求数据封装
    private class RequestLog{
        private String url;
        private String ip;
        private String classname;
        private Object[] args;

        public RequestLog(String url, String ip, String classname, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classname = classname;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classname='" + classname + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
