package com.dhj.demo.business.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @ClassName: TimingAOP
 * @Description TODO
 * @Author itw_wanght02
 * @Date 2019/7/30 14:21
 * @Version 1.0
 */
@Component
@Aspect
@Slf4j
public class TimingAOP {
    @Pointcut("@annotation(Timing)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object timingAroud(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        Long startTime = System.nanoTime();
        Object[] tagertArgs = joinPoint.getArgs();
        result = joinPoint.proceed(tagertArgs);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = signature.getMethod();
        Timing timing = targetMethod.getAnnotation(Timing.class);
        Long endTime = System.nanoTime();
        Long rate = timing.type().getTypeRate();
        String type = timing.type().getTypeName();
        Long useTime = endTime - startTime;
        log.info("方法共用时:{},单位:{}", useTime / rate, type);
        return result;
    }
}
