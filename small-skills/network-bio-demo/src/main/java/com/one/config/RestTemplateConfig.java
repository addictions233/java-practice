package com.one.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 在java中发起http请求的主要有 JDK的HttpURLConnection, Apache HttpClient4.x, Square公司开源的OkHttp
 * 使用okHttp封装RestTemplate
 * 效率: OkHttp > HttpClient > HttpURLConnection
 * @author wanjunjie
 * @date 2023/3/21
 */
@Configuration
public class RestTemplateConfig {
	public static final String MAIN_REST_TEMPLATE = "mainRestTemplate";

	/**
	 * Spring框架提供的RestTemplate类简化了http服务的通信方式, 同一了Restful的标准, 封装了http连接
	 * RestTemplate默认依赖HttpURLConnection, 我们可以调用#setRequestFactory方法来替换为HttpClient或者OkHttp
	 * @param okHttpClient 底层依赖 OKHttp
	 */
	@Primary
	@Bean(MAIN_REST_TEMPLATE)
	public RestTemplate restTemplate(@Autowired OkHttpClient okHttpClient) {
		OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory(okHttpClient);
		return new RestTemplate(factory);
	}

	@Bean
	@DependsOn("connectionPool")
	public OkHttpClient okHttpClient(@Autowired ConnectionPool connectionPool) {
		return new OkHttpClient.Builder()
				.connectionPool(connectionPool)
				.connectTimeout(60L, TimeUnit.SECONDS)
				.readTimeout(60L, TimeUnit.SECONDS)
				.writeTimeout(60L, TimeUnit.SECONDS)
				.build();
	}

	@Bean
	public ConnectionPool connectionPool() {
		return new ConnectionPool(100, 5, TimeUnit.MINUTES);
	}
}
