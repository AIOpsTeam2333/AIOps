package com.aiops.processdata.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ResourceBundle;

/**
 * @author Zongwen Yang
 * @version 1.0
 * @date 2020/4/7 21:09
 */
@Configuration
public class DataConfig {

    @Bean(name = "bundle")
    public ResourceBundle getBundle() {
        return ResourceBundle.getBundle("datainfo");
    }

    @Bean
    @Autowired
    public DataSource dataSource(ResourceBundle bundle) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(bundle.getString("DATASOURCE_DRIVER_CLASS"));
        ds.setUrl(bundle.getString("DATASOURCE_URL"));
        ds.setUsername(bundle.getString("DATASOURCE_USER"));
        ds.setPassword(bundle.getString("DATASOURCE_PASSWORD"));
        ds.setInitialSize(Integer.parseInt(bundle.getString("DATASOURCE_INIT_COLLECTIONS")));
        ds.setMaxActive(Integer.parseInt(bundle.getString("DATASOURCE_MAX_ACTIVE")));
        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
