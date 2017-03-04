package com.nokia.ucms.openapi;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by x36zhao on 2017/3/5.
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackageClasses = OpenApiConfig.class)
public class OpenApiConfig
{
}
