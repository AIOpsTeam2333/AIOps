package com.aiops.processdata.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/4 21:35
 */
@Configuration
@Import(DataConfig.class)
@ComponentScan({"com.aiops.processdata"})
public class Config {
}
