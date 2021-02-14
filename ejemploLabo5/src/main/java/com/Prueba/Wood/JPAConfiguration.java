package com.Prueba.Wood;


import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories (basePackages="com.uca.capas.tarea5.repositories")
public class JPAConfiguration {
	
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPersistenceUnitName("Wood");
		em.setPackagesToScan("com.Prueba.Wood.domain");
		
		
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(hibernateProperties());
		
		return em;
	}

	@Bean
	public DataSource dataSource() {
		// TODO Auto-generated method stub
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		//dataSource.setUrl("jdbc:postgresql://ec2-52-7-168-69.compute-1.amazonaws.com:5432/d6n11tskd5josq");
		//dataSource.setUsername("ghplbdrnyecifl");
		//dataSource.setPassword("1f395dc0746276c55dce256b099bb66871fa486e14c10badc7c4646ac43fe5dd");
		dataSource.setUrl("jdbc:postgresql://ec2-54-162-119-125.compute-1.amazonaws.com:5432/dd5kf8be6ge9s");
		dataSource.setUsername("sfdirgvdoyozay");
		dataSource.setPassword("f9d1029e475645c178d712375d9be521ea7971297b8a323d385d3ba1900a0071");
		//dataSource.setUsername("postgres");
		//dataSource.setPassword("root");
		return dataSource;
	}
	
	 Properties hibernateProperties() {
		// TODO Auto-generated method stub
		Properties properties = new Properties();
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.setProperty("hibernate.enable_lazy_load_no_trans","true");
		
		return properties;
	}

}