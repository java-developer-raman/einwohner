package com.sharma.db;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {

    @Autowired
    @Qualifier("adminDataSource")
    private DataSource dataSource;

    @Value("liquibase.contexts")
    private String liquibaseContexts;
    @Value("db.admin.user")
    private String adminUser;
    @Value("db.admin.password")
    private String adminPassword;
    @Value("spring.datasource.url")
    private String dbUrl;

    @Bean
    public SpringLiquibase springLiquibase(){
        SpringLiquibase springLiquibaseBean = new SpringLiquibase();
        springLiquibaseBean.setDataSource(dataSource);
        springLiquibaseBean.setChangeLog("classpath:liquibase/db-changelog-master.xml");
        springLiquibaseBean.setContexts(liquibaseContexts);
        return springLiquibaseBean;
    }

}
