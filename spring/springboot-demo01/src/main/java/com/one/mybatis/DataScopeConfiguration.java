package com.one.mybatis;

import com.one.mybatis.plugin.DataScopePlugin;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataScopeConfiguration {

    @Bean
    public Interceptor dataScopeInterceptor(DataScopeProperty dataScopeProperty) {
        return new DataScopePlugin(dataScopeProperty.getCompanyMap());
    }
}
