package com.example.springbootwebfluxsecuritydao.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;
import java.util.stream.IntStream;

@Aspect
@Profile({"default", "dev", "prod"})
@Component
@Slf4j
public class LogAspect {
    private static final String DASH_LINE = "===================================";
    private static final String NEXT_LINE = "\n";

    @Pointcut("execution(* com.example.springbootwebfluxsecuritydao.config.WebfluxSecurityConfig.*(..))")
    public void logParams() {
    }

    @Around("logParams()")
    public Object redisAudit(ProceedingJoinPoint pjp) throws Throwable {
        StringBuilder builder = new StringBuilder(NEXT_LINE);
        printBefore(builder, pjp);
        // Method Execution
        Object object = pjp.proceed(pjp.getArgs());
        printAfter(builder, object);
        log.info(builder.toString());
        return object;
    }

    private void printBefore(StringBuilder builder, JoinPoint jp) {
        Object[] args = jp.getArgs();
        Parameter[] parameters = ((MethodSignature) jp.getSignature()).getMethod().getParameters();

        builder.append(DASH_LINE);
        builder.append(NEXT_LINE);
        builder.append("[调用]");
        builder.append(NEXT_LINE);
        builder.append(" Class: ");
        builder.append(jp.getTarget().getClass().getName());
        builder.append(NEXT_LINE);
        builder.append("Method: ");
        builder.append(jp.getSignature().getName());
        builder.append(NEXT_LINE);
        builder.append("Params: ");
        builder.append(NEXT_LINE);

        if (null != args && null != parameters) {
            IntStream.range(0, args.length).forEach(index -> {
                builder.append("> ");
                builder.append(parameters[index].getName());
                builder.append(": ");
                builder.append(args[index]);
                builder.append(NEXT_LINE);
            });
        }
    }

    private void printAfter(StringBuilder builder, Object object) {
        builder.append(NEXT_LINE);
        builder.append("[返回]");
        builder.append(NEXT_LINE);
        builder.append("Return: ");
        builder.append(object == null ? "void/null" : object);
        builder.append(NEXT_LINE);
        builder.append(DASH_LINE);
    }
}
