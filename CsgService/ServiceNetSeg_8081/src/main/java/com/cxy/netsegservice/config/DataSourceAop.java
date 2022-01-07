package com.cxy.netsegservice.config;

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
    @Pointcut("@annotation(com.cxy.netsegservice.config.WorkSlave) " +
            "|| execution(* com.cxy.netsegservice..service..*.select*(..)) " +
            "|| execution(* com.cxy.netsegservice..service..*.list*(..)) " +
            "|| execution(* com.cxy.netsegservice..service..*.query*(..)) " +
            "|| execution(* com.cxy.netsegservice..service..*.find*(..)) " +
            "|| execution(* com.cxy.netsegservice..service..*.get*(..))")
    public void readPointcut() {
        log.info("readPointcut");
    }

    /**
     * 使用主库插入更新
     */
    @Pointcut("@annotation(com.cxy.netsegservice.config.WorkMaster) " +
            "|| execution(* com.cxy.netsegservice..service..*.login(..)) " +
            "|| execution(* com.cxy.netsegservice..service..*.insert*(..)) " +
            "|| execution(* com.cxy.netsegservice..service..*.add*(..)) " +
            "|| execution(* com.cxy.netsegservice..service..*.update*(..)) " +
            "|| execution(* com.cxy.netsegservice..service..*.edit*(..)) " +
            "|| execution(* com.cxy.netsegservice..service..*.delete*(..)) " +
            "|| execution(* com.cxy.netsegservice..service..*.remove*(..))")
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
