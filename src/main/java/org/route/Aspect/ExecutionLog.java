package org.route.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionLog {

    @Pointcut("execution(* org.route.*Service.*(..))")
    public void serviceMethods() {}

    @Around("serviceMethods()")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("Method " + joinPoint.getSignature().getName() + " executed in " + elapsedTime + " ms.");
        return result;
    }


    @Pointcut("execution(* org.route.Service.ProductService.getPrice(..))")
    public void productServiceMethods() {}

    @Around("productServiceMethods()")

    //@Around("execution(* org.route.Service.ProductService.*(..))")
    public Object executeLog(ProceedingJoinPoint jp) throws Throwable {
        String methodName = jp.getSignature().toShortString();
        System.out.println(methodName + " Started");

        Object result = jp.proceed(); //
        System.out.println(methodName + " Success");

        System.out.println(methodName + " Finished");
        return result;
    }
}