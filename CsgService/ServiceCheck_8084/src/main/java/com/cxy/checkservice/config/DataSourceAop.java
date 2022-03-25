package com.cxy.checkservice.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 *
 */
@Aspect
@Component
@Slf4j
public class DataSourceAop {

    /**
     * 使用从库查询
     */
    @Pointcut("@annotation(com.cxy.checkservice.config.WorkSlave) " +
            "|| execution(* com.cxy.checkservice..service..*.select*(..)) " +
            "|| execution(* com.cxy.checkservice..service..*.list*(..)) " +
            "|| execution(* com.cxy.checkservice..service..*.query*(..)) " +
            "|| execution(* com.cxy.checkservice..service..*.find*(..)) " +
            "|| execution(* com.cxy.checkservice..service..*.get*(..))")
    public void readPointcut() {
        log.info("readPointcut");
    }

    /**
     * 使用主库插入更新
     */
    @Pointcut("@annotation(com.cxy.checkservice.config.WorkMaster) " +
            "|| execution(* com.cxy.checkservice..service..*.login(..)) " +
            "|| execution(* com.cxy.checkservice..service..*.insert*(..)) " +
            "|| execution(* com.cxy.checkservice..service..*.add*(..)) " +
            "|| execution(* com.cxy.checkservice..service..*.update*(..)) " +
            "|| execution(* com.cxy.checkservice..service..*.edit*(..)) " +
            "|| execution(* com.cxy.checkservice..service..*.delete*(..)) " +
            "|| execution(* com.cxy.checkservice..service..*.remove*(..))")
    public void writePointcut() {
        log.info("writePointcut");
    }

    @Before("readPointcut()")
    public void read() {
        DBContextHolder.workSlave();
    }

    @Before("writePointcut()")
    public void write() {
        DBContextHolder.workMaster();
    }

}
