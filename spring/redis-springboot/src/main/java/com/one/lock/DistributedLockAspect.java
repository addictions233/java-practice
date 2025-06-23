package com.one.lock;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;

/**
 * 分布式锁
 *
 * @author one
 */
@Aspect
@Slf4j
public class DistributedLockAspect implements ApplicationContextAware {

    private final ExpressionParser expressionParser;

    private final RedissonClient redissonClient;

    private ApplicationContext applicationContext;

    DistributedLockAspect(ExpressionParser expressionParser, RedissonClient redissonClient) {
        this.expressionParser = expressionParser;
        this.redissonClient = redissonClient;
    }


    @Around("@annotation(com.one.lock.DistributedLock)")
    public Object aroundAdvice(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);
        // 锁标识前缀
        Assert.hasLength(distributedLock.prefix(), "锁标识前缀为空");
        Assert.hasLength(distributedLock.key(), "key为空");

        ExpressionMetadata expressionMetadata = new ExpressionMetadata(pjp.getTarget(), method, pjp.getArgs());
        boolean needLock = true;
        if (StringUtils.isNotBlank(distributedLock.condition())) {
            needLock = (boolean) expressionParser.parserExpression(expressionMetadata, distributedLock.condition());
        }
        // 生成锁标识
        String lockKey = distributedLock.prefix() + ":" + expressionParser.parserExpression(expressionMetadata, distributedLock.key());
        RLock lock = null;
        try {
            if (!needLock) {
                return pjp.proceed();
            }
            log.info("获取到分布式锁:{}", lockKey);
            lock = redissonClient.getLock(lockKey);
            if (!lock.tryLock(distributedLock.waitTime(), distributedLock.unit())) {
                String tryFailed = distributedLock.tryFailed();
                if (StringUtils.isEmpty(tryFailed)) {
                    log.info("获取锁异常：{}", lockKey);
                    throw new RuntimeException("获取锁失败");
                }
                return this.tryLockFailed(distributedLock, expressionMetadata, pjp);
            }
            return pjp.proceed();
        } catch (InterruptedException e) {
            String msg = MessageFormat.format("中断, lockKey: {0}", lockKey);
            Thread.currentThread().interrupt();
            throw new RuntimeException(msg);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            if (lock != null && lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info("释放锁，lockKey: {}", lockKey);
            }
        }
    }

    private Object tryLockFailed(DistributedLock distributedLock, ExpressionMetadata expressionMetadata, ProceedingJoinPoint pjp) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String tryFailed = distributedLock.tryFailed();
        if (StringUtils.isEmpty(tryFailed)) {
            throw new RuntimeException("获取锁失败");
        }
        Class<?> clazz;
        String methodName;
        if (tryFailed.contains(":")) {
            String[] methodInfo = tryFailed.split(":");
            String beanName = methodInfo[0];
            clazz = applicationContext.getBean(beanName).getClass();
            methodName = methodInfo[1];
        } else {
            methodName = tryFailed;
            clazz = expressionMetadata.getTargetClass();
        }
        //反射调用失败处理逻辑
        Method method = clazz.getDeclaredMethod(methodName, expressionMetadata.getTargetMethod().getParameterTypes());
        method.setAccessible(true);
        return method.invoke(expressionMetadata.getTarget(), pjp.getArgs());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
