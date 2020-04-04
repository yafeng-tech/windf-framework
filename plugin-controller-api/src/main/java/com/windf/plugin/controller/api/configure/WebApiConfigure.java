package com.windf.plugin.controller.api.configure;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.windf.plugin.controller.api.session.SessionFilter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebApiConfigure {
    @Bean
    public HttpMessageConverters fastJsonConfigure(){
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();

        //处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON);
        converter.setSupportedMediaTypes(fastMediaTypes);

        //日期格式化
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        converter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(converter);
    }

    @Bean
    public FilterRegistrationBean sessionFilterConfigure(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new SessionFilter());//实例化Filter类
        filterRegistrationBean.addUrlPatterns("/*");//设置匹配模式,这里设置为所有
        filterRegistrationBean.setName("SessionFilter");//设置过滤器名称
        filterRegistrationBean.setOrder(1);//设置执行顺序

        return filterRegistrationBean;
    }
}
