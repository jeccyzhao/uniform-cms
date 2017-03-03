package com.nokia.ucms.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by x36zhao on 2017/3/3.
 */
@SpringBootApplication
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
