package com.example.springbootdatajpatransaction.config;

import com.example.springbootdatajpatransaction.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

/**
 * basePackages 包名称为字符串, 如果写错误很难排查, 可以用 basePackageClasses 替代, 这些指定的类所在的包就是要扫描的包.
 * 在IDE的支持下, 重构是可以修改包名的. 很少出现这种错误.
 */
@Configuration
@EnableJpaRepositories(
    basePackages = {"com.example.springbootdatajpatransaction.repository"}, // 字符串包名
    basePackageClasses = {AccountRepository.class}                          // 类所在的包名称
)
@EnableTransactionManagement
public class TransactionConfig {

//    @Bean
//    public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }

//    @Autowired
//    DataSource dataSource;
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(true);
//
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setJpaVendorAdapter(vendorAdapter);
//        factory.setPackagesToScan("com.acme.domain");
//        factory.setDataSource(dataSource);
//        return factory;
//    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Autowired
            private AccountRepository repository;

            @Override
            public void run(String... args) throws Exception {
//                repository.save(new Account(1L, "Tom", BigDecimal.valueOf(1000)));
//                repository.save(new Account(2L, "Jerry", BigDecimal.valueOf(2000)));
//                repository.save(new Account(3L, "Donald", BigDecimal.valueOf(3000)));
            }
        };
    }
}
