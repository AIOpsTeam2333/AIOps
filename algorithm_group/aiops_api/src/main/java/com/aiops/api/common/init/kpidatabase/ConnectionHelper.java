package com.aiops.api.common.init.kpidatabase;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.sql.*;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-07 13:39
 */
@Component
class ConnectionHelper {

    @Value("${spring.datasource.url}")
    private String connectingUrl;
    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(driver);  //加载数据库驱动
            System.out.println("数据库驱动加载成功");

            conn = DriverManager.getConnection(connectingUrl, username, password); //创建连接
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
