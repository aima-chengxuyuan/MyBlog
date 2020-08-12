package com.lz.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**拦截器
 * @author lize
 */
@Configuration//配置类
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")//拦截admin/**的访问路径
                .excludePathPatterns("/admin")//排除
                .excludePathPatterns("/admin/login");//排除
    }
}
