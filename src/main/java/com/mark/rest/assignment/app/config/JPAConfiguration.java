package com.mark.rest.assignment.app.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "appEntityManager",
        transactionManagerRef = "appTransactionManager",
        basePackages = { "com.mark.rest.assignment.app.repo" })
public class JPAConfiguration {

    @Value("${application.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${application.datasource.url}")
    private String databaseUrl;

    @Value("${application.datasource.username}")
    private String username;

    @Value("${application.datasource.password}")
    private String password;

    @Value("${application.datasource.hibernate.dialect}")
    private String dialect;

    @Bean(name = "appDataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(
                databaseUrl, username, password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;

    }

    @Bean(name = "appEntityManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("appDataSource") DataSource dataSource) {

        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", dialect);
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean
                .setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean
                .setPackagesToScan("com.mark.rest.assignment.app.entity");
        entityManagerFactoryBean.setJpaProperties(properties);
        return entityManagerFactoryBean;
    }

    @Bean(name = "appTransactionManager")
    public JpaTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

}
