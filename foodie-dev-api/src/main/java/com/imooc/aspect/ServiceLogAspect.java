package com.imooc.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ServiceLogAspect {
    /**
     * AOP通知：
     * 1.前置通知：方法调用前通知
     * 2.后置通知：方法调用正常执行后通知
     * 3.环绕通知：方法调用之前和之后，都分别可以调用的通知
     * 4.异常通知：方法调用异常时通知
     * 5.最终通知：方法调用后通知
     */
    /**
     * 切面表达式
     *第一处 * 表示所有的返回类型
     * 第二处 包名代表AOP监控的所有的包
     * 第三处.. 表示该包以及子包下的所有类,方法
     * 第四处 *.*  代表所有的类
     * 第五处 *(..)表示类中的方法名 (..)表示方法中所有的参数
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.imooc.service.impl..*.*(..))")
    public Object RecordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("======开始执行{}.{}}=========",
                joinPoint.getTarget().getClass(),
                joinPoint.getSignature().getName());
        //记录时间
        long begin = System.currentTimeMillis();
        //执行目标
        Object object = joinPoint.proceed();
        long end = System.currentTimeMillis();

        long time = end - begin;
        if(time > 3000){
            log.warn("=========任务执行耗时{} 毫秒",time);
        }else{
            log.info("=========任务执行耗时{} 毫秒",time);
        }

        return  object;
    }

}
