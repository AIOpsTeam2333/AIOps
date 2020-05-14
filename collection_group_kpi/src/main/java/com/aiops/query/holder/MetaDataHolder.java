package com.aiops.query.holder;

import java.util.ArrayList;
import java.util.List;

public class MetaDataHolder {

    private static List<String> services = new ArrayList<>();
    private static List<String> endpoints = new ArrayList<>();
    private static List<String> instances = new ArrayList<>();

    //获取服务信息
    public static List<String> getServices() {
        return services;
    }

    //设置服务信息
    public static void setServices(List<String> services) {
        MetaDataHolder.services = services;
    }

    //获取端点信息
    public static List<String> getEndpoints() {
        return endpoints;
    }

    //设置端点信息
    public static void setEndpoints(List<String> endpoints) {
        MetaDataHolder.endpoints = endpoints;
    }

    //获取实例信息
    public static List<String> getInstances() {
        return instances;
    }

    //设置端点信息
    public static void setInstances(List<String> instances) {
        MetaDataHolder.instances = instances;
    }
}
