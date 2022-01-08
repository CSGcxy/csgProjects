package com.cxy.netflowservice.config;

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
    @Pointcut("@annotation(com.cxy.netflowservice.config.WorkSlave) " +
            "|| execution(* com.cxy.netflowservice..service..*.select*(..)) " +
            "|| execution(* com.cxy.netflowservice..service..*.list*(..)) " +
            "|| execution(* com.cxy.netflowservice..service..*.query*(..)) " +
            "|| execution(* com.cxy.netflowservice..service..*.find*(..)) " +
            "|| execution(* com.cxy.netflowservice..service..*.get*(..))")
    public void readPointcut() {
        log.info("readPointcut");
    }

    /**
     * 使用主库插入更新
     */
    @Pointcut("@annotation(com.cxy.netflowservice.config.WorkMaster) " +
            "|| execution(* com.cxy.netflowservice..service..*.login(..)) " +
            "|| execution(* com.cxy.netflowservice..service..*.insert*(..)) " +
            "|| execution(* com.cxy.netflowservice..service..*.add*(..)) " +
            "|| execution(* com.cxy.netflowservice..service..*.update*(..)) " +
            "|| execution(* com.cxy.netflowservice..service..*.edit*(..)) " +
            "|| execution(* com.cxy.netflowservice..service..*.delete*(..)) " +
            "|| execution(* com.cxy.netflowservice..service..*.remove*(..))")
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
