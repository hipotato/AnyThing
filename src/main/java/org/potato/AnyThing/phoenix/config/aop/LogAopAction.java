package org.potato.AnyThing.phoenix.config.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.potato.AnyThing.phoenix.config.annotation.ControllerLog;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by potato on 2018/5/2.
 */
@Component
@Aspect
public class LogAopAction {
    private static final Logger log = LoggerFactory.getLogger(LogAopAction.class);


    //指定切点：匹配org.potato.AnyThing.phoenix.controller包及其子包下的所有类的所有方法
    @Pointcut("(execution(* org.potato.AnyThing.hbase.controller..*.*(..)) || execution(* org.potato.AnyThing.phoenix.controller..*.*(..))) and " +
            "@annotation(org.potato.AnyThing.phoenix.config.annotation.ControllerLog)")
    public void executeController(){

    }
    @Before("executeController()")
    public void before(JoinPoint joinPoint){
        try {
            String targetName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();
            Class<?> targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            String operation = "";
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class<?>[] clazzs = method.getParameterTypes();
                    if (clazzs.length == arguments.length) {
                        operation = method.getAnnotation(ControllerLog.class).operation();// 操作人
                        break;
                    }
                }
            }
            StringBuilder paramsBuf = new StringBuilder();
            for (Object arg : arguments) {
                paramsBuf.append(arg);
                paramsBuf.append("&");
            }

            // *========控制台输出=========*//
            log.info("["+operation +"] Method called:" + targetName+"."+methodName +" Parms:"
                    + paramsBuf.toString());
        } catch (Throwable e) {
            log.info("around " + joinPoint + " with exception : " + e.getMessage());
        }
    }
    //返回通知：在目标方法正常结束执行后的通知
    //返回通知是可以访问到目标方法的返回值的
    @AfterReturning(pointcut = "executeController()", returning = "result")
    public void afterRunningMethod(JoinPoint joinPoint , Object result){
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        log.info("The method "+ className+"."+methodName+" ends with the Result "+ result);
    }
    @AfterThrowing(pointcut = "executeController()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        // 通过request获取登陆用户信息
        // HttpServletRequest request = ((ServletRequestAttributes)
        // RequestContextHolder.getRequestAttributes()).getRequest();
        try {
            String targetName = joinPoint.getTarget().getClass().getName();
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();
            Class<?> targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            String operation = "";
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class<?>[] clazzs = method.getParameterTypes();
                    if (clazzs.length == arguments.length) {
                        operation = method.getAnnotation(ControllerLog.class).operation();
                        break;
                    }
                }
            }

            StringBuilder paramsBuf = new StringBuilder();
            for (Object arg : arguments) {
                paramsBuf.append(arg);
                paramsBuf.append("&");
            }

            log.info("异常方法:" + className + "." + methodName + "();参数:" + paramsBuf.toString() + ",处理了:" + operation);
            log.info("异常信息:" + e.getMessage());
        } catch (Exception ex) {
            log.error("异常信息:{}", ex.getMessage());
        }
    }
    /**
     * 环绕通知处理处理
     *
     * @param point
     * @throws Throwable
     */
    @Around("executeController()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        Object result = point.proceed();
        Signature sig = point.getSignature();
        try {
            if (!(sig instanceof MethodSignature)) {
                throw new IllegalArgumentException("该注解只能用于方法");
            }
        } catch (Exception e) {
            log.error("日志记录异常", e);
        }
        return result;
    }
}
