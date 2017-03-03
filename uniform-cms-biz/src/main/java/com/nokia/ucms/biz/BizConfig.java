package com.nokia.ucms.biz;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by x36zhao on 2017/3/3.
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackageClasses = BizConfig.class)
public class BizConfig
{
}
