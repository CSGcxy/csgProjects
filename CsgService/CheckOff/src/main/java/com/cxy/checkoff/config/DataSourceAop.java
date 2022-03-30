package com.cxy.checkoff.config;

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
    @Pointcut("@annotation(com.cxy.checkoff.config.WorkMaster) " +
            "|| execution(* com.cxy.checkoff..service.off..*.select*(..)) " +
            "|| execution(* com.cxy.checkoff..service.off..*.list*(..)) " +
            "|| execution(* com.cxy.checkoff..service.off..*.query*(..)) " +
            "|| execution(* com.cxy.checkoff..service.off..*.find*(..)) " +
            "|| execution(* com.cxy.checkoff..service.off..*.get*(..))")
    public void readSlavePointcut() {
        log.info("readSlavePointcut");
    }

//    /**
//     * 使用主库插入更新
//     */
//    @Pointcut("@annotation(com.cxy.checkoff.config.WorkMaster) " +
//            "|| execution(* com.cxy.checkoff..service.netseg..*.login(..)) " +
//            "|| execution(* com.cxy.checkoff..service.netseg..*.insert*(..)) " +
//            "|| execution(* com.cxy.checkoff..service.netseg..*.add*(..)) " +
//            "|| execution(* com.cxy.checkoff..service.netseg..*.update*(..)) " +
//            "|| execution(* com.cxy.checkoff..service.netseg..*.edit*(..)) " +
//            "|| execution(* com.cxy.checkoff..service.netseg..*.delete*(..)) " +
//            "|| execution(* com.cxy.checkoff..service.netseg..*.remove*(..))")
//    public void writePointcut() {
//        log.info("writePointcut");
//    }
    /**
     * 使用主库查询表1
     */
    @Pointcut("@annotation(com.cxy.checkoff.config.WorkSlave) " +
            "|| execution(* com.cxy.checkoff..service.check..*.select*(..)) " +
            "|| execution(* com.cxy.checkoff..service.check..*.list*(..)) " +
            "|| execution(* com.cxy.checkoff..service.check..*.query*(..)) " +
            "|| execution(* com.cxy.checkoff..service.check..*.find*(..)) " +
            "|| execution(* com.cxy.checkoff..service.check..*.get*(..))")
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
