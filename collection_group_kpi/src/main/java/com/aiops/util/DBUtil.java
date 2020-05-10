package com.aiops.util;

import java.sql.*;


public class DBUtil {

    private static volatile Connection conn;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/aiops?useSSL=false&serverTimezone=UTC","root","1998");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean execSql(String sql){
        Statement statement = null;
        try {
            statement = conn.createStatement();
            return statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Connection getConnection() {
        return conn;
    }
}
