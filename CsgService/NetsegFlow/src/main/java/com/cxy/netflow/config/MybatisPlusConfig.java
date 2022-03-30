package com.cxy.netflow.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;


@Configuration
@MapperScan("com.cxy.netflow.mapper")
public class MybatisPlusConfig {

    /**
     * xml文件所在路径
     */
    @Value("${mybatis-plus.mapper-locations}")
    private String mapperLocations;
    /**
     * 别名扫描包名
     */
    @Value("${mybatis-plus.type-aliases-package}")
    private String typeAliasesPackage;
    /**
     * 是否显示banner
     */
    @Value("${mybatis-plus.global-config.banner}")
    private Boolean banner;
    /**
     * 是否开启转驼峰
     */
    @Value("${mybatis-plus.configuration.map-underscore-to-camel-case}")
    private Boolean mapUnderscoreToCamelCase;


    /**
     * 数据源路由
     */
    @Resource(name = "myRoutingDataSource")
    private DataSource myRoutingDataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(myRoutingDataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);
        // mybatisConfiguration.setObjectWrapperFactory(new MapWrapperFactory());
        // 配置打印sql语句
//        mybatisConfiguration.setLogImpl(StdOutImpl.class);
        sqlSessionFactoryBean.setConfiguration(mybatisConfiguration);
        GlobalConfig config = new GlobalConfig();
        config.setBanner(banner);
        sqlSessionFactoryBean.setGlobalConfig(config);
        // 添加分页功能
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{new PaginationInterceptor()});
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 事务配置
     *
     * @return 事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager tx = new DataSourceTransactionManager();
        tx.setDataSource(myRoutingDataSource);
        return tx;
    }

    /**
     * 旧版mybatis-plus分页插件
     */
//    @Bean
//    public PaginationInnerInterceptor paginationInnerInterceptor() {
//        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
//        //最大单页限制数量，默认500条
//        paginationInnerInterceptor.setMaxLimit(100L);
//        return paginationInnerInterceptor;
//
//    }
    /**
     * 新版mybatis-plus分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.SQLITE));
        return interceptor;
    }

}
