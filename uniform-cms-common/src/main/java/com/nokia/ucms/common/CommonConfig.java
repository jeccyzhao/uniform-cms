package com.nokia.ucms.common;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by x36zhao on 2017/3/3.
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackageClasses = CommonConfig.class)
public class CommonConfig
{

}
