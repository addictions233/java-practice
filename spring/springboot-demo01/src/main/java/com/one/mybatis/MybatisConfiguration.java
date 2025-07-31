package com.one.mybatis;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MybatisConfiguration {

    private final Interceptor[] interceptors;

    @Bean
    ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> {
            if (ArrayUtils.isNotEmpty(interceptors)) {
                for (Interceptor interceptor : interceptors) {
                    configuration.addInterceptor(interceptor);
                }
            }
        };
    }
}
