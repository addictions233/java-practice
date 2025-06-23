//package com.one.mybatisplus.config;
//
//import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
//import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
//import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
//import com.one.mybatisplus.datascope.handler.impl.DataPermissionHandlerImpl;
//import com.one.mybatisplus.datascope.handler.impl.DataScopeLineHandlerImpl;
//import com.one.mybatisplus.datascope.interceptor.DataScopeInnerInterceptor;
//import com.one.mybatisplus.datascope.interceptor.ChannelInnerInterceptor;
//import com.one.mybatisplus.datascope.handler.ChannelLineHandler;
//import com.one.mybatisplus.datascope.interceptor.DelFlagLineInnerInterceptor;
//import net.sf.jsqlparser.expression.Expression;
//import net.sf.jsqlparser.expression.LongValue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.Resource;
//
///**
// * @ClassName: PageInterceptor
// * @Description: mybatis plus做分页查询必须写该配置类
// * @Author: one
// * @Date: 2020/12/26
// */
//@Configuration
//public class InterceptorConfig {
//
//    @Resource
//    private MybatisPlusProperties properties;
//
//    @Bean
//    public com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor mybatisPlusInterceptor(){
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        // 分页插件
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
//        //数据权限的插件
////        // 使用mybatis-plus的数据权限的插件
////        interceptor.addInnerInterceptor(new DataPermissionInterceptor(new DataPermissionHandlerImpl()));
//        // 自定义数据权限的插件
//        interceptor.addInnerInterceptor(new DataScopeInnerInterceptor(new DataScopeLineHandlerImpl()));
//
//        // 租户隔离插件
//        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
//            @Override
//            public Expression getTenantId() {
//                return new LongValue(1000L);
//            }
//
//            // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
//            @Override
//            public boolean ignoreTable(String tableName) {
//                return false;
//            }
//        }));
//
//        // 逻辑删除插件
//        interceptor.addInnerInterceptor(new DelFlagLineInnerInterceptor(properties, new DataPermissionHandlerImpl.DelLineHandler() {
//            @Override
//            public boolean ignoreTable(String tableName) {
//                return false;
//            }
//        }));
//
//        //添加系统渠道插件
//        interceptor.addInnerInterceptor(new ChannelInnerInterceptor(new ChannelLineHandler() {
//            @Override
//            public Expression getSysSource() {
//                return new LongValue("");
//            }
//            // 这是 default 方法,默认返回 false 表示所有表都需要拼系统来源条件
//            @Override
//            public boolean ignoreTable(String tableName) {
//                return false;
//            }
//        }));
//        return interceptor;
//    }
//}
