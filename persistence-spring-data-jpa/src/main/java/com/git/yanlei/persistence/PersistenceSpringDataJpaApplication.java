package com.git.yanlei.persistence;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@EnableTransactionManagement
@SpringBootApplication
public class PersistenceSpringDataJpaApplication implements TransactionManagementConfigurer {

    @Resource(name = "transactionManager")
    private PlatformTransactionManager jpaTransactionManager;

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return jpaTransactionManager;
    }

    @Bean(name = "dataSourceTransactionManager")
    public PlatformTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager jpaTransactionManager(
            EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    public static void main(String[] args) {
        SpringApplication.run(PersistenceSpringDataJpaApplication.class, args);
    }
}
