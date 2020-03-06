package com.aiops.api.common.type;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-06 15:19
 */
public enum ClassificationType {

    ALL,
    SERVICE,
    ENDPOINT,
    INSTANCE,
    DATABASE;

    public String databaseName() {
        return name().toLowerCase();
    }
}
