package controllers;

import com.mchange.v2.c3p0.*;

import java.beans.PropertyVetoException;
import java.sql.*;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.MultipartConfigElement;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		
		factory.setMaxFileSize("1Mb");
		factory.setMaxRequestSize("1Mb");
		
		return factory.createMultipartConfig();
	}	

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
