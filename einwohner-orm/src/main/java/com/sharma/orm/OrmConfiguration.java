package com.sharma.orm;

import com.sharma.config.server.client.DecryptTextConfigServerRequestSender;
import com.sharma.data.resource.configserver.PayloadToDecrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "com.sharma.orm.repository")
@EnableTransactionManagement
public class OrmConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(OrmConfiguration.class);

    @Autowired
    private Environment environment;

    @Bean
    @Primary
    public DataSource dataSource() {
        logger.info("Creating Datasource for application user");
        String decryptedPassword = decryptText(environment.getProperty("spring.datasource.password"));
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(decryptedPassword);
        return dataSource;
    }

    @Bean
    public DataSource adminDataSource() {
        logger.info("Creating Datasource for admin user to perform liquibase migration.");
        String decryptedPassword = decryptText(environment.getProperty("db.admin.password"));
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("db.admin.user"));
        dataSource.setPassword(decryptedPassword);
        return dataSource;
    }

    private String decryptText(String encryptedText){
        PayloadToDecrypt payloadToDecrypt = new PayloadToDecrypt();
        payloadToDecrypt.setTextToDecrypt(encryptedText);
        payloadToDecrypt.setApplicationName("einwohner");
        DecryptTextConfigServerRequestSender decryptTextRequestSender = new DecryptTextConfigServerRequestSender(environment);
        return decryptTextRequestSender.postDecryptText(payloadToDecrypt);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.sharma.orm.entity");
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.setProperty("hibernate.dialect", environment.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.setProperty("hibernate.show_sql", environment.getProperty("spring.jpa.show-sql"));
        properties.setProperty("hibernate.format_sql", environment.getProperty("spring.jpa.properties.hibernate.format_sql"));
        return properties;
    }
}
