package com.aiops.api.common.enums;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 15:19
 */
public enum ScopeType {

    ALL("All"),
    SERVICE("Service"),
    ENDPOINT("Endpoint"),
    INSTANCE("Instance"),
    DATABASE("Database");

    private String frontEndName;

    public String getFrontEndName() {
        return frontEndName;
    }

    ScopeType(String frontEndName) {
        this.frontEndName = frontEndName;
    }

    public String databaseName() {
        return name().toLowerCase();
    }
}
