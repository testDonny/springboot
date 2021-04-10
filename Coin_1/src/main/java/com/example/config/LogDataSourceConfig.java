package com.example.config;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "logEntityManagerFactory", //
		transactionManagerRef = "logTransactionManager", //
		basePackages = { "com.example.log.dao", })
@Configuration
@EnableConfigurationProperties
@Slf4j
public class LogDataSourceConfig {

	@Autowired
	private HibernateProperties hibernateProperties;

	@Autowired
	private JpaProperties jpaProperties;

//	@Bean(name = "logDataSourceProperties")
//	@ConfigurationProperties(prefix = "spring.datasource.log.hikari")
//	public DataSourceProperties dataSourceProperties() {
//		DataSourceProperties dataSourceProperties = new DataSourceProperties();
//		return dataSourceProperties;
//	}

	@Bean(name = "logHikariConfig")
	@ConfigurationProperties(prefix = "spring.datasource.log.hikari")
	public HikariConfig hikariConfig() {
		HikariConfig hikariConfig = new HikariConfig();
		return hikariConfig;
	}

	@Bean(name = "logDataSource")
	public DataSource dataSource(@Qualifier("logHikariConfig") HikariConfig hikariConfig) {

		log.info("poolName: " + hikariConfig.getPoolName());
		log.info("jdbcUrl: " + hikariConfig.getJdbcUrl());
		log.info("username: " + hikariConfig.getUsername());
		log.info("driverClassName: " + hikariConfig.getDriverClassName());
		log.info("maximumPoolSize: " + hikariConfig.getMaximumPoolSize());
		log.info("connectionTimeout: " + hikariConfig.getConnectionTimeout());
		log.info("autoCommit: " + hikariConfig.isAutoCommit());
		DataSource dataSource = new HikariDataSource(hikariConfig);
		return dataSource;
	}

	@Bean(name = "logEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("logDataSource") DataSource dataSource) {
		// 2020/02/05
		Map<String, Object> properties = hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(),
				new HibernateSettings());
		//
		return builder.dataSource(dataSource)//
				.packages("com.example.log.model").persistenceUnit("logDatabase")
				// poolName=logDatabase
				.properties(properties)//
				.build();
	}

	@Bean(name = "logTransactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("logEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

	@Bean(name = "logJdbcTemplate")
	public JdbcTemplate jdbcTemplate(@Qualifier("logDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}
