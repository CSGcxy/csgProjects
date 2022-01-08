package com.cxy.offstatusservice.config;

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
    @Pointcut("@annotation(com.cxy.offstatusservice.config.WorkSlave) " +
            "|| execution(* com.cxy.offstatusservice..service..*.select*(..)) " +
            "|| execution(* com.cxy.offstatusservice..service..*.list*(..)) " +
            "|| execution(* com.cxy.offstatusservice..service..*.query*(..)) " +
            "|| execution(* com.cxy.offstatusservice..service..*.find*(..)) " +
            "|| execution(* com.cxy.offstatusservice..service..*.get*(..))")
    public void readPointcut() {
        log.info("readPointcut");
    }

    /**
     * 使用主库插入更新
     */
    @Pointcut("@annotation(com.cxy.offstatusservice.config.WorkMaster) " +
            "|| execution(* com.cxy.offstatusservice..service..*.login(..)) " +
            "|| execution(* com.cxy.offstatusservice..service..*.insert*(..)) " +
            "|| execution(* com.cxy.offstatusservice..service..*.add*(..)) " +
            "|| execution(* com.cxy.offstatusservice..service..*.update*(..)) " +
            "|| execution(* com.cxy.offstatusservice..service..*.edit*(..)) " +
            "|| execution(* com.cxy.offstatusservice..service..*.delete*(..)) " +
            "|| execution(* com.cxy.offstatusservice..service..*.remove*(..))")
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
