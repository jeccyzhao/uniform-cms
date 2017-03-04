package com.nokia.ucms.portal;

import com.nokia.ucms.biz.BizConfig;
import com.nokia.ucms.common.CommonConfig;
import com.nokia.ucms.openapi.OpenApiConfig;
import com.nokia.ucms.openapi.v1.controller.ProjectController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by x36zhao on 2017/3/3.
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {CommonConfig.class, PortalApplication.class, BizConfig.class, OpenApiConfig.class, ProjectController.class})
public class PortalApplication extends SpringBootServletInitializer
{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(PortalApplication.class);
    }

    public static void main(String[] args)
    {
        SpringApplication.run(PortalApplication.class, args);
    }
}
