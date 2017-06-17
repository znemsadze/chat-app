package ge.ssoft.chat.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.nio.charset.Charset;
import java.util.Properties;

import static ge.ssoft.chat.init.ApplicationConfig.*;

/**
 * Created by zviad on 3/15/16.1
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "ge.ssoft.chat.mvc")
public class WebMvcConfig extends WebMvcConfigurerAdapter {



    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }


    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }



    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false).
                favorParameter(true).
                parameterName("mediaType").
                ignoreAcceptHeader(true).
                useJaf(false).
                defaultContentType(MediaType.APPLICATION_JSON).
                mediaType("xml", MediaType.APPLICATION_XML).
                mediaType("json", MediaType.APPLICATION_JSON);
    }





    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public WebContentInterceptor webContentInterceptor() {
        WebContentInterceptor interceptor = new WebContentInterceptor();
        interceptor.setCacheSeconds(0);
        interceptor.setUseExpiresHeader(true);
        interceptor.setUseCacheControlHeader(true);
        interceptor.setUseCacheControlNoStore(true);
        return interceptor;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/bower_components/**").addResourceLocations("/bower_components/");
        registry.addResourceHandler("/app/**").addResourceLocations("/app/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webContentInterceptor());
    }

    @SuppressWarnings("ConstantConditions")
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(getConfig("spring.datasource.driver-class-name"));
        dataSource.setUrl(getConfig("spring.datasource.url"));
        dataSource.setUsername(getConfig("spring.datasource.username"));
        dataSource.setPassword(getConfig("spring.datasource.password"));
        return dataSource;
    }


    @SuppressWarnings("ConstantConditions")
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
		em.setPackagesToScan("ge.ssoft.chat.core.model");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter() {
            {
                this.setDatabasePlatform(getConfig("spring.jpa.database-platform"));
                String showSql = getConfig("spring.jpa.show-sql");
                if (showSql != null) {
                    this.setShowSql(showSql.equals("true"));
                }
            }
        });
        Properties entityManagerProperties = new Properties();
        entityManagerProperties.put("hibernate.temp.use_jdbc_metadata_defaults", getConfig("hibernate.temp.use_jdbc_metadata_defaults"));
        entityManagerProperties.put("hibernate.show_sql",getConfig("spring.jpa.show-sql"));
        entityManagerProperties.put("hibernate.format_sql", getConfig("hibernate.format_sql"));
        em.setJpaProperties(entityManagerProperties);
        return em;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
