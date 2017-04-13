package com.nokia.ucms.portal;

import com.nokia.ucms.biz.BizConfig;
import com.nokia.ucms.common.CommonConfig;
import com.nokia.ucms.core.CoreConfig;
import com.nokia.ucms.openapi.OpenApiConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.Filter;

/**
 * Created by x36zhao on 2017/3/3.
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {
        CommonConfig.class,
        BizConfig.class,
        OpenApiConfig.class,
        CoreConfig.class,
        PortalApplication.class
})
public class PortalApplication extends SpringBootServletInitializer
{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(PortalApplication.class);
    }

//    @Bean
//    public FilterRegistrationBean gzipFilter()
//    {
//        FilterRegistrationBean filterBean = new FilterRegistrationBean();
//        filterBean.setFilter(new GZIPFilter());
//        return filterBean;
//    }

    public static void main(String[] args)
    {
        SpringApplication.run(PortalApplication.class, args);
    }
}
