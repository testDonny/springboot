package com.example.handlerInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RegisterInterceptor implements WebMvcConfigurer {
    @Autowired
    private MyInterceptor myInterceptor;

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * addInterceptor 注册拦截器
         * addPathPatterns 配置拦截规则
         */
        registry.addInterceptor(myInterceptor)
                .addPathPatterns("/**");
    }
}