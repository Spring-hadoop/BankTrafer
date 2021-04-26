package com.plus.bysj.account.config;


import com.plus.bysj.account.filter.AccessFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class ApplicationConfig {
    /**
     * 资源过滤器
     * @return 资源过滤器
     */
    @Bean
    public AccessFilter accessFilter(){
        return new AccessFilter();
    }

}
