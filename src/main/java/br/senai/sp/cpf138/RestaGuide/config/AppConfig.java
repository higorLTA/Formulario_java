package br.senai.sp.cpf138.RestaGuide.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.senai.sp.cpf138.RestaGuide.interceptor.AppInterceptor;

@Configuration
public class AppConfig implements WebMvcConfigurer {
	
	@Autowired
	private AppInterceptor interceptor;
	
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor);
		
	}
	
	@Bean
	public DataSource datasource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3307/cadhotel");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		return dataSource;
	}
	
	//configura o hibernate (ORM - mapeamento objeto relacional)
	@Bean
	public org.springframework.orm.jpa.JpaVendorAdapter JpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQL8Dialect");
		adapter.setShowSql(true);
		adapter.setPrepareConnection(true);
		
		//Cria as tabelas 
		adapter.setGenerateDdl(true);
		return adapter;
		
	}
}


