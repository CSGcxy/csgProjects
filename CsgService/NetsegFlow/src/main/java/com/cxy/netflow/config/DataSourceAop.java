package com.cxy.netflow.config;

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
     * 使用从库查询表2
     */
    @Pointcut("@annotation(com.cxy.netflow.config.WorkSlave) " +
            "|| execution(* com.cxy.netflow..service.flows..*.select*(..)) " +
            "|| execution(* com.cxy.netflow..service.flows..*.list*(..)) " +
            "|| execution(* com.cxy.netflow..service.flows..*.query*(..)) " +
            "|| execution(* com.cxy.netflow..service.flows..*.find*(..)) " +
            "|| execution(* com.cxy.netflow..service.flows..*.get*(..))")
    public void readSlavePointcut() {
        log.info("readSlavePointcut");
    }

//    /**
//     * 使用主库插入更新
//     */
//    @Pointcut("@annotation(com.cxy.netflow.config.WorkMaster) " +
//            "|| execution(* com.cxy.netflow..service.netseg..*.login(..)) " +
//            "|| execution(* com.cxy.netflow..service.netseg..*.insert*(..)) " +
//            "|| execution(* com.cxy.netflow..service.netseg..*.add*(..)) " +
//            "|| execution(* com.cxy.netflow..service.netseg..*.update*(..)) " +
//            "|| execution(* com.cxy.netflow..service.netseg..*.edit*(..)) " +
//            "|| execution(* com.cxy.netflow..service.netseg..*.delete*(..)) " +
//            "|| execution(* com.cxy.netflow..service.netseg..*.remove*(..))")
//    public void writePointcut() {
//        log.info("writePointcut");
//    }
    /**
     * 使用主库查询表1
     */
    @Pointcut("@annotation(com.cxy.netflow.config.WorkMaster) " +
            "|| execution(* com.cxy.netflow..service.netseg..*.select*(..)) " +
            "|| execution(* com.cxy.netflow..service.netseg..*.list*(..)) " +
            "|| execution(* com.cxy.netflow..service.netseg..*.query*(..)) " +
            "|| execution(* com.cxy.netflow..service.netseg..*.find*(..)) " +
            "|| execution(* com.cxy.netflow..service.netseg..*.get*(..))")
    public void readMasterPointcut() {
        log.info("readMasterPointcut");
    }


    @Before("readSlavePointcut()")
    public void readSlave() {
        DBContextHolder.workSlave();
    }

    @Before("readMasterPointcut()")
    public void readMaster() {
        DBContextHolder.workMaster();
    }

}
