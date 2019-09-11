package com.qjl.log;


import com.qjl.domain.SysLog;
import com.qjl.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Aspect
public class Log {
    @Autowired
    LogService logService;


    @Autowired
    HttpServletRequest request;

    /**
     * 切入点
     */
    @Pointcut("execution(* com.qjl.controller.*.*(..))")
    public void pointcut(){};

    /**
     * 环绕增强
     * 连接点 === 拦截的方法
     * ProceedingJoinPoint： 连接点对象 -- 可以执行真实对象的真实方法 -- 只在环绕增强中使用
     * JoinPoint: 连接点对象 ,-- 不可以执行真实对象的真实方法
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint){
        //创建日志对象
        SysLog log = new SysLog();
        //将日志对象封装
        //1. 访问时间 private String visitTime;
        log.setVisitTime(new Date());
        //2. 访问用户名 private String username;
        //获取securityContext对象
        SecurityContext securityContext = SecurityContextHolder.getContext();
        //获取认证对象
        Authentication authentication = securityContext.getAuthentication();
        //获取用户对象
        User user = (User) authentication.getPrincipal();
        //获取用户名
        String username = user.getUsername();
        log.setUsername(username);
        //3. 访问的ip地址 private String ip;
        //获取ip地址
        String ip = request.getRemoteAddr();
        log.setIp(ip);
        //4. 访问的全限类名.方法 private String method;
        //被拦截的对象
        Object target = joinPoint.getTarget();
        //被拦截的类的全限类名
        String className = target.getClass().getName();
        //获取被拦截的方法
        Signature signature = joinPoint.getSignature();
        //获取方法名称
        String methodName = signature.getName();
        log.setMethod(className +"."+ methodName);
        //将日志对象存储到数据库中

        logService.save(log);

        try {
            //执行真实的方法-- 把真实的方法返回值返回，如果不返回，所有方法被拦截，没有执行返回值
            return joinPoint.proceed();
        } catch (Throwable throwable) {

            throwable.printStackTrace();
        }
        return null;
    }
}
