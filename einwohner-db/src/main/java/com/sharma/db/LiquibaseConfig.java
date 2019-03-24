package com.sharma.db;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {

    @Autowired
    private DataSource dataSource;

    @Value("liquibase.contexts")
    private String liquibaseContexts;

    @Bean
    public SpringLiquibase springLiquibase(){
        SpringLiquibase springLiquibaseBean = new SpringLiquibase();
        springLiquibaseBean.setDataSource(dataSource);
        springLiquibaseBean.setChangeLog("classpath:liquibase/db-changelog-master.xml");
        springLiquibaseBean.setContexts(liquibaseContexts);
        return springLiquibaseBean;
    }
}
