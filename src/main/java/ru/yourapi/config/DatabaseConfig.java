package ru.yourapi.config;

import liquibase.integration.spring.SpringLiquibase;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.util.Properties;

@Configuration
@ComponentScan("ru.yourapi")
public class DatabaseConfig {

    private static final String PROPERTY_NAME_ENTITY_LOCATION = "ru.yourapi.entity";

    @Value("${app.db.worker.driver}")
    private String driver;

    @Value("${app.db.worker.url}")
    private String url;

    @Value("${app.db.worker.login}")
    private String login;

    @Value("${app.db.worker.password}")
    private String password;

    @Bean
    @Primary
    public BasicDataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(login);
        dataSource.setPassword(password);
        dataSource.setMinIdle(10);
        dataSource.setInitialSize(5);
        return dataSource;
    }

    @Bean
    @DependsOn("dataSource")
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource());
        liquibase.setChangeLog("classpath:liquibase/db-migrations/changelog.xml");
        return liquibase;
    }

    @Bean
    @DependsOn("dataSource")
    public LocalSessionFactoryBean localSessionFactoryBean() {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource());
        localSessionFactoryBean.setPackagesToScan(PROPERTY_NAME_ENTITY_LOCATION);
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
        properties.setProperty("hibernate.show_sql", "false");
        localSessionFactoryBean.setHibernateProperties(properties);
        return localSessionFactoryBean;
    }

}
