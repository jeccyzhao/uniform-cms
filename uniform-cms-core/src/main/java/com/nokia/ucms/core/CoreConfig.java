package com.nokia.ucms.core;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by x36zhao on 2017/3/14.
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackageClasses = CoreConfig.class)
public class CoreConfig
{
}
